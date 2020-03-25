import static org.junit.Assert.*;

import org.junit.Test;

public class ATMSimulationTests {

	@Test
	public void testCanFindUserNameRegardlessOfCaseSensitivity() {
		ATM_Simulation testObject = new ATM_Simulation();
		String desiredOutcome = "seshan.govender@gmail.com";
		String result = testObject.findUserName(desiredOutcome, testObject.userNames);
		assertEquals("First test:", desiredOutcome, result);
	
		desiredOutcome = "Seshan.Govender@Gmail.Com";
		result = testObject.findUserName(desiredOutcome, testObject.userNames);
		assertEquals("Second test:", desiredOutcome, result);
	}
	
	@Test
	public void testCannotFindUserNameInDatabase() {
		ATM_Simulation testObject = new ATM_Simulation();
		String userInput = "seshan.govender@gmail.co.za";
		String desiredOutcome = "User not found";
		String result = testObject.findUserName(userInput, testObject.userNames);
		assertEquals(desiredOutcome, result);
	}

}
