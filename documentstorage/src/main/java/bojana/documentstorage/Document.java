package bojana.documentstorage;

public class Document {

	private String document_base64;

	public String getDocument_base64() {
		return document_base64;
	}

	public void setDocument_base64(String document_base64) {
		this.document_base64 = document_base64;
	}

	public Document(String document_base64) {
		super();
		this.document_base64 = document_base64;
	}
	
	
	
}
