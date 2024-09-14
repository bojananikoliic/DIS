package dis.loan;

public class LoanValidationResponse {
	private boolean valid;
	private String reason;
	public LoanValidationResponse(boolean valid, String reason) {
		super();
		this.valid = valid;
		this.reason = reason;
	}
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	@Override
	public String toString() {
		return "LoanValidationResponse [valid=" + valid + ", reason=" + reason + "]";
	}
	
}
