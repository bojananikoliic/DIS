package bojana.validator;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

@Controller
@RequestMapping(path="/loanValidation")
public class MainController {
	
	@PostMapping(path="/newLoanValidationRequest", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody HashMap<String, Object> newLoanValidation (@RequestBody LoanValidation newLoanValidation) throws IOException, TimeoutException {
		System.out.println(newLoanValidation);
		HashMap<String, Object> response = new HashMap<String, Object>();
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("172.17.0.2");
		try (Connection connection = factory.newConnection();
		    Channel channel = connection.createChannel()) {
			channel.queueDeclare("logs", true, false, false, null);
			String message = "New loan validation request: " + newLoanValidation.toString();
			channel.basicPublish("", "logs", null, message.getBytes());
			System.out.println(" [x] Sent '" + message + "'");
		}

		boolean ok = true;
		if (!newLoanValidation.getCurrency().equals("EUR") && !newLoanValidation.getCurrency().equals("USD") && !newLoanValidation.getCurrency().equals("GBP")) {
			response.put("valid", false);
			response.put("reason", "Invalid currency. Accepted currencies: EUR, USD & GBP");
			ok = false;
		}
		
		if (newLoanValidation.getLoan_participation() < newLoanValidation.getPrice() / 5) {
			response.put("valid", false);
			response.put("reason", "Too small loan participation, it has to be at least 20% of the original price.");
			ok = false;
		}
		
		if (ok) {
			response.put("valid", true);
			response.put("reason", "All ok");
		}
		
		return response;
	}
}
