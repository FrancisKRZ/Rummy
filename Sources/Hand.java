package Sources;

import Interfaces.*;
import javax.swing.*;

// Hand.java - John K. Estell - 8 May 2003
// last modified: 23 Febraury 2004
// Implementation of a abstract hand of playing cards.
// Uses the Card class.  Requires subclass for specifying
// the specifics of what constitutes the evaluation of a hand
// for the game being implemented.


/**
 * Represents the basic functionality of a hand of cards.
 * Extensions of this class will provide the
 * definition of what constitutes a hand for that game and how hands are
 * compared
 * to one another by overriding the <code>compareTo</code> method.
 * 
 * @author John K. Estell
 * @version 1.0
 */
public class Hand implements HandInterface {

   // Methods for Array List.
   // https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html
   // protected java.util.List hand = new ArrayList<Card>();

   // Changed to reflect p1Hand from Table class
   DefaultListModel<Card> hand = new DefaultListModel<Card>();

   // final int TotalSuits = 4;
   // final int TotalRanks = 13;

   // int HoldSize;
   // final int MaxHoldSize = 9;

   // Add constructor for Hand Pile list to work
   public Hand(){
      hand = new DefaultListModel<Card>();
   }


   // // Getters for Total Suits and Total Ranks
   // public int getTotalSuits(){
   //    return TotalSuits;
   // }

   
   // public int getTotalRanks(){
   //    return TotalRanks;
   // }

   /**
    * Adds a card to this hand.
    * 
    * @param card card to be added to the current hand.
    */

   // Add and make sure to sort!
   @Override
   public void addCard(Card card) {
      // hand.add(card);
      hand.addElement(card);
      // Collections.sort(hand, Collections.reverseOrder());
      this.sort();

   }

   /**
    * Searches for the first instance of a set (3 or 4 Cards of the same rank) in
    * the hand.
    * 
    * @return returns Card [] of Cards found in deck or <code>-null </code> if not
    *         found.
    */

   // Needs minimum 3 of the same type / rank...
   // We'll need a sort function,
   // given that the size of items is short,
   // we can use selection sort
   // since O(n) isn't a big deal here.
   public Card[] findSet() {

      this.sort();
      // Retrieve the hand size and find all the same type.
      // We'll make use of the set, given that's where our cards
      // are stored
  
      final int SIZE = hand.size();
      // Given a sorted list, a+0 , a+1, a+2
      // will be of the same type, at least supposed to be
      // in that sequence, we'll have to loop throughout the hand
      // and see if those three at the minimum are of the same rank.

      // First check if the hand size is at least 3

      if (SIZE < 3 || SIZE > 4) return null;

      char rank;
      
      Set set = new Set('0');
      Card [] cards = new Card[SIZE];


      for (int i = 0; i < SIZE-1; i++){

         rank = hand.get(i).getRank();
         while ( rank == hand.get(i+1).getRank() ){
            set.addCard(hand.get(i));
            cards[i] = set.getCard(i);
         } 

      }

      return cards;
   }

   /**
    * Obtains the card stored at the specified location in the hand. Does not
    * remove the card from the hand.
    * 

    * @param index position of card to be accessed.
    * @return the card of interest, or the null reference if the index is out of
    *         bounds.
    */
   @Override
   public Card getCard(int index) {
      return (Card) hand.get(index);
   }

   /**
    * Removes the specified card from the current hand.
    * 
    * @param card the card to be removed.
    * @return the card removed from the hand, or null if the card
    *         was not present in the hand.
    */
   @Override
   public Card removeCard(Card card) {
      return hand.removeElement(card) ? card : null;
   }

   /**
    * Removes the card at the specified index from the hand.
    * 
    * @param index poisition of the card to be removed.
    * @return the card removed from the hand, or the null reference if
    *         the index is out of bounds.
    */
   @Override
   public Card removeCard(int index) {

      if (index < 0){
         return null;
      }
      else { 
         // hand.remove(index);
         Card c = hand.remove(index);
         this.sort();
         // return (Card) hand.get(index);
         return c;
      }
   
   }

   /**
    * Removes all the cards from the hand, leaving an empty hand.
    */
   public void discardHand() {
      hand.clear();
   }

   /**
    * The number of cards held in the hand.
    * 
    * @return number of cards currently held in the hand.
    */
   @Override
   public int getNumberOfCards() {
      return hand.size();
   }

   /**
    * Sorts the card in the hand.
    * Sort is performed according to the order specified in the {@link Card} class.
    */

