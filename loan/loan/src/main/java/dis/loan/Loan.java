package dis.loan;

public class Loan {
	private double price;
	private String currency;
	private double area;
	private double interest;
	private int years;
	private double loan_participation;
	private boolean first_apartment;
	private int number_of_residents;
	private String uuid;
	public Loan(double price, String currency, double area, double interest, int years, double loan_participation,
			boolean first_apartment, int number_of_residents, String uuid) {
		super();
		this.price = price;
		this.currency = currency;
		this.area = area;
		this.interest = interest;
		this.years = years;
		this.loan_participation = loan_participation;
		this.first_apartment = first_apartment;
		this.number_of_residents = number_of_residents;
		this.uuid = uuid;
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
	public double getArea() {
		return area;
	}
	public void setArea(double area) {
		this.area = area;
	}
	public double getInterest() {
		return interest;
	}
	public void setInterest(double interest) {
		this.interest = interest;
	}
	public int getYears() {
		return years;
	}
	public void setYears(int years) {
		this.years = years;
	}
	public double getLoan_participation() {
		return loan_participation;
	}
	public void setLoan_participation(double loan_participation) {
		this.loan_participation = loan_participation;
	}
	public boolean isFirst_apartment() {
		return first_apartment;
	}
	public void setFirst_apartment(boolean first_apartment) {
		this.first_apartment = first_apartment;
	}
	public int getNumber_of_residents() {
		return number_of_residents;
	}
	public void setNumber_of_residents(int number_of_residents) {
		this.number_of_residents = number_of_residents;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	@Override
	public String toString() {
		return "Loan [price=" + price + ", currency=" + currency + ", area=" + area + ", interest=" + interest
				+ ", years=" + years + ", loan_participation=" + loan_participation + ", first_apartment="
				+ first_apartment + ", number_of_residents=" + number_of_residents + ", uuid=" + uuid + "]";
	}
	
}
