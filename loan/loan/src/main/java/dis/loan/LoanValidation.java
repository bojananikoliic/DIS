package dis.loan;

public class LoanValidation {
	private double price;
	private String currency;
	private double loan_participation;
	public LoanValidation(double price, String currency, double loan_participation) {
		super();
		this.price = price;
		this.currency = currency;
		this.loan_participation = loan_participation;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public double getLoan_participation() {
		return loan_participation;
	}
	public void setLoan_participation(double loan_participation) {
		this.loan_participation = loan_participation;
	}
	@Override
	public String toString() {
		return "LoanValidation [price=" + price + ", currency=" + currency + ", loan_participation="
				+ loan_participation + "]";
	}
	
}
