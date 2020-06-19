
public class Checking extends Account
{
	private static String accountType = "checking";

	Checking(double initialDeposit)
	{
		super();
		this.setBalance(initialDeposit);
		this.checkIntrest(0);
	}

	@Override
	public String toString()
	{
		return "Account Type: " + accountType + " account\n" + "Account number: " + this.getAccountNumber() + "\n"
				+ "Balance: " + this.getBalance() + "\n" + "Intrest rate: " + this.getIntrest() + "%\n";
	}
	
}
