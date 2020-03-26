import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

import java.util.Arrays;

public class ATMSimulationTests {
	
	ATM_Simulation testObject;
	
	@Before
	public void setup() {
		testObject = new ATM_Simulation();
	}
	
	// Logging in to the system
	
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
		String userName = "seshan.govender@gmail.co.za";
		String desiredOutcome = "User not found";
		String result = testObject.findUserName(userName, testObject.userNames);
		assertEquals(desiredOutcome, result);
	}
	
	@Test
	public void testCanFindAValidPasswordInDatabase() {
		String userName = "seshan.govender@gmail.com";
		String userPassword = "myPa$$word";
		String desiredOutcome = "Valid password. Logging in...";
		String result = testObject.locatePassword(userName, userPassword, testObject.passwords);
		assertEquals(desiredOutcome, result);
	}
	
	@Test
	public void testPasswordIsNotValidIfCaseMismatch() {
		String userName = "seshan.govender@gmail.com";
		String userInput = "mypa$$word";
		String desiredOutcome = "Incorrect password!\nPlease re-enter your password and note that passwords are case sensitive.";
		String result = testObject.locatePassword(userName, userInput, testObject.passwords);
		assertEquals(desiredOutcome, result);
	}
	
	@Test
	public void testUsernameAndPasswordIsForCorrectAccount() {
		String userName = "seshan.govender@gmail.com";
		String password = "myPa$$word";
		String desiredOutcome = "Valid password. Logging in...";
		String result = testObject.locatePassword(userName, password, testObject.passwords);
		assertEquals(desiredOutcome, result);
	}
	
	@Test
	public void testUsernameAndPasswordIsNotForCorrectAccount() {
		String userName = "seshan.govender@gmail.com";
		String password = "";
		String desiredOutcome = "Incorrect password!\nPlease re-enter your password and note that passwords are case sensitive.";
		String result = testObject.locatePassword(userName, password, testObject.passwords);
		assertEquals(desiredOutcome, result);
	}
	
	// Once logged in - main functionality
	
	@Test
	public void testWelcomeScreenLoad() {
		String result = testObject.loadMainScreen();
		String desiredOutcome = "----------------------------------------\n"
							+	" WELCOME TO THE ONLINE BANKING PLATFORM \n"
							+ 	""
							+ 	" - Press 1 if you would like to view your current balance."
							+ 	" - Press 2 of you would like to withdraw cash from your account."
							+ 	" - Press 3 if you would like to deposit cash into your account."
							+ 	" - Press 4 if you would like to purchase airtime."
							+ 	"\n Type 'quit' if you wish to cancel this transaction."
							+	"----------------------------------------";
		assertEquals(desiredOutcome, result);
	}
	
	@Test
	public void userEntersValidOptionFromMainScreen() {
		String desiredOutcome = "";
		String result = testObject.getUserInput();
		
		if (Arrays.asList(testObject.transactionOptions).contains(result)) {
			desiredOutcome = "Valid response";
			assertEquals(desiredOutcome, result);
		}		
	}
	
	@Test
	public void userEntersInvalidOptionFromMainScreen() {
		testObject = mock(ATM_Simulation.class);
		when(testObject.getUserInput()).thenReturn("5");
		String desiredOutcome = "Invalid response";
		String result = testObject.getUserInput();
		assertNotEquals(desiredOutcome, result);
	}
	
	@Test
	public void testViewCurrentBalance() {
		String userName = "seshan.govender@gmail.com";
		Double result = testObject.getAccountBalance(userName);
		Double desiredOutcome = 10500.00;
		assertEquals(desiredOutcome, result);
	}
	
	@Test
	public void testCanGetCorrectBalanceForCorrectUser() {
		String userName = "seshan.govender@gmail.com";
		Double desiredOutcome = testObject.accountBalances[0];
		Double result = testObject.getAccountBalance(userName);
		assertEquals(desiredOutcome, result);
		
		userName = "keshini.chetty@gmail.com";
		desiredOutcome = testObject.accountBalances[1];
		result = testObject.getAccountBalance(userName);
		assertEquals(desiredOutcome, result);
	}

}
