import java.util.Arrays;

public class ATM_Simulation {
	
	String[] userNames = {"seshan.govender@gmail.com", "keshini.chetty@gmail.com"};
	String[] passwords = {"myPa$$word", "5e5hanG"};
	
	public static void main(String[] args) {
		System.out.println("Test");
	}

	public String findUserName(String userInput, String[] listOfUserNames) {
		for (String userName: listOfUserNames) {
			if (userName.equalsIgnoreCase(userInput)) return userInput;
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

}
