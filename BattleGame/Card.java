// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP-102-112 - 2021T1, Assignment 9
 * Name: Ella Wipatene
 * Username: wipateella
 * ID: 300558005
 */

import ecs100.*;
import java.util.*;

/**
 * Card
 * Represent individual Cards
 * Methods:
 *   draw(double left, double top)  draw the card at a position
 *   getRank() and getSuit() return the rank and the suit of the card
 *   getDeck return an ArrayList containing a shuffled deck of 52 cards
 *
 */

public class Card {

    public static final String[] suitNames = {"clubs", "diamonds" ,"hearts" ,"spades"};

    public int rank;
    public String suit;
    private String image;

    /** 
     * Construct a new Card object with the given suit and the given rank
     */
    private Card(String suit, int rank){
        this.suit = suit;
        this.rank = rank;
        this.image = getImage();
    }

    /**
     * Get a new deck of 52 different cards
     * The cards in the deck have been shuffled.
     */
    public static ArrayList<Card> getShuffledDeck(){
        ArrayList<Card> deck = new ArrayList<Card>();
        for (String suitName : suitNames){
            for (int rank = 1; rank <= 13; rank++){
                deck.add(new Card(suitName, rank));
            }
        }
        Collections.shuffle(deck);
        return deck;
    }

    /**
     * Return the suit of the card
     */
    public String getSuit(){
        return suit;
    }

    /**
     * Return the rank of the card
     */
    public int getRank(){
        return rank;
    }

    /**
     * Return the name of the image of the card
     */
    public String getImage(){
        if (this.rank == 1)       return "cards/ace_of_" + this.suit + ".png";
        else if (this.rank < 11)  return "cards/" + this.rank+ "_of_" + this.suit + ".png";
        else if (this.rank == 11) return "cards/jack_of_"  + this.suit + ".png";
        else if (this.rank == 12) return "cards/queen_of_" + this.suit + ".png";
        else                      return "cards/king_of_"  + this.suit + ".png";
    }

    /**
     * Draw the card at the position (left, top)
     */
    public void draw(double left, double top){
        UI.drawImage(this.image, left, top);
    }


}
