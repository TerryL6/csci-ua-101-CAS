// DO NOT TOUCH THIS FILE!
package edu.nyu.cs.assignment7.tests;

// import the classes to be tested
import edu.nyu.cs.assignment7.Character;
import edu.nyu.cs.assignment7.OrderedThing;
import edu.nyu.cs.assignment7.Word;
import edu.nyu.cs.assignment7.Sentence;


// import junit 4 testing framework
import static org.junit.Assert.*;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.*;

// import java.nio.file.Paths;
import java.util.ArrayList;
// import java.util.Arrays;

// import javax.print.DocFlavor.CHAR_ARRAY;
// import javax.swing.event.ChangeEvent;

// import java.util.Arrays;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule; // system rules lib - useful for capturing system output
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;
// import org.mockito.internal.matchers.Or;

public class LanguageExerciseTest {

    @ClassRule
    public static final TextFromStandardInputStream systemInMock = emptyStandardInputStream();

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule();

    @Before
    public void init() {
        // any pre-test setup here
    }

    @Test
    public void testCharacterEquals() {
        char[] mockCh = {'a', 'c', 'd', 'f'};
        int[] mockPos = {0, 1, 0, 0};
        boolean[] expecteds = {true, false, true, false};
        for (int i = 0; i < mockCh.length; i++) {
            char c = mockCh[i];
            int pos = mockPos[i];
            boolean expected = expecteds[i];
            Character ch;
            if (i == 3) {
                ch = new Character('b', pos);
            } else { ch = new Character(c, pos); }
            boolean actual = ch.equals(new Character(c, 0));
            assertTrue("Expected Character.equals() to return " + expected + " but it returned " + actual + " instead.", expected == actual);
        }
    }

    @Test
    public void testCharacterToString() {
        char[] mockCh = { 'a', 'b' };
        for (int i = 0; i < mockCh.length; i++) {
            Character c = new Character(mockCh[i], 0);
            String expected = String.valueOf(mockCh[i]);
            String actual = c.toString();
            assertTrue("Expected Character.toString() to return " + expected + " but it returned " + actual + " instead.", expected.equals(actual));
        }
    }

    @Test
    public void testWordEquals() {
        String[] mockWords = { "hello", "goodbye", "yes" };
        int[] mockPos = { 0, 1, 0};
        boolean[] expecteds = { true, false, false };
        for (int i = 0; i < mockWords.length; i++) {
            String str = mockWords[i];
            int pos = mockPos[i];
            boolean expected = expecteds[i];
            Word w;
            if (i == 2) { 
                w = new Word("no", pos); 
            } 
            else { w = new Word(str, pos); }
            boolean actual = w.equals(new Word(str, 0));
            assertTrue("Expected Word.equals() to return " + expected + " but it returned " + actual + " instead.", expected == actual);
        }
    }
    
    @Test
    public void testGetFirstOfWord() {
        String[] mockWords = { "hello", "goodbye" };
        for (int i = 0; i < mockWords.length; i++) {
            Word w = new Word(mockWords[i], 0);
            Character expected = new Character(mockWords[i].charAt(0), 0);
            OrderedThing actual = w.getFirst();
            // assertTrue(actual instanceof Character);
            // Character acChar = (Character) actual;
            assertTrue("Expected Word.getFirst() to return " + expected + " but it returned " + actual + " instead.", expected.equals(actual));
        }
    }

    @Test
    public void testGetLastOfWord() {
        String[] mockWords = { "hello", "goodbye" };
        for (int i = 0; i < mockWords.length; i++) {
            Word w = new Word(mockWords[i], 0);
            Character expected = new Character(mockWords[i].charAt(mockWords[i].length() - 1), mockWords[i].length() - 1);
            OrderedThing actual = w.getLast();
            // assertTrue(actual instanceof Character);
            // Character acChar = (Character) actual;
            assertTrue("Expected Word.getLast() to return " + expected + " but it returned " + actual + " instead.", expected.equals(actual));
        }
    }

