package bojana.documentstorage;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/documentStorage")
public class MainController {
	
	@PostMapping(path="/newDocument", consumes=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String newDocument (@RequestBody HashMap<String, String> newDocument) throws IOException, TimeoutException {
		System.out.println(newDocument.get("document_base64"));
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://172.17.0.4:3306/documents", "Bojana", "Bojana234");
	     	String query = "INSERT INTO document (document_base64) VALUES (?);";
	     	PreparedStatement preparedStmt = conn.prepareStatement(query);
	     	preparedStmt.setString(1, newDocument.get("document_base64"));
	     	
	     	preparedStmt.execute();
	     	conn.close();
	     	System.out.println("mhm");
	     	
	    } catch (Exception e) {
	        System.out.println(e);
	    }
		
		return "Document successfully saved";
	}
}
