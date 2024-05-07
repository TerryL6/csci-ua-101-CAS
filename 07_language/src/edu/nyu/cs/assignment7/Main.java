package edu.nyu.cs.assignment7;

public class Main {

        public static void main(String[] args) {

                // creating Sentence object
                Sentence s = new Sentence(
                                "In a village of La Mancha, the name of which I have no desire to call to mind, there lived not long since one of those "
                                                + "gentlemen that keep a lance in the lance-rack, an old buckler, a lean hack, and a greyhound for coursing.");

                Word w = (Word) s.getLast(); // creating Word object
                Character c = (Character) w.getLast(); // creating Character object

                // demonstration of various methods, using the first sentence of Miguel de
                // Cervantes's novel Don Quixote
                System.out.printf("The first word of Don Quixote is: %s%n", s.getFirst());
                System.out.printf("The last word of the first sentence of Don Quixote is: %s%n", w);
                System.out.printf("The first sentence of Don Quixote as a sequence is:%n%s%n", s.getSequence());
                System.out.printf("%n----------------------%n%n");
                System.out.printf("The first letter of the last word of the first sentence of Don Quixote is: %s%n",
                                w.getFirst());
                System.out.printf("The last letter of the last word of the first sentence of Don Quixote is: %s%n",
                                w.getLast());
                System.out.printf("The last word of the first sentence of Don Quixote as a sequence is: %s%n",
                                w.getSequence());
                System.out.printf("The position of the last word of the first sentence of Don Quixote is: %s%n",
                                w.getPosition());
                System.out.printf(
                                "The position of the last letter of the last word of the first sentence of Don Quixote is: %s%n",
                                c.getPosition());

                System.out.println();
        }

}