    @Test
    public void testWordSequence() {
        String[] mockWords = { "hello", "goodbye" };
        for (int i = 0; i < mockWords.length; i++) {
            Word word = new Word(mockWords[i], 0);
            ArrayList<OrderedThing> expected = new ArrayList<OrderedThing>();
            for (int j = 0; j < mockWords[i].length(); j++) {
                expected.add( (OrderedThing) new Character(mockWords[i].charAt(j), j));
            }
            ArrayList<OrderedThing> actual = word.getSequence();
            assertTrue("Expected Word.getSequence() to return " + expected + " but it returned " + actual + " instead.", expected.equals(actual));
        }
    }

    @Test
    public void testWordToString() {
        String[] mockWords = { "hello", "goodbye" };
        for (int i = 0; i < mockWords.length; i++) {
            Word word = new Word(mockWords[i], 0);
            String expected = mockWords[i];
            String actual = word.toString();
            assertTrue("Expected Word.toString() to return " + expected + " but it returned " + actual + " instead.", expected.equals(actual));
        }
    }

    @Test
    public void testSentenceEquals() {
        String[] mockSents = { "A sentence.", "Another sentence." };
        boolean[] expecteds = { true, false };
        for (int i = 0; i < mockSents.length; i++) {
            String str = mockSents[i];
            boolean expected = expecteds[i];
            Sentence s;
            if (i == 1) { 
                s = new Sentence("Not this one."); 
            } 
            else { s = new Sentence(str); }
            boolean actual = s.equals(new Sentence(str));
            assertTrue("Expected Sentence.equals() to return " + expected + " but it returned " + actual + " instead.", expected == actual);
        }
    }

    @Test
    public void testGetFirstOfSentence() {
        String[] mockSents = { "This! is a sentence.", "Another, sentence." };
        for (int i = 0; i < mockSents.length; i++) {
            Sentence s = new Sentence(mockSents[i]);
            Word expected = new Word(mockSents[i].split("[^\\w']+")[0], 0);
            OrderedThing actual = s.getFirst();
            // assertTrue(actual instanceof Word);
            // Word acWord = (Word) actual;
            assertTrue("Expected Sentence.getFirst() to return " + expected + " but it returned " + actual + " instead.", expected.equals(actual));
        }
    }

    @Test
    public void testGetLastOfSentence() {
        String[] mockSents = { "This is a !sentence.", "Another example?." };
        for (int i = 0; i < mockSents.length; i++) {
            Sentence s = new Sentence(mockSents[i]);
            String[] input = mockSents[i].split("[^\\w']+");
            Word expected = new Word(input[input.length - 1], input.length - 1);
            OrderedThing actual = s.getLast();
            // assertTrue(actual instanceof Word);
            // Word acWord = (Word) actual;
            assertTrue("Expected Sentence.getLast() to return " + expected + " but it returned " + actual + " instead.", expected.equals(actual));
        }
    }

    @Test
    public void testSentenceSequence() {
        String[] mockSents = { "A sentence.", "Another sentence." };
        for (int i = 0; i < mockSents.length; i++) {
            Sentence s = new Sentence(mockSents[i]);
            ArrayList<OrderedThing> expected = new ArrayList<OrderedThing>();
            String[] words = mockSents[i].split("[^\\w']+");
            for (int j = 0; j < words.length; j++) {
                expected.add( (OrderedThing) new Word(words[j], j));
            }
            ArrayList<OrderedThing> actual = s.getSequence();
            assertTrue("Expected Sentence.getSequence() to return " + expected + " but it returned " + actual + " instead.", expected.equals(actual));
        }
    }

    @Test
    public void testSentenceToString() {
        String[] mockSents = { "A sentence.", "Another sentence." };
        for (int i = 0; i < mockSents.length; i++) {
            Sentence s = new Sentence(mockSents[i]);
            String expected = mockSents[i];
            String actual = s.toString();
            assertTrue("Expected Sentence.toString() to return " + expected + " but it returned " + actual + " instead.", expected.equals(actual));
        }
    }
    
}


