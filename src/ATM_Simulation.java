public class ATM_Simulation {
	
	String[] userNames = {"seshan.govender@gmail.com"};
	
	public static void main(String[] args) {
		System.out.println("Test");
	}

	public String findUserName(String userInput, String[] listOfUserNames) {
		for (String userName: listOfUserNames) {
			if (userName.equalsIgnoreCase(userInput)) return userInput;
		}
		return "User not found";		
	}

}
