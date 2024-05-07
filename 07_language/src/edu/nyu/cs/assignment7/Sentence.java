package edu.nyu.cs.assignment7;

import java.util.ArrayList;

public class Sentence implements SequentiallyOrdered {
    private ArrayList<Word> sentenceAL = new ArrayList<Word>();

    public Sentence(String sentence) {
        String[] words = sentence.split("[^\\w']+");
        for (int i = 0; i < words.length; i++) {
            Word word = new Word(words[i], i);
            sentenceAL.add(word);
        }
    }

    public OrderedThing getFirst() {
        return sentenceAL.get(0);
    }

    public OrderedThing getLast() {
        return sentenceAL.get(sentenceAL.size() - 1);
    }

    public ArrayList<OrderedThing> getSequence() {
        ArrayList<OrderedThing> OT = new ArrayList<OrderedThing>();
        for (int i = 0; i < sentenceAL.size(); i++) {
            OT.add(sentenceAL.get(i));
        }
        return OT;
    }

    public String toString() {
        String result = "";
        for (int i = 0; i < sentenceAL.size(); i++) {
            result += sentenceAL.get(i).toString();
            if (i != sentenceAL.size() - 1) {
                result += " ";
            }
        }
        return result + ".";
    }

    public boolean equals(Sentence other) {
        for (int i = 0; i < this.sentenceAL.size(); i++) {
            if (!this.sentenceAL.get(i).equals(other.sentenceAL.get(i))) {
                return false;
            }
        }
        return true;
    }
}
