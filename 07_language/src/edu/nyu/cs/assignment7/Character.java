package edu.nyu.cs.assignment7;

public class Character extends OrderedThing {
    private char letter;

    public Character(char letter, int position) {
        super.position = position;
        this.letter = letter;
    }

    public String toString() {
        String result =  String.valueOf(letter);
        return result;
    }

    // Added a getter 
    public char getLetter() {
        return letter;
    }

    public int getPosition() {
        return super.position;
    }

    public boolean equals(Object other) {
        if(other instanceof Character){
            Character c = (Character) other;
            return (c.getPosition() == this.getPosition()) && (this.getLetter() == c.getLetter());
        }
        return false;
    }
}
