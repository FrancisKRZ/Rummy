// package Interfaces;
/**
 * Represents the basic functionality of a hand of cards.
 * Extensions of this class will provide the
 * definition of what constitutes a hand for that game and how hands are
 * compared
 * to one another by overriding the <code>compareTo</code> method.
 */

public interface HandInterface extends Comparable {

  /**
   * Adds a card to this hand.
   * 
   * @param card card to be added to the current hand.
   */
  public void addCard(Card card);

  /**
   * Obtains the card stored at the specified location in the hand. Does not
   * remove the card from the hand.
   * 
   * @param index position of card to be accessed.
   * @return the card of interest, or the null reference if the index is out of
   *         bounds.
   */
  public Card getCard(int index);

  /**
   * Removes the specified card from the current hand.
   * 
   * @param card the card to be removed.
   * @return the card removed from the hand, or null if the card
   *         was not present in the hand.
   */
  public Card removeCard(Card card);

  /**
   * Removes the card at the specified index from the hand.
   * 
   * @param index poisition of the card to be removed.
   * @return the card removed from the hand, or the null reference if
   *         the index is out of bounds.
   */
  public Card removeCard(int index);

  /**
   * The number of cards held in the hand.
   * 
   * @return number of cards currently held in the hand.
   */
  public int getNumberOfCards();

  /**
   * Sorts the card in the hand.
   * Sort is performed according to the order specified in the {@link Card} class.
   */
  public void sort();

  /**
   * Checks to see if the hand is empty.
   * 
   * @return <code>true</code> is the hand is empty.
   */
  public boolean isEmpty();

  /**
   * Determines whether or not the hand contains the specified card.
   * 
   * @param card the card being searched for in the hand.
   * @return <code>true</code> if the card is present in the hand.
   */
  public boolean containsCard(Card card);

  /**
   * Searches for the first instance of the specified card in the hand.
   * 
   * @param card card being searched for.
   * @return position index of card if found, or <code>-1</code> if not found.
   */
  public int findCard(Card card);

  /**
   * Searches for the first instance of a set (3 or 4 Cards of the same rank) in
   * the hand.
   * 
   * @return returns Card [] of Cards found in deck or <code>-null </code> if not
   *         found.
   */
  // public Card [] findSet( );

  /**
   * Compares two hands.
   * 
   * @param otherHandObject the hand being compared.
   * @return < 0 if this hand is less than the other hand, 0 if the two hands are
   *         the same, or > 0 if this hand is greater then the other hand.
   */
  public int compareTo(Object otherHandObject);

  /**
   * Evaluates the hand. Must be defined in the subclass that implements the hand
   * for the game being written by the client programmer.
   * 
   * @return an integer corresponding to the rating of the hand.
   */
  // not sure we have to do this.....because we are just laying down the
  // cards.....
  public int evaluateHand();

  /**
   * Returns a description of the hand.
   * 
   * @return a list of cards held in the hand.
   */
  public String toString();

}
