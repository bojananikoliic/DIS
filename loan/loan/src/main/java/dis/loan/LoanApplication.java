package dis.loan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.*;

@SpringBootApplication
public class LoanApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoanApplication.class, args);
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://172.17.0.4:3306/bank_loan", "Bojana", "Bojana234");
	     	System.out.println("Connection is created successfully:");
	     	
	     	String query = "CREATE TABLE IF NOT EXISTS loan (id int NOT NULL AUTO_INCREMENT, currency varchar(255), area float, interest float, years int, loan_participation float, first_apartment tinyint, number_of_residents tinyint, uuid varchar(255), PRIMARY KEY (id) );";
	     	PreparedStatement preparedStmt = conn.prepareStatement(query);
	     	preparedStmt.execute();
	     	conn.close();
	     	
	    } catch (Exception e) {
	        System.out.println(e);
	    }
	      
	}

}
