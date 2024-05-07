package edu.nyu.cs.assignment7;

import java.util.ArrayList;

public class Word extends OrderedThing implements SequentiallyOrdered {
    private ArrayList<Character> wordAL = new ArrayList<Character>();

    public Word(String word, int position) {
        super.position = position;
        for (int i = 0; i < word.length(); i++) {
            Character character = new Character(word.charAt(i), i);
            wordAL.add(character);
        }
    }

    public Character getFirst() {
        return wordAL.get(0);
    }

    public Character getLast() {
        return wordAL.get(wordAL.size() - 1);
    }

    public ArrayList<OrderedThing> getSequence() {
        ArrayList<OrderedThing> OT = new ArrayList<OrderedThing>();
        for (int i = 0; i < wordAL.size(); i++) {
            OT.add(wordAL.get(i));
        }
        return OT;
    }

    public int getPosition() {
        return position;
    }

    public String toString() {
        String result = "";
        for (int i = 0; i < wordAL.size(); i++) {
            result += wordAL.get(i).toString();
        }
        return result;
    }

    public boolean equals(Object other) {
        if(other instanceof Word){
            Word other2 = (Word) other;

            // Compare position attribute
            if (position != other2.position) {
                return false;
            }

            // Compare size
            ArrayList<OrderedThing> OT1 = this.getSequence();
            ArrayList<OrderedThing> OT2 = other2.getSequence();
            if (OT1.size() != OT2.size()) {
                return false;
            }

            // Compare each element
            for (int i = 0; i < OT1.size(); i++) {
                if (! OT1.get(i).equals(OT2.get(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
