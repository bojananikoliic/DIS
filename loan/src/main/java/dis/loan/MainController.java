package dis.loan;

import java.sql.*;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

@Controller
@RequestMapping(path="/loan")
public class MainController {
	
	@PostMapping(path="/newLoanRequest", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody HashMap<String, String> addNewLoan (@RequestBody Loan newLoan) throws IOException, TimeoutException {
		UUID uuid = UUID.randomUUID();
		newLoan.setUuid(uuid.toString());
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("172.17.0.2");
		try (Connection connection = factory.newConnection();
		    Channel channel = connection.createChannel()) {
			channel.queueDeclare("logs", true, false, false, null);
			String message = "New declaration request: " + newLoan.toString();
			channel.basicPublish("", "logs", null, message.getBytes());
			System.out.println(" [x] Sent '" + message + "'");
		}
		
		LoanValidation lv = new LoanValidation(
				newLoan.getPrice(),
				newLoan.getCurrency(),
				newLoan.getLoan_participation()
				);
		RestTemplate rt = new RestTemplate();
		ResponseEntity<LoanValidationResponse> responseEntity = rt.postForEntity(
				"http://172.17.0.3:8002/loanValidation/newLoanValidationRequest", 
				lv, 
				LoanValidationResponse.class
		);
		LoanValidationResponse response = responseEntity.getBody();
		if (!response.isValid()) {
			HashMap<String, String> res = new HashMap<String, String>();
			res.put("message", "Loan request invalid");
			res.put("reason", response.getReason());
			return res;
		}
		
		Document d = new Document(newLoan.getDocument_base46());
		System.out.println(d);
		RestTemplate rt2 = new RestTemplate();
		ResponseEntity<String> responseEntity2 = rt2.postForEntity(
				"http://172.17.0.7:8006/documentStorage/newDocument", 
				d, 
				String.class
		);
		
		
		Payment pay = new Payment(
				(float) (newLoan.getPrice() - newLoan.getLoan_participation()),
				(float) newLoan.getInterest() / 100,
				newLoan.getYears()
				);
		RestTemplate rt3 = new RestTemplate();
		ResponseEntity<Object> responseEntity3 = rt3.postForEntity(
				"http://172.17.0.8:8007/paymentCalculator/newPaymentCalculation", 
				pay, 
				Object.class
		);
		HashMap<String, Double> response2 = (HashMap<String, Double>) responseEntity3.getBody();
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://172.17.0.4:3306/bank_loan", "Bojana", "Bojana234");
	     	String query = "INSERT INTO loan (currency, area, interest, years, loan_participation, first_apartment, number_of_residents, uuid) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
	     	PreparedStatement preparedStmt = conn.prepareStatement(query);
	     	preparedStmt.setString(1, newLoan.getCurrency());
	     	preparedStmt.setFloat(2, (float) newLoan.getArea());
	     	preparedStmt.setFloat(3, (float) newLoan.getInterest());
	     	preparedStmt.setInt(4, newLoan.getYears());
	     	preparedStmt.setFloat(5, (float) newLoan.getLoan_participation());
	     	preparedStmt.setBoolean(6, newLoan.isFirst_apartment());
	     	preparedStmt.setInt(7, newLoan.getNumber_of_residents());
	     	preparedStmt.setString(8, newLoan.getUuid());
	     	
	     	preparedStmt.execute();
	     	conn.close();
	     	System.out.println("mhm");
	     	
	    } catch (Exception e) {
	        System.out.println(e);
	    }
		
		
		HashMap<String, String> res = new HashMap<String, String>();
		res.put("message", "Loan request successfully submitted");
		res.put("payment", Double.toString(response2.get("payment")));
		return res;
	}

	@GetMapping(path="/testApi")
	public @ResponseBody String testApi() {
		return "All ok";
	}
}