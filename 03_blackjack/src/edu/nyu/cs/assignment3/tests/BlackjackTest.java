// DO NOT TOUCH THIS FILE!
package edu.nyu.cs.assignment3.tests;

import edu.nyu.cs.assignment3.Blackjack;

// import junit 4 testing framework
import java.util.Random;
import org.junit.Test;
import org.junit.ClassRule;
import org.junit.Rule;
import static org.junit.Assert.*;
import org.junit.contrib.java.lang.system.SystemOutRule; // system rules lib - useful for capturing system output
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.*;
// import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;

//import gradescope tests
// import jh61b.grader.GradedTest;

public class BlackjackTest {

    long seed = 252352352;

    // @Rule
    // public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();

    @ClassRule
    public static final TextFromStandardInputStream systemInMock = emptyStandardInputStream();

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule();

    @Test
    // @GradedTest(name="Test welcome", max_score=11)
    public void testWelcome() {
        // checks whether program behaves as expected
        systemInMock.provideLines("stand");
        try {
            // run the program and get the results
            TestOutput actual = TestOutput.getOne(1, systemOutRule);
            System.out.println();
            assertTrue("Expected to see a welcome message in the first line of output", actual.isWelcome);
        }
        catch (Exception e) {
            assertEquals("Program should not crash!", "Program crashed when testing for welcome message: " + e); // fail the test if any exception occurs
        }
    }

    @Test
    // @GradedTest(name="Test user cards displayed", max_score=11)
    public void testUserCardsDisplayed() {
        long testSeed;
        ArrayList<Integer> correctUserCards;
        // checks whether program behaves as expected
        for (int i=0; i<2; i++) {
            if (i == 0) {
                testSeed = 666;
                systemInMock.provideLines("stand");
                correctUserCards = new ArrayList<>(Arrays.asList(7, 6));
            } else {
                testSeed = 6666;
                correctUserCards = new ArrayList<>(Arrays.asList(10, 10, 8));
                systemInMock.provideLines("hit");
            }
            try {
                // run the program and get the results
                TestOutput actual = TestOutput.getOne(testSeed, systemOutRule);
                System.out.println();
                assertTrue("User's cards are not display", actual.usersCards.size() != 0);
                assertEquals("User's cards are not displayed correctly", correctUserCards, actual.usersCards);
            }
            catch (Exception e) {
                assertEquals("Program should not crash!", "Program crashed when testing for display of user's cards: " + e); // fail the test if any exception occurs
            }
        }
    }

    @Test
    // @GradedTest(name="Test dealers cards displayed", max_score=11)
    public void testDealersCardsDisplayed() {
        long testSeed;
        ArrayList<Integer> correctDealerCards;
        // checks whether program behaves as expected
        for (int i=0; i<2; i++) {
            if (i == 0) {
                testSeed = 666;
                systemInMock.provideLines("hit", "stand");
                correctDealerCards = new ArrayList<>(Arrays.asList(9, 5));
            } else {
                testSeed = 6666;
                correctDealerCards = new ArrayList<>(Arrays.asList(10, 5));
                systemInMock.provideLines("hit");
            }
            try {
                // run the program and get the results
                TestOutput actual = TestOutput.getOne(testSeed, systemOutRule);
                System.out.println();
                assertTrue("Dealer's cards are not display", actual.dealersCards.size() != 0);
                assertEquals("Dealer's cards are not displayed correctly", correctDealerCards, actual.dealersCards);
            }
            catch (Exception e) {
                assertEquals("Program should not crash!", "Program crashed when testing for display of user's cards: " + e); // fail the test if any exception occurs
            }
        }
    }


    @Test
    // @GradedTest(name="Test user win scenarios", max_score=11)
    public void testUserWinScenarios() {
        long testSeed;
        // checks whether program behaves as expected
        for (int i=0; i<2; i++) {
            if (i == 0) {
                testSeed = 99;
                systemInMock.provideLines("hit", "stand");
            }
            else {
                testSeed = 98;
                systemInMock.provideLines("hit", "hit", "hit", "stand");
            }
            try {
                // run the program and get the results
                TestOutput actual = TestOutput.getOne(testSeed, systemOutRule);
                System.out.println();
                if (!actual.isUserWon) {
                    assertEquals("Expected user to win when their card total is greater than the dealer's or the dealer is bust", "Your program does not show the user wins.");
                }
            }
            catch (Exception e) {
                assertEquals("Program should not crash!", "Program crashed when testing various user win scenarios: " + e); // fail the test if any exception occurs
            }
        }
    }

    @Test
    // @GradedTest(name="Test dealer win scenarios", max_score=11)
    public void testDealerWinScenarios() {
        long testSeed;
        // checks whether program behaves as expected
        for (int i=0; i<2; i++) {
            if (i == 0) {
                testSeed = 150;
                systemInMock.provideLines("hit","stand");
            } else {
                testSeed = 170;
                systemInMock.provideLines("stand");
            }
            try {
                // run the program and get the results
                TestOutput actual = TestOutput.getOne(testSeed, systemOutRule);
                System.out.println();
                if (!actual.isDealerWon) {
                    assertEquals("Expected dealer to win when their card total is greater than the user's", "Your program does not show the dealer wins.");
                }
            }
            catch (Exception e) {
                assertEquals("Program should not crash!", "Program crashed when testing various dealer win scenarios: " + e); // fail the test if any exception occurs
            }
        }
    } 

    @Test
    // @GradedTest(name="Test tie scenarios", max_score=11)
    public void testTieScenarios() {
        // checks whether program behaves as expected
        long testSeed;
        for (int i = 0; i < 2; i++){
            if (i == 0) {
                testSeed = 95;
                systemInMock.provideLines("hit", "stand");  // a tie scenario
            } else {
                testSeed = seed;
                systemInMock.provideLines("stand");  // a tie scenario

            }
            try {
                // run the program and get the results
                TestOutput actual = TestOutput.getOne(testSeed, systemOutRule);
                System.out.println();
                if (!actual.isTie) {
                    assertEquals("Expected a tie when the two players' card totals are equal.", "Your program does not announce a tie.");
                }

            }
            catch (Exception e) {
                assertEquals("Program should not crash!", "Program crashed when testing various tie scenarios: " + e); // fail the test if any exception occurs
            }
        }

    }    

    @Test
    // @GradedTest(name="Test dealer bust scenarios", max_score=11)
    public void testDealerBustScenarios() {
        long testSeed;
        // checks whether program behaves as expected
        for (int i=0; i<2; i++) {
            if (i == 0) {
                testSeed = 1000;
                systemInMock.provideLines("stand");
            }
            else{
                testSeed = 140;
                systemInMock.provideLines("hit", "stand");
            } 

            try {
                // run the program and get the results
                TestOutput actual = TestOutput.getOne(testSeed, systemOutRule);
                System.out.println();
                if (!actual.isDealerBust || !actual.isUserWon) {
                    assertEquals("Expected dealer to bust and user to win.", "Your program does not show the dealer is bust or the user wins");
                }
            }
            catch (Exception e) {
                assertEquals("Program should not crash!", "Program crashed when testing various dealer bust scenarios: " + e); // fail the test if any exception occurs
            }
        }
    }    

    @Test
    // @GradedTest(name="Test user bust scenarios", max_score=11)
    public void testUserBustScenarios() {
        long testSeed;
        // checks whether program behaves as expected
        for (int i=0; i<2; i++) {
            if (i == 0) testSeed = 2000;    // user bust at the beginning
            else {
                testSeed = 1000;
                systemInMock.provideLines("hit", "hit");    // user bust after two hits
            }
            try {
                // run the program and get the results
                TestOutput actual = TestOutput.getOne(testSeed, systemOutRule);
                System.out.println();
                if (!actual.isUserBust || !actual.isDealerWon) {
                    assertEquals("Expected user to bust and dealer win.", "Your program does not show the user is bust or the dealer wins");
                }
            }
            catch (Exception e) {
                assertEquals("Program should not crash!", "Program crashed when testing various user bust scenarios: " + e); // fail the test if any exception occurs
            }
        }
    }    

    
    @Test
    // @GradedTest(name="Test for any outcome", max_score=12)
    public void testForAnyOutcome() {
        Random random = new Random(seed);
        // checks whether program behaves as expected
        for (int i=0; i<20; i++) {
            double rand = random.nextDouble();
            if (rand < 0.25) systemInMock.provideLines("stand");
            else if (rand < 0.5) systemInMock.provideLines("hit", "stand");
            else if (rand < 0.75) systemInMock.provideLines("hit", "hit", "stand");
            else systemInMock.provideLines("hit", "hit", "hit", "stand");
            try {
                // run the program and get the results
                TestOutput actual = TestOutput.getOne(random.nextLong(), systemOutRule);
                System.out.println();
                if (!actual.isWelcome) {
                    // program did not print welcome message as first line
                    assertEquals("Expected to see a welcome message in the first line of output", "Your program did not output a welcome message as the first line... tsk tsk.");
                }
                if (actual.isUserWon && actual.isDealerWon || actual.isUserWon && actual.isTie || actual.isDealerWon && actual.isTie) {
                    assertEquals("In any run of the program, either the user wins, the dealer wins, or it's a tie", "Your program shows two winners, or shows a winner and a tie at the same time.");
                }
                // test for no outcome
                if (!actual.isUserWon && !actual.isDealerWon && !actual.isTie) {
                    assertEquals("In any run of the program, someone should win or the players should tie.", "In your program, we did not detect any winner or any tie, based on your output.");
                }
                /* test for user bust scenerios */
                    // test false negatives
                if (actual.userTotal > 21 && !actual.isUserBust) {
                    assertEquals("Expected user to bust when their card total is over 21.", "Your program does not show the user is bust even when their card total is " + actual.userTotal);
                }
                    // test for false positive
                if (actual.userTotal <= 21 && actual.isUserBust) {
                    assertEquals("Expected user to only bust when their card total is over 21.", "Your program shows the user is bust even when their card total is " + actual.userTotal);
                }
                /* test for dear bust scenerios */
                    // test for false negative
                if (actual.dealerTotal > 21 && !actual.isDealerBust) {
                    assertEquals("Expected dealer to bust when their card total is over 21.", "Your program does not show the dealer is bust even when their card total is " + actual.dealerTotal);
                }
                    // test for false positive
                if (actual.dealerTotal > 21 && !actual.isDealerBust) {
                    assertEquals("Expected dealer to only bust when their card total is over 21.", "Your program shows the dealer is bust even when their card total is " + actual.dealerTotal);
                }
                /* test for dealer win scenerios */
                    // test for false positive
                if (actual.isDealerWon && !((actual.dealerTotal > actual.userTotal) || actual.isUserBust)) {
                    assertEquals("Expected dealer to win only when their card total is greater than the user's or the user busts", "Your program shows the dealer winning despite having " + actual.dealerTotal + " while the user has " + actual.userTotal + " and the user busts=" + actual.isUserBust + ".");
                }
                    // test for false negative
                if (!(actual.dealerTotal > 21) && !actual.isDealerWon && ((actual.dealerTotal > actual.userTotal) || actual.isUserBust)) {
                    assertEquals("Expected dealer to win when their card total is greater than the user's's or the user is bust", "Your program does not show the dealer winning despite having " + actual.dealerTotal + " while the user has " + actual.userTotal + " and the dealer busts=" + actual.isUserBust + ".");
                }
                /* test for user win scenerios */
                    // test for false positive
                if (actual.isUserWon && !((actual.userTotal > actual.dealerTotal) || actual.isDealerBust)) {
                    assertEquals("Expected user to win only when their card total is greater than the dealer's or the dealer is bust", "Your program shows the user winning despite having " + actual.userTotal + " while the dealer has " + actual.dealerTotal + " and the dealer busts=" + actual.isDealerBust + ".");
                }
                    // test for false negative
                if (!(actual.userTotal > 21) && !actual.isUserWon && ((actual.userTotal > actual.dealerTotal) || actual.isDealerBust)) {
                    assertEquals("Expected user to win when their card total is greater than the dealer's or the dealer is bust", "Your program does not show the user winning despite having " + actual.userTotal + " while the dealer has " + actual.dealerTotal + " and the dealer busts=" + actual.isDealerBust + ".");
                }
                /* test for tie scenerios */
                    // false positive tie
                if (actual.isTie && !(actual.dealerTotal == actual.userTotal)) {
                    assertEquals("Expected a tie announce only when the two players' card totals are equal.", "Your program shows a tie even though it shows the dealer scored " + actual.dealerTotal + " while the user scored " + actual.userTotal);
                }
                    // false negative tie
                if (!actual.isTie && actual.userTotal == actual.dealerTotal) {
                    assertEquals("Expected a tie when the two players' card totals are equal.", "Your program does not announce a tie, even when the dealer's total is " + actual.dealerTotal + " and the user's is " + actual.userTotal);
                }

            }
            catch (Exception e) {
                assertEquals("Program should not crash!", "Program crashed when testing for either a win of any kind or a tie: " + e); // fail the test if any exception occurs
            }
        }
    }

} // Test class

