package bojana.calculator;

public class RiskCalculation {
	private float amount;
	private float interest;
	private int years;
	public RiskCalculation(float amount, float interest, int years) {
		super();
		this.amount = amount;
		this.interest = interest;
		this.years = years;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public float getInterest() {
		return interest;
	}
	public void setInterest(float interest) {
		this.interest = interest;
	}
	public int getYears() {
		return years;
	}
	public void setYears(int years) {
		this.years = years;
	}
	@Override
	public String toString() {
		return "RiskCalculation [amount=" + amount + ", interest=" + interest + ", years=" + years + "]";
	}
	
	
}
