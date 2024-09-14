package bojana.logger;

import java.sql.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

@SpringBootApplication
public class LoggerApplication {

	public static void main(String[] args) throws IOException, TimeoutException {
		SpringApplication.run(LoggerApplication.class, args);
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://172.17.0.4:3306/logs", "Bojana", "Bojana234");
	     	System.out.println("Connection is created successfully:");
	     	
	     	String query = "CREATE TABLE IF NOT EXISTS log (id int NOT NULL AUTO_INCREMENT, log_text text, PRIMARY KEY (id) );";
	     	PreparedStatement preparedStmt = conn.prepareStatement(query);
	     	preparedStmt.execute();
	     	conn.close();
	     	
	    } catch (Exception e) {
	        System.out.println(e);
	    }
		
		ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("172.17.0.2");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare("logs", true, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "'");
            
            try {
            	Class.forName("com.mysql.cj.jdbc.Driver");
    			java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://172.17.0.4:3306/logs", "Bojana", "Bojana234");
    	     	String query = "INSERT INTO log (log_text) VALUES (?);";
    	     	PreparedStatement preparedStmt = conn.prepareStatement(query);
    	     	preparedStmt.setString(1, message);
    	     	preparedStmt.execute();
    	     	conn.close();
			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
			}
        };
        channel.basicConsume("logs", true, deliverCallback, consumerTag -> { });
	}

}