// convenience class
class TestOutput {
    public TestOutput(String output) {
            output = output.trim(); // remove any leading/trailing whitespace
            this.lines = output.split("\n"); // split program output by line break
            this.usersCards = new ArrayList<Integer>();
            this.dealersCards = new ArrayList<Integer>();
            this.isWelcome = false;
            this.isUserBust = false;
            this.isDealerBust = false;
            this.isUserWon = false;
            this.isDealerWon = false;
            this.isTie = false;
            
            /* check for presence/absence of specific messages */ 
            // check welcome massage
            int size = lines.length;
            if (size >= 1 && lines[0].trim().startsWith("Welcome to Blackjack!")) {
                this.isWelcome = true;
            }
            // check the last line
            if (size >= 1 && lines[size - 1].startsWith("Dealer wins!")) {
                this.isDealerWon = true;
            }
            if (size >= 1 && lines[size - 1].startsWith("You win!")) {
                isUserWon = true;
            }
            if (size >= 1 && lines[size - 1].startsWith("Tie!")) {
                this.isTie = true;
            }

            // check whether user busts
            for (String line : lines) {
                line = line.trim();
                // check user's cards
                if (line.startsWith("Your cards are: ")) {
                    // get the numbers out of the line
                    this.usersCards = TestOutput.getNumbers(lines[size - 3]);
                }
                // check dealer's cards
                if (line.startsWith("The dealer's cards are: ")) {
                    // get the numbers out of the line
                    this.dealersCards = TestOutput.getNumbers(lines[size - 2]);
                }
                // check whether dealer busts
                if (line.startsWith("The dealer has bust!")) {
                    this.isDealerBust = true;
                }
                if (line.startsWith("You have bust!")) {
                    this.isUserBust = true;
                }
            } // for each line

            this.dealerTotal = TestOutput.getTotal(dealersCards);
            this.userTotal = TestOutput.getTotal(usersCards);
    }

    public static ArrayList<Integer> getNumbers(String line) {
        String[] words = line.split("[ ,]+"); // split by space or comma
        ArrayList<Integer> nums = new ArrayList<Integer>();
        for (int i=0; i<words.length; i++) {
            words[i] = words[i].replaceAll("[^0-9]", ""); // extract just the number from string
            if (words[i].length() > 0) {
                nums.add(Integer.parseInt(words[i])); // add to array list
            }
        }
        return nums;
    }

    public static int getTotal(ArrayList<Integer> nums) {
        int total = 0;
        for (int i=0; i<nums.size(); i++) {
            total += nums.get(i);
        }        
        return total;
    }

    public static TestOutput getOne(long seed, SystemOutRule systemOutRule) throws Exception {
        String[] args = {Long.toString(seed)};
        try {
            systemOutRule.clearLog();
            systemOutRule.enableLog(); // start capturing System.out
            Blackjack.main(args);
            String output = systemOutRule.getLogWithNormalizedLineSeparator().trim();
            TestOutput actual = new TestOutput(output);
            return actual;
        }
        catch (Exception e) {
            throw e;
        }

    }

    public String[] lines;
    public ArrayList<Integer> usersCards = new ArrayList<Integer>();
    public ArrayList<Integer> dealersCards = new ArrayList<Integer>();
    public boolean isWelcome = false;
    public boolean isUserBust = false;
    public boolean isDealerBust = false;
    public boolean isUserWon = false;
    public boolean isDealerWon = false;
    public boolean isTie = false;
    public int dealerTotal = 0;
    public int userTotal = 0;
}
