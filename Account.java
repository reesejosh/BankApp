
public class Account
{
	private double balance = 0;
	private double intrest = 0.02;
	private int accountNumber;
	private static int numbOfAccounts = 1000000;
	
	Account()
	{
		accountNumber = numbOfAccounts++;
	}

	public double getBalance()
	{
		return balance;
	}

	public void setBalance(double balance)
	{
		this.balance = balance;
	}

	public double getIntrest()
	{
		return intrest;
	}

	public void setIntrest(double intrest)
	{
		this.intrest = intrest;
	}

	public int getAccountNumber()
	{
		return accountNumber;
	}
	
	public void checkIntrest(double amount)
	{
		if (balance + amount > 10000)
		{
			intrest = 0.05;
		} else
		{
			intrest = 0.02;
		}
	}

	public void deposit(double amount)
	{
		if (amount <= 0)
		{
			System.out.println("You can not deposit negative money.");
			return;
		}
		checkIntrest(amount);
		amount = amount + amount * intrest;
		balance += amount;
		System.out.println();
		System.out.printf("You have deposited $%.2f" , amount);
		System.out.println(" dollars with an intrest rate of " + (intrest * 100) + "%");
		System.out.printf("You now have a balance of $%.2f.\n",  balance);
		
	}

	public void withdraw(double amount)
	{
		if (amount + 5 > balance)
		{
			System.out.println("You have insufficent funds.");
			return;
		}
		balance -= amount + 5;
		checkIntrest(0);
		System.out.println();
		System.out.printf("You have withdrawn $%.2f", amount);
		System.out.println(" dollars and incurred a fee of $5.00");
		System.out.printf("You now have a balance of $%.2f\n" ,balance);
		
	}
	
}
