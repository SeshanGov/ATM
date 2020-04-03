import java.util.Arrays;
import java.util.Scanner;

public class ATM_Simulation {
	
	String[] userNames = {"seshan.govender@gmail.com", "keshini.chetty@gmail.com"};
	String[] passwords = {"myPa$$word", "5e5hanG"};
	String[] transactionOptions = {"1", "2", "3", "4", "quit"};
	Double[] accountBalances = {10500.00, 8850.00};
	String[] airtimeMenuOptions = {"1", "2", "3", "4"};
	Scanner getUserInput = new Scanner(System.in);
	private String userName = null;
	private String password = null;
	private boolean loggedIn = false;
	
	private void setLoggedIn(boolean value) {
		this.loggedIn = value;
	}
	
	private void setUserName(String userName) {
		this.userName = userName;
	}
	
	private void setUserPassword(String password) {
		this.password = password;
	}
	
	private String getUserName() {
		return this.userName;
	}
	
	private String getUserPassword() {
		return this.password;
	}
	
	public static void main(String[] args) {
		ATM_Simulation currentSession = new ATM_Simulation();
		
		String userName = "User not found";
		while (userName.equals("User not found")) {
			System.out.println("Please enter your user name:");
			userName = currentSession.findUserName(currentSession.getUserInput.nextLine(), currentSession.userNames);
			if (userName.equals("User not found")) {
				System.out.println("User name does not exist.");
			}
		}
		
		String password = "";
		int loginAttempts = 3;
		boolean passwordIsValid = false;
		
		while (loginAttempts >= 0) {
			System.out.println("Please enter your password:");
			password = currentSession.getUserInput.nextLine();
			passwordIsValid = currentSession.locatePassword(userName, password, currentSession.passwords);
			if (passwordIsValid) {
				System.out.println("Authentication successful. Logging in...");
				currentSession.setLoggedIn(passwordIsValid);
				break;
			}
			else {
				if (loginAttempts == 0) {
					System.out.println("Your account has been locked. Please wait a few minutes before trying again.");
					break;
				}
				else {
					System.out.println("Incorrect password. Please re-type your password, bearing in mind that the passwords is case-sensitive.");
					System.out.println(loginAttempts + " attempts remaining.");
					loginAttempts--;
				}
			}
		}
		
		while (currentSession.loggedIn) {
			System.out.println(currentSession.loadMainScreen());
			String transact = currentSession.getUserInput.nextLine();
			boolean isValidTransaction = currentSession.isValidTransaction(transact);
			
			if (isValidTransaction) {
				switch(transact) {
					case "1":
						System.out.println("Displaying balance...");
						break;
					case "2":
						System.out.println("Withdrawing cash...");
						break;
					case "3":
						System.out.println("Depositing cash...");
						break;
					case "4":
						System.out.println("Buying airtime...");
						break;
					default:
						System.out.println("You have chosen not to proceed. Logging out...");
						currentSession.loggedIn = false;
				}
			} else {
				System.out.println("You have not entered a valid option");
				continue;
			}
			
			
			
		}
		
		
		currentSession.getUserInput.close();
		System.out.println("END");
		
	}

	public String findUserName(String userInput, String[] listOfUserNames) {
		for (String userName: listOfUserNames) {
			if (userName.equalsIgnoreCase(userInput)) return userInput.toLowerCase();
		}
		return "User not found";		
	}

	public boolean locatePassword(String userNameEntered, String passwordEntered, String[] listOfPasswords) {
		boolean loggedIn = false;
		int userNameIndex = Arrays.asList(userNames).indexOf(userNameEntered);
		int passwordIndex = Arrays.asList(passwords).indexOf(passwordEntered);
		if (userNameIndex == passwordIndex) {
			loggedIn = true;
		}		
		return loggedIn;
	}

	public String loadMainScreen() {
		return "---------------------------------------------------------------------\n"
				+ "\n"
				+ "WELCOME TO THE ONLINE BANKING PLATFORM\n"
				+ "\n"
				+ " - Press 1 if you would like to view your current balance.\n"
				+ " - Press 2 of you would like to withdraw cash from your account.\n"
				+ " - Press 3 if you would like to deposit cash into your account.\n"
				+ " - Press 4 if you would like to purchase airtime.\n"
				+ "\n"
				+ " Type 'quit' if you wish to cancel this transaction.\n"
				+ "------------------------------------------------------------------\n";
	}

	public boolean isValidTransaction(String userInput) {		
		boolean isValidTransaction = false;
		
		if (Arrays.asList(transactionOptions).contains(userInput)) isValidTransaction = true;
		else isValidTransaction = false;
	
		return isValidTransaction;
	}

	public Double getAccountBalance(String userName) {
		int userIndex = Arrays.asList(userNames).indexOf(userName);
		return accountBalances[userIndex];
	}

	public Double withdrawFromAccount(String userName, Double withdrawalAmount) {
		Double currentBalance = getAccountBalance(userName);
		if (withdrawalAmount > 0) {
			if (withdrawalAmount > currentBalance) throw new IllegalArgumentException("Sorry, you do not have sufficient funds in your account to complete this transaction.");
			return currentBalance - withdrawalAmount;
		}
		throw new IllegalArgumentException("Please note that the withdrawal amount must be greater than zero.");
	}

	public Double depositAmount(String userName, Double amountToDeposit) {
		if (amountToDeposit <= 0) throw new IllegalArgumentException("Please note that the deposit amount must be greater than zero.");
		Double currentBalance = getAccountBalance(userName);
		return currentBalance += amountToDeposit;
	}

	public String displayAirtimeMenu() {
		String airtimeMenu = "---------- BUY AIRTIME ----------"
						+	 ""
						+ 	 "Please select your network provider from the list provided below:"
						+ 	 ""
						+ 	 "- Enter 1 for MTN"
						+ 	 "- Enter 2 for Vodacom"
						+ 	 "- Enter 3 for CellC"
						+ 	 "- Enter 4 for Telkom"
						+ 	 "";
		return airtimeMenu;
	}

	public String getNetworkProviderInput(String userInput) {
		if (Arrays.asList(airtimeMenuOptions).contains(userInput)) return userInput;
		throw new IllegalArgumentException("You have not entered a valid option.");
	}

}
