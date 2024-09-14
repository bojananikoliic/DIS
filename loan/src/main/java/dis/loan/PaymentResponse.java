package dis.loan;

public class PaymentResponse {
	private float payment;

	public PaymentResponse(float payment) {
		super();
		this.payment = payment;
	}

	public float getPayment() {
		return payment;
	}

	public void setPayment(float payment) {
		this.payment = payment;
	}

	@Override
	public String toString() {
		return "PaymentResponse [payment=" + payment + "]";
	}
	
}