   @Override   // O(N^2)
   public void sort(){

      // Sort the cards by their suits...
      SuitSort();

      final int N = hand.size();

      // Store to a subset of suits
      // Then add the fully sorted list to a tmp
      DefaultListModel<Card> tmp = new DefaultListModel<Card> ();
      DefaultListModel<Card> subset = new DefaultListModel<Card> ();

      int k;

      for (int i = 0; i < N; i++){

         k = i;

         // We'll add a subset of the same suits,
         // which'll then be sorted by its rank.
         while ( k < N && hand.get(k).getSuit() == hand.get(i).getSuit() ){

            subset.addElement( hand.get(k) );
            k++;
         }

         // After the subset traverse,
         // We have to update i to follow the k'th index
         // else we'd have an infinite loop.
         i = k;
         i--;
         
         RankSort(subset);

         // Now we'll add the sorted elements in the subset
         // to the sorted list
         for (int z = 0; z < subset.size(); z++){
            tmp.addElement( subset.get(z) );
         }

         // Important to clear the already
         // held subset of same suits
         // else we'd be adding different suits altogether
         // and thus rendering our algorithm useless.
         subset.clear();
      }

      hand.clear();

      // Assigning like these makes the JList dissapear :(
      for (int i = 0; i < tmp.size(); i++){
         hand.addElement( tmp.get(i) );
      }

   }


   public void SuitSort(){

      final int N = hand.getSize();

      for (int i = 0; i < N; i++){

         int min_idx = i;

         for (int k = i+1; k < N; k++){

            if (hand.get(k).getSuit() < hand.get(min_idx).getSuit() ){
               min_idx = k;
            }
         
            Card temp = hand.get(min_idx);
            hand.setElementAt(hand.get(i), min_idx);
            hand.setElementAt(temp, i);

         }
      } // EO ML

   }

   
   public void RankSort(DefaultListModel<Card> h){

      final int N = h.getSize();

      // System.out.println("Before: " + h.toString() );

      for (int i = 0; i < N; i++){

         int min_idx = i;

         for (int k = i+1; k < N; k++){

            if ( h.get(k).getRank() < h.get(min_idx).getRank() ){
               min_idx = k;
            }
         
            Card temp = h.get(min_idx);
            h.setElementAt(h.get(i), min_idx);
            h.setElementAt(temp, i);
         }
      } // EO ML

   }


   /**
    * Checks to see if the hand is empty.
    * 
    * @return <code>true</code> is the hand is empty.
    */
   @Override
   public boolean isEmpty() {
      return hand.isEmpty();
   }

   /**
    * Determines whether or not the hand contains the specified card.
    * 
    * @param card the card being searched for in the hand.
    * @return <code>true</code> if the card is present in the hand.
    */
   @Override
   public boolean containsCard(Card card) {
      return hand.contains(card);
   }

   /**
    * Searches for the first instance of the specified card in the hand.
    * 
    * @param card card being searched for.
    * @return position index of card if found, or <code>-1</code> if not found.
    */
   @Override
   public int findCard(Card card) {
      return hand.indexOf(card);
   }

   /**
    * Compares two hands.
    * 
    * @param otherHandObject the hand being compared.
    * @return < 0 if this hand is less than the other hand, 0 if the two hands are
    *         the same, or > 0 if this hand is greater then the other hand.
    */
   @Override
   public int compareTo(Object otherHandObject) {
      Hand otherHand = (Hand) otherHandObject;
      return evaluateHand() - otherHand.evaluateHand();
   }

   /**
    * Evaluates a hand according to the rules of the dumb card game.
    * Each card is worth its displayed pip value (ace = 1, two = 2, etc.)
    * in points with face cards worth ten points. The value of a hand
    * is equal to the summation of the points of all the cards held in
    * the hand.
    */
    @Override
    public int evaluateHand() {
   
      int value = 0;

      for (int i = 0; i < getNumberOfCards(); i++) {
         Card c = getCard(i);
         int cardValue = Card.getRankIndex(c.getRank()) - Card.getRankIndex('a') + 1;
         if (cardValue > 10)
            cardValue = 10;
         value += cardValue;
      }

      return value;
   }


   public int evaluateMelds() {

      int value = 0;

      for (int i = 0; i < getNumberOfCards(); i++){

         Card c = getCard(i);
         int cardValue = Card.getSuitIndex( c.getSuit() ) - Card.getSuitIndex('c') + 1;
         if (cardValue > 10)
            cardValue = 10;
         value += cardValue;
      }

      return value;
   }


   /**
    * Returns a description of the hand.
    * 
    * @return a list of cards held in the hand.
    */
   @Override
   public String toString() {
      sort();
      return hand.toString();
   }

   /**
    * Replaces the specified card with another card. Only the first
    * instance of the targeted card is replaced. No action occurs if
    * the targeted card is not present in the hand.
    * 
    * @return <code>true</code> if the replacement occurs.
    */
   public boolean replaceCard(Card oldCard, Card replacementCard) {
      int location = findCard(oldCard);
      if (location < 0)
         return false;
      hand.set(location, replacementCard);

      this.sort();

      return true;
   }

   // Copies cards from DLM hand to a temp DLM and return it
   // Used for JList hand pile, in replacement of . 
   public DefaultListModel<Card> getHand() {
   
      return this.hand;
   }


   // The 'AI' will determine if its current hand
   // is superior to its opponent.
   // If it is it shall instantly play its hand,
   // thus instantly winning.
   public boolean PlayBestHand(Hand other){

      if (compareTo(other) > 0 ) return true;
      return false;
   }   

}