
public class Savings extends Account
{
	private static String accountType = "savings";
	Savings(double initialDeposit)
	{
		super();
		this.setBalance(initialDeposit);
		this.checkIntrest(0);
	}
	@Override
	public String toString()
	{
		return "Account Type: " + accountType + "Account\n" + "Account number: " + this.getAccountNumber() + "\n"
				+ "Balance: " + this.getBalance() + "\n" + "Intrest rate: " + this.getIntrest() + "%\n";
	}
}
