// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP-102-112 - 2021T1, Assignment 9
 * Name: Ella Wipatene
 * Username: wipateella
 * ID: 300558005
 */

// Challenge version
import ecs100.*;
import java.util.*;
import java.io.*;
import java.awt.Color;

public class FullBattleGame{
    
    private ArrayList<Card> deck = Card.getShuffledDeck();          // the deck (a list of Cards)
    private ArrayList<Card> handComputer = new ArrayList<Card>();  // the list of Cards that the computer has played
    private ArrayList<Card> handPlayer = new ArrayList<Card>();    // the list of Cards that the player has played
    
    public static final int CARD_OVERLAP = 15;
    
    public void restart(){
        deck.clear();
        handComputer.clear();
        handPlayer.clear(); 
        deck = Card.getShuffledDeck();  
        for (int i = 0; i < deck.size(); i++){
            if (i % 2 == 0){
                handComputer.add(deck.get(i)); 
            } else{
                handPlayer.add(deck.get(i));
            }
        }
        drawHand(); 
    }
    
    public void playBattle(){
        Card computer_card = handComputer.get(0); 
        handComputer.remove(0); 
        Card player_card = handPlayer.get(0); 
        handPlayer.remove(0); 
        
        drawBattle(computer_card,player_card); 
        UI.sleep(1000); 
        
        if (computer_card.getRank() > player_card.getRank()){
            handComputer.add(computer_card); 
            handComputer.add(player_card); 
        } else if (computer_card.getRank() < player_card.getRank()){
            handPlayer.add(computer_card); 
            handPlayer.add(player_card); 
        } else if (computer_card.getRank() == player_card.getRank()){
            handComputer.add(computer_card); 
            handPlayer.add(player_card);
            war(); 
        }
        
        if (handComputer.size() == deck.size() || handPlayer.size() == deck.size()){
            endGame(); 
        } 
        
        drawHand(); 
    }
    
    public void war(){
        Card comp_one = handComputer.get(0); 
        handComputer.remove(0); 
        Card comp_two = handComputer.get(0);
        handComputer.remove(0);
        Card comp_three = handComputer.get(0);
        handComputer.remove(0); 
        Card comp_four = handComputer.get(0); 
        handComputer.remove(0); 
        
        Card play_one = handPlayer.get(0); 
        handPlayer.remove(0); 
        Card play_two = handPlayer.get(0);
        handPlayer.remove(0);
        Card play_three = handPlayer.get(0);
        handPlayer.remove(0); 
        Card play_four = handPlayer.get(0); 
        handPlayer.remove(0); 
        
        UI.clearGraphics(); 
        drawHand(); 
        
        for (int i = 0; i < 3; i++){
            UI.drawImage("cards/back.png", 25 + i*CARD_OVERLAP, 250);
        }
        comp_four.draw(150, 250); 
        for (int i = 0; i < 3; i++){
            UI.drawImage("cards/back.png", 275 + i*CARD_OVERLAP, 250);
        }
        play_four.draw(400, 250); 
        
        if (comp_four.getRank() > play_four.getRank()){
            handComputer.add(comp_one); 
            handComputer.add(comp_two); 
            handComputer.add(comp_three); 
            handComputer.add(comp_four); 
            handComputer.add(play_one); 
            handComputer.add(play_two);
            handComputer.add(play_three);
            handComputer.add(play_four); 
        } else if (comp_four.getRank() < play_four.getRank()){
            handPlayer.add(comp_one); 
            handPlayer.add(comp_two); 
            handPlayer.add(comp_three); 
            handPlayer.add(comp_four); 
            handPlayer.add(play_one); 
            handPlayer.add(play_two);
            handPlayer.add(play_three);
            handPlayer.add(play_four); 
        } else if (comp_four.getRank() == play_four.getRank()){ // returns the cards and wars again
            handPlayer.add(play_one); 
            handPlayer.add(play_two);
            handPlayer.add(play_three);
            handPlayer.add(play_four);
            handComputer.add(comp_one); 
            handComputer.add(comp_two); 
            handComputer.add(comp_three); 
            handComputer.add(comp_four);
            war(); 
        }
        
        UI.sleep(1500); 
        drawHand(); 
    }
    
    public void drawHand(){
        UI.clearGraphics(); 
        for (int i = 0; i < handComputer.size(); i++){
            UI.drawImage("cards/back.png", 25 + i*CARD_OVERLAP, 10); 
        }
        for (int i = 0; i < handPlayer.size(); i++){
            UI.drawImage("cards/back.png", 25 + i*CARD_OVERLAP, 500); 
        }
        
        UI.setFontSize(20);
        UI.setColor(Color.black); 
        UI.drawString("Computer Hand Size: " + String.valueOf(handComputer.size()),800, 25); 
        UI.drawString("Player Hand Size: " + String.valueOf(handPlayer.size()), 800, 600); 
    }
    
    public void drawBattle(Card computer, Card player){
        computer.draw(250, 250); 
        player.draw(150, 250); 
    }
    
    /**
     * Displays a win/lose message
     */
    public void endGame(){
        UI.setFontSize(40);
        UI.setColor(Color.red);
        if (this.handComputer.size() < this.handPlayer.size()){
            UI.drawString("YOU WIN!!!", 600, 300);
        }
        else{
            UI.drawString("YOU LOSE", 600, 300);
        }
        UI.sleep(3000);
        this.restart();
    }
    
    /**
     * Set up the user interface
     */
    public void setupGUI(){
        UI.addButton("Battle", this::playBattle);
        UI.addButton("Restart", this::restart);
        UI.addButton("Quit", UI::quit);
        UI.setWindowSize(1100,650);
        UI.setDivider(0.0);

    }

    public static void main(String[] args){
        FullBattleGame bg = new FullBattleGame();
        bg.setupGUI();
        bg.restart();
    }   









}
