public class ATM_Simulation {
	
	String[] userNames = {"seshan.govender@gmail.com"};
	String[] passwords = {"myPa$$word"};
	
	public static void main(String[] args) {
		System.out.println("Test");
	}

	public String findUserName(String userInput, String[] listOfUserNames) {
		for (String userName: listOfUserNames) {
			if (userName.equalsIgnoreCase(userInput)) return userInput;
		}
		return "User not found";		
	}

	public String locatePassword(String userInput, String[] listOfPasswords) {
		for (String password : listOfPasswords) {
			if (password.equals(userInput)) return userInput;
		}
		return "Incorrect password!\nPlease re-enter your password and note that passwords are case sensitive.";
	}

}
