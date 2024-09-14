package bojana.documentstorage;

import java.sql.DriverManager;
import java.sql.PreparedStatement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DocumentstorageApplication {

	public static void main(String[] args) {
		SpringApplication.run(DocumentstorageApplication.class, args);
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://172.17.0.4:3306/documents", "Bojana", "Bojana234");
	     	System.out.println("Connection is created successfully:");
	     	
	     	String query = "CREATE TABLE IF NOT EXISTS document (id int NOT NULL AUTO_INCREMENT, document_base64 text, PRIMARY KEY (id) );";
	     	PreparedStatement preparedStmt = conn.prepareStatement(query);
	     	preparedStmt.execute();
	     	conn.close();
	     	
	    } catch (Exception e) {
	        System.out.println(e);
	    }
		
	}

}
