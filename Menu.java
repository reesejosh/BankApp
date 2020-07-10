import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Menu
{

    Scanner keyboard = new Scanner(System.in);
    Bank bank = new Bank();
    boolean exit;

    public static void main(String[] args)
    {
        Menu menu = new Menu();
        menu.run();
    }

    private void run()
    {
        printAppHeader("Joshua's Banking app");
        while (!exit)
        {
            printMainMenu();
            int selection = getMainMenuSelection();
            performMainAction(selection);
        }
    }

    private void printAppHeader(String title)
    {
        int titleWidth = title.length() + 8;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < titleWidth; ++i)
        {
            sb.append("*");
        }
        System.out.println(sb.toString());
        System.out.println("*   " + title + "   *");
        System.out.println(sb.toString());
    }

    private void printMenuHeader(String message)
    {
        System.out.println();
        int messageWidth = message.length() + 6;
        StringBuilder sb = new StringBuilder();
        sb.append("+");
        for (int i = 0; i < messageWidth; ++i)
        {
            sb.append("=");
        }
        sb.append("+");
        System.out.println(sb.toString());
        System.out.println("    " + message + "   ");
        System.out.println(sb.toString());
    }

    private void printMainMenu()
    {
        printMenuHeader("Please Make a selection: ");
        System.out.println("1) Create a new account");
        System.out.println("2) Make a deposit");
        System.out.println("3) Make a withdraw");
        System.out.println("4) List account balances");
        System.out.println("0) Exit");
    }

    private int getMainMenuSelection()
    {
        int selection = -1;
        do
        {
            System.out.print("Enter your selection: ");
            try
            {
                selection = Integer.parseInt(keyboard.nextLine());
                if (selection < 0 || selection > 4)
                {
                    System.out.println("Selection outside of range. Please try again.");
                }
            } catch (NumberFormatException e)
            {
                System.out.println("Invalid selection. Please select 1-4. ");
            }
        } while (selection < 0 || selection > 4);
        return selection;
    }

    private void performMainAction(int selection)
    {
        switch (selection)
        {
            case 0:
                System.out.println("Thank you for using our application.");
                System.exit(0);
                break;
            case 1:
		try
                {
                    createAccount();
                } catch (InvalidAccountTypeException e)
                {
                System.out.println("Account was not created successfully.");
                }
                break;
            case 2:
                makeDeposit();
                break;
            case 3:
                makeWithdraw();
                break;
            case 4:
                listBalance();
                break;

            default:
                System.out.println("Unknown error has occured.");
        }
    }

    private String askQuestion(String question, List<String> answers)
    {
        String response = "";
        boolean choices = (answers != null && !answers.isEmpty());
        boolean firstRun = true;
        do
        {
            if (!firstRun)
            {
                System.out.println("Invalid entry. Please try again.");
            }
            System.out.print(question);
            if (choices)
            {
                for (int i = 0; i < answers.size() - 1; ++i)
                {
                    System.out.print("(");
                    System.out.print(answers.get(i) + "/");
                }
                System.out.print(answers.get(answers.size() - 1));
                System.out.print("): ");
            }
            response = keyboard.nextLine();
            if (choices)
            {
                response = response.toLowerCase();
            }
            firstRun = false;
            if (!response.isEmpty() && !choices)
            {
                break;
            }
        } while (response.isEmpty() || !answers.contains(response) );
        return response;
    }

    private String getSsn(String question)
    {
        String response = "";
        do
        {
            response = askQuestion(question, null);
            if (!response.matches("(?!000|666)[0-8][0-9]{2}-?(?!00)[0-9]{2}-?(?!0000)[0-9]{4}"))
            {
                System.out.println("Invalid entry. SSN must be 9 digits, dashes are acceptable. Please try again.");
            }
        } while (!response.matches("(?!000|666)[0-8][0-9]{2}-?(?!00)[0-9]{2}-?(?!0000)[0-9]{4}"));
        response = response.replaceFirst("(\\d{3})(\\d{2})(\\d{4})", "$1-$2-$3");
        return response;

    }

    private void createAccount() throws InvalidAccountTypeException
    {
        printMenuHeader("Create an Account");
        String accountType = askQuestion("Please enter accout type ", Arrays.asList("checking", "savings"));
        String firstName = askQuestion("Please enter first name: ", null);
        String lastName = askQuestion("Please enter last name: ", null);
        String ssn = getSsn("Please enter ssn:  ");
        double initialDeposit = getDeposit(accountType);

        Account account;
        if (accountType.equalsIgnoreCase("checking"))
        {
            account = new Checking(initialDeposit);
        } else if (accountType.equalsIgnoreCase("savings"))
        {
            account = new Savings(initialDeposit);
        } else
        {
            throw new InvalidAccountTypeException();
        }

        Customer customer = new Customer(firstName, lastName, ssn, account);
        bank.addCustomer(customer);
    }

    private double getDeposit(String accountType)
    {
        double initialDeposit = 0;
        boolean valid = false;
        while (!valid)
        {
            System.out.print("Please enter an initial deposit: ");
            try
            {
                initialDeposit = Double.parseDouble(keyboard.nextLine());
            } catch (NumberFormatException e)
            {
                System.out.println("Deposit must be a number.");
            }
            if (accountType.equalsIgnoreCase("checking"))
            {
                if (initialDeposit < 100)
                {
                    System.out.println("Checking accounts require a minimum of $100 dollars to open.");
                } else
                {
                    valid = true;
                }
            } else if (accountType.equalsIgnoreCase("savings"))
            {
                if (initialDeposit < 50)
                {
                    System.out.println("Savings accounts require a minimum of $50 dollars to open.");
                } else
                {
                    valid = true;
                }
            }
        }
        return initialDeposit;
    }

    private int selectAccount()
    {
        ArrayList<Customer> customers = bank.getCustomers();
        if (customers.size() <= 0)
        {
            System.out.println("No customers at your bank.");
            return -1;
        }
        System.out.println("Select an account:");
        for (int i = 0; i < customers.size(); i++)
        {
            System.out.println("\t" + (i + 1) + ") " + customers.get(i).basicInfo());
        }
        int account = 0;
        System.out.print("Enter your selection: ");
        try
        {
            account = Integer.parseInt(keyboard.nextLine()) - 1;
        } catch (NumberFormatException e)
        {
            account = -1;
        }
        if (account < 0 || account >= customers.size())
        {
            account = -1;
            System.out.println("Invalid account selected.");
        }
        return account;
    }

    private double getDollarAmount(String question)
    {
        System.out.print(question);
        double amount = 0;
        try
        {
            amount = Double.parseDouble(keyboard.nextLine());
        } catch (NumberFormatException e)
        {
            amount = 0;
        }
        return amount;
    }

    private void makeDeposit()
    {
        String repeat = "y";
        printMenuHeader("Make a Deposit");
        do
        {
            int account = selectAccount();
            if (account >= 0)
            {
                double amount = getDollarAmount("How much would you like to deposit?: ");
                bank.getCustomer(account).getAccount().deposit(amount);
            }
            repeat = askQuestion("Do you want to make addtional deposites", Arrays.asList("y", "n"));

        } while (!repeat.equalsIgnoreCase("n"));
    }

    private void makeWithdraw()
    {
        printMenuHeader("Make a Withdrawal");
        int account = selectAccount();
        if (account >= 0)
        {
            double amount = getDollarAmount("How much would you like to withdraw?: ");
            bank.getCustomer(account).getAccount().withdraw(amount);
        }
    }

    private void listBalance()
    {
        printMenuHeader("List Account Details");
        int account = selectAccount();
        if (account >= 0)
        {
            printMenuHeader("Account Details");
            System.out.println(bank.getCustomer(account).getAccount());
        }
    }

}
