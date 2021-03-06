import java.util.Arrays;
import java.util.Scanner;

public class ATM_Simulation {
	
	String[] userNames = {"seshan.govender@gmail.com", "keshini.chetty@gmail.com"};
	String[] passwords = {"myPa$$word", "5e5hanG"};
	String[] transactionOptions = {"1", "2", "3", "4", "quit"};
	Double[] accountBalances = {10500.00, 8850.00};
	String[] airtimeMenuOptions = {"1", "2", "3", "4"};
	Scanner getUserInput = new Scanner(System.in);
	String userName = null;
	String password = null;
	boolean loggedIn = false;
	
	void setLoggedIn(boolean value) {
		this.loggedIn = value;
	}
	
	void setUserName(String userName) {
		this.userName = userName;
	}
	
	void setUserPassword(String password) {
		this.password = password;
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
				currentSession.setUserName(userName);
				currentSession.setUserPassword(password);
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
						System.out.println("Your current account balance is R " + currentSession.getAccountBalance(userName));
						break;
					case "2":
						System.out.println("Please enter the amount that you would like to withdraw:");
						Double withdrawalAmount = currentSession.getUserInput.nextDouble();
						System.out.println("Withdrawing cash...");
						try {
							currentSession.withdrawFromAccount(userName, withdrawalAmount);
							System.out.println("Thank you for your patience. Your transaction has been completed successfully.\n"
											+ "Please take your cash.\n"
											+ "Your new account balance is R " + currentSession.getAccountBalance(userName));
						} catch (IllegalArgumentException e) {
							System.out.println(e.getMessage());
						}
						break;
					case "3":
						System.out.println("Please enter the amount that you would like to deposit:");
						Double depositAmount = currentSession.getUserInput.nextDouble();
						try {
							System.out.println("Depositing funds...");
							currentSession.depositAmount(userName, depositAmount);
							System.out.println("Thank you for your patience. Your deposit has been made successfully.");
							System.out.println("Your new account balance is R " + currentSession.getAccountBalance(userName));
						} catch (IllegalArgumentException e) {
							System.out.println(e.getMessage());
						}
						break;
					case "4":
						System.out.println(currentSession.displayAirtimeMenu());
						try {
							String transaction = currentSession.validateNetworkProvider(currentSession.getUserInput.nextLine());
							currentSession.purchaseAirtime();							
						} catch (IllegalArgumentException e) {
							System.out.println(e.getMessage());
						}
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
	
	public void updateAccountBalance(String userName, double transactionAmount) {
		int userIndex = Arrays.asList(userNames).indexOf(userName);
		accountBalances[userIndex] = getAccountBalance(userName) + transactionAmount;
	}

	public Double withdrawFromAccount(String userName, Double withdrawalAmount) {
		Double currentBalance = getAccountBalance(userName);
		if (withdrawalAmount > 0) {
			if (withdrawalAmount > currentBalance) throw new IllegalArgumentException("Sorry, you do not have sufficient funds in your account to complete this transaction.");
			updateAccountBalance(userName, -withdrawalAmount);
			return getAccountBalance(userName);
		}
		throw new IllegalArgumentException("Please note that the withdrawal amount must be greater than zero.");
	}

	public Double depositAmount(String userName, Double amountToDeposit) {
		if (amountToDeposit <= 0) throw new IllegalArgumentException("Please note that the deposit amount must be greater than zero.");
		updateAccountBalance(userName, amountToDeposit);
		return getAccountBalance(userName);
	}

	public String displayAirtimeMenu() {
		String airtimeMenu = "---------- BUY AIRTIME ----------\n"
						+	 "\n"
						+ 	 "Please select your network provider from the list provided below:\n"
						+ 	 "\n"
						+ 	 "- Enter 1 for MTN\n"
						+ 	 "- Enter 2 for Vodacom\n"
						+ 	 "- Enter 3 for CellC\n"
						+ 	 "- Enter 4 for Telkom\n"
						+ 	 "\n";
		return airtimeMenu;
	}

	public String validateNetworkProvider(String userInput) {
		if (Arrays.asList(airtimeMenuOptions).contains(userInput)) return userInput;
		throw new IllegalArgumentException("You have not entered a valid option.");
	}
	
	public void purchaseAirtime() {
		System.out.println("Please enter the prepaid amount you wish to purchase:");
		double airtimeAmount = getUserInput.nextDouble();
		String cellphoneNumber = "";
		while(cellphoneNumber.length() < 10) {
			System.out.println("Please enter your 10 digit cell phone number here:");
			cellphoneNumber = getUserInput.nextLine();
		}
		
		System.out.println("Please wait while we process your request...");
		System.out.println("Thank you for your patience. Your transaction has been completed successfully.");
		updateAccountBalance(userName, -airtimeAmount);
		System.out.println("Airtime to the value of " + airtimeAmount + " has been purchased successfully for cellphone number " + cellphoneNumber);
	}

}
