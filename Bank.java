import java.util.ArrayList;

public class Bank
{
	ArrayList<Customer> customers = new ArrayList<Customer>();
	
	public void addCustomer(Customer customer)
	{
		customers.add(customer);
	}
	public Customer getCustomer(int account)
	{
		return customers.get(account);
	}
	ArrayList<Customer> getCustomers()
	{
		return customers;
	}
}
