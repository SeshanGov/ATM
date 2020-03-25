import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ATMSimulationTests {
	
	ATM_Simulation testObject;
	
	@Before
	public void setup() {
		testObject = new ATM_Simulation();
	}

	@Test
	public void testCanFindUserNameRegardlessOfCaseSensitivity() {
		
		String desiredOutcome = "seshan.govender@gmail.com";
		String result = testObject.findUserName(desiredOutcome, testObject.userNames);
		assertEquals("First test:", desiredOutcome, result);
	
		desiredOutcome = "Seshan.Govender@Gmail.Com";
		result = testObject.findUserName(desiredOutcome, testObject.userNames);
		assertEquals("Second test:", desiredOutcome, result);
		
	}
	
	@Test
	public void testCannotFindUserNameInDatabase() {
		
		String userInput = "seshan.govender@gmail.co.za";
		String desiredOutcome = "User not found";
		String result = testObject.findUserName(userInput, testObject.userNames);
		assertEquals(desiredOutcome, result);
		
	}
	
	@Test
	public void testCanFindAValidPasswordInDatabase() {
		
		testObject = new ATM_Simulation();
		String userInput = "myPa$$word";
		String desiredOutcome = userInput;
		String result = testObject.locatePassword(userInput, testObject.passwords);
		assertEquals(desiredOutcome, result);
		
	}
	
	@Test
	public void testPasswordIsNotValidIfCaseMismatch() {
		testObject = new ATM_Simulation();
		String userInput = "mypa$$word";
		String desiredOutcome = "Incorrect password!\nPlease re-enter your password and note that passwords are case sensitive.";
		String result = testObject.locatePassword(userInput, testObject.passwords);
		assertEquals(desiredOutcome, result);
	}
	

}
