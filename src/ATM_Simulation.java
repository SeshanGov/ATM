import java.util.Arrays;
import java.util.Scanner;

public class ATM_Simulation {
	
	String[] userNames = {"seshan.govender@gmail.com", "keshini.chetty@gmail.com"};
	String[] passwords = {"myPa$$word", "5e5hanG"};
	String[] transactionOptions = {"1", "2", "3", "4", "quit"};
	Double[] accountBalances = {10500.00, 8850.00};
	
	public static void main(String[] args) {
		System.out.println("Test");
	}

	public String findUserName(String userInput, String[] listOfUserNames) {
		for (String userName: listOfUserNames) {
			if (userName.equalsIgnoreCase(userInput)) return userInput.toLowerCase();
		}
		return "User not found";		
	}

	public String locatePassword(String userNameEntered, String passwordEntered, String[] listOfPasswords) {
		int userNameIndex = Arrays.asList(userNames).indexOf(userNameEntered);
		int passwordIndex = Arrays.asList(passwords).indexOf(passwordEntered);
		if (userNameIndex == passwordIndex) {
			return "Valid password. Logging in...";
		}		
		return "Incorrect password!\nPlease re-enter your password and note that passwords are case sensitive.";
	}

	public String loadMainScreen() {
		return "----------------------------------------\n"
				+	" WELCOME TO THE ONLINE BANKING PLATFORM \n"
				+ 	""
				+ 	" - Press 1 if you would like to view your current balance."
				+ 	" - Press 2 of you would like to withdraw cash from your account."
				+ 	" - Press 3 if you would like to deposit cash into your account."
				+ 	" - Press 4 if you would like to purchase airtime."
				+ 	"\n Type 'quit' if you wish to cancel this transaction."
				+	"----------------------------------------";
	}

	public String getUserInput() {

		Scanner getUserInput = new Scanner(System.in);
		String userInput = getUserInput.nextLine();
		String outcome = "";
		
		if (Arrays.asList(transactionOptions).contains(userInput)) outcome = "Valid response";
		else outcome = "Invalid response";
		
		getUserInput.close();
		return outcome;
	}

	public Double getAccountBalance(String userName) {
		int userIndex = Arrays.asList(userNames).indexOf(userName);
		return accountBalances[userIndex];
	}

	public Double withdrawFromAccount(String userName, Double withdrawalAmount) {
		if (withdrawalAmount > 0) {
			Double currentBalance = getAccountBalance(userName);
			return currentBalance - withdrawalAmount;
		}
		throw new IllegalArgumentException("Please note that the withdrawal amount must be greater than zero.");
	}

}
