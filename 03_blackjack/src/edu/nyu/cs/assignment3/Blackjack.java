package edu.nyu.cs.assignment3;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * A program to play blackjack.
 * 
 */
public class Blackjack {
  /* Do not modify this method */
  public static Random initRandom(String[] args) {
    if (args.length >= 1) {
      return new Random(Long.parseLong(args[0]));
    } else {
      return new Random();
    }
  }

  public static int sumup(ArrayList<Integer> deck) {
    int sum = 0;
    for (int index = 0; index < deck.size(); index++) {
      sum += deck.get(index);
    }
    return sum;
  }

  public static void user_showhand(ArrayList<Integer> deck) {
    System.out.print("Your cards are: " + deck.get(0));
    for (int index = 1; index < deck.size(); index++) {
      System.out.print(", ");
      System.out.print(deck.get(index));
    }
    System.out.println();
  }

  public static void dealer_showhand(ArrayList<Integer> deck) {
    System.out.print("The dealer's cards are: " + deck.get(0));
    for (int index = 1; index < deck.size(); index++) {
      System.out.print(", ");
      System.out.print(deck.get(index));
    }
    System.out.println();
  }

  public static void main(String[] args) throws Exception {
    Random r = initRandom(args); // Do not modify this line
    /*
     * TODO:
     * Implement the game.
     * Feel free to add additional methods.
     * 
     * Please use the provided random number generator r.
     */

    // print the first line
    System.out.println("Welcome to Blackjack!");

    // Initialize the deck by giving cards to the user and the dealer
    int user_card1 = r.nextInt(10) + 2;
    int user_card2 = r.nextInt(10) + 2;
    int dealer_card1 = r.nextInt(10) + 2;
    int dealer_card2 = r.nextInt(10) + 2;

    // Display initial hands
    ArrayList<Integer> user_deck = new ArrayList<>();
    user_deck.add(user_card1);
    user_deck.add(user_card2);
    System.out.println("Your cards are: " + user_deck.get(0) + ", " + user_deck.get(1));

    // dealer deck but not displayed
    ArrayList<Integer> dealer_deck = new ArrayList<>();
    dealer_deck.add(dealer_card1);
    dealer_deck.add(dealer_card2);

    // Game time
    // User's action
    boolean end_of_user_action = false;
    while (end_of_user_action == false) {
      Scanner scanner = new Scanner(System.in);
      System.out.println("Would you like to hit or stand?");
      String response = scanner.nextLine();

      if (response.equals("hit")) {
        user_deck.add(r.nextInt(10) + 2);
        if (sumup(user_deck) > 21) {
          System.out.println("You have bust!");
          end_of_user_action = true;
          user_showhand(user_deck);
          dealer_showhand(dealer_deck);
          System.out.println("Dealer wins!");
        }
      }
      // User choose to stand
      else {
        end_of_user_action = true;
      }
    }

    // Dealer's action
    boolean end_of_dealer_action = false;
    if (sumup(user_deck) > 21) {
      end_of_dealer_action = true;
    }

    // User stands, now dealer's turn
    while (end_of_dealer_action == false) {
      boolean dealer_hit = r.nextBoolean();
      // Deal draws the card
      while (dealer_hit == true) {
        dealer_deck.add(r.nextInt(10) + 2);
        System.out.println("The dealer hits.");

        // Dealer bust
        if (sumup(dealer_deck) > 21) {
          System.out.println("The dealer has bust!");
          dealer_hit = false;
          end_of_dealer_action = true;

          user_showhand(user_deck);
          dealer_showhand(dealer_deck);
          System.out.println("You win!");

        }
        // Did not bust, update decision
        else {
          dealer_hit = r.nextBoolean();
        }
      }

      if (sumup(dealer_deck) > 21) {
        break;
      }

      // Dealer stands
      System.out.println("The dealer stands.");
      end_of_dealer_action = true;

      // Compare
      user_showhand(user_deck);
      dealer_showhand(dealer_deck);

      if (sumup(dealer_deck) == sumup(user_deck)) {
        System.out.println("Tie!");
      } else if (sumup(dealer_deck) > sumup(user_deck)) {
        System.out.println("Dealer wins!");
      } else {
        System.out.println("You win!");
      }
    }
  }
}