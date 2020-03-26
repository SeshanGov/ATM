import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

import java.util.Arrays;

public class ATMSimulationTests {
	
	ATM_Simulation testObject;
	String userName;
	
	@Before
	public void setup() {
		testObject = new ATM_Simulation();
		userName = testObject.findUserName("Seshan.Govender@gmail.COM", testObject.userNames);
	}
	
	// Logging in to the system
	
	@Test
	public void testCanFindUserNameRegardlessOfCaseSensitivity() {
		String desiredOutcome = "seshan.govender@gmail.com";
		String userInput = "seshan.govender@gmail.com";
		String result = testObject.findUserName(userInput, testObject.userNames);
		assertEquals("First test:", desiredOutcome, result);
	
		userInput = "Seshan.Govender@Gmail.Com";
		result = testObject.findUserName(userInput, testObject.userNames);
		assertEquals("Second test:", desiredOutcome, result);
	}
	
	@Test
	public void testCannotFindUserNameInDatabase() {
		userName = "seshan.govender@gmail.co.za";
		String desiredOutcome = "User not found";
		String result = testObject.findUserName(userName, testObject.userNames);
		assertEquals(desiredOutcome, result);
	}
	
	@Test
	public void testCanFindAValidPasswordInDatabase() {
		String userPassword = "myPa$$word";
		String desiredOutcome = "Valid password. Logging in...";
		String result = testObject.locatePassword(userName, userPassword, testObject.passwords);
		assertEquals(desiredOutcome, result);
	}
	
	@Test
	public void testPasswordIsNotValidIfCaseMismatch() {
		String userInput = "mypa$$word";
		String desiredOutcome = "Incorrect password!\nPlease re-enter your password and note that passwords are case sensitive.";
		String result = testObject.locatePassword(userName, userInput, testObject.passwords);
		assertEquals(desiredOutcome, result);
	}
	
	@Test
	public void testUsernameAndPasswordIsForCorrectAccount() {
		String password = "myPa$$word";
		String desiredOutcome = "Valid password. Logging in...";
		String result = testObject.locatePassword(userName, password, testObject.passwords);
		assertEquals(desiredOutcome, result);
	}
	
	@Test
	public void testUsernameAndPasswordIsNotForCorrectAccount() {
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
	
	// Displaying the customer's account balance
	
	@Test
	public void testViewCurrentBalance() {
		Double result = testObject.getAccountBalance(userName);
		Double desiredOutcome = 10500.00;
		assertEquals(desiredOutcome, result);
	}
	
	@Test
	public void testCanGetCorrectBalanceForCorrectUser() {
		Double desiredOutcome = testObject.accountBalances[0];
		Double result = testObject.getAccountBalance(userName);
		assertEquals(desiredOutcome, result);
		
		userName = "keshini.chetty@gmail.com";
		desiredOutcome = testObject.accountBalances[1];
		result = testObject.getAccountBalance(userName);
		assertEquals(desiredOutcome, result);
	}
	
	// Withdrawing from account
	
	@Test
	public void testWithdrawalFromAccount() {
		Double withdrawalAmount = 500.00;
		Double desiredOutcome = 10000.00;
		Double result = testObject.withdrawFromAccount(userName, withdrawalAmount);
		assertEquals(desiredOutcome, result);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCannotWithdrawANegativeAmount() {
		Double withdrawalAmount = -500.00;
		testObject.withdrawFromAccount(userName, withdrawalAmount);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testWithdrawalAmountCannotBeEqualToZero() {
		Double withdrawalAmount = 0.00;
		testObject.withdrawFromAccount(userName, withdrawalAmount);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testWithdrawalAmountCannotExceedAccountBalance() {
		Double withdrawalAmount = 11000.00;
		testObject.withdrawFromAccount(userName, withdrawalAmount);
	}
	
	// Depositing into account
	
	@Test
	public void testCanDepositFundsIntoCustomersAccount() {
		Double amountToDeposit = 1000.00;
		Double desiredOutcome = 11500.00;
		Double result = testObject.depositAmount(userName,amountToDeposit);
		assertEquals(desiredOutcome, result);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testDepositAmountCannotBeEqualToZero() {
		Double amountToDeposit = 0.00;
		testObject.depositAmount(userName, amountToDeposit);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testDepositAmountCannotBeLessThanZero() {
		Double amountToDeposit = -250.00;
		testObject.depositAmount(userName, amountToDeposit);
	}
	
	// Airtime menu - option 4 on home screen
	
	@Test
	public void testDisplayAirtimeMenu() {
		String desiredOutcome = "---------- BUY AIRTIME ----------"
							+	""
							+ 	"Please select your network provider from the list provided below:"
							+ 	""
							+ 	"- Enter 1 for MTN"
							+ 	"- Enter 2 for Vodacom"
							+ 	"- Enter 3 for CellC"
							+ 	"- Enter 4 for Telkom"
							+ 	"";
		String result = testObject.displayAirtimeMenu();
		assertEquals(desiredOutcome, result);
	}
	
	@Test
	public void testUserEntersValidOptionFromAirtimeMenu() {
		String desiredOutcome = "2";
		String result = testObject.getNetworkProviderInput("2");
		assertEquals(desiredOutcome, result);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testUserEntersInvalidOptionFromAirtimeMenu() {
		testObject.getNetworkProviderInput("5");
	}

}
