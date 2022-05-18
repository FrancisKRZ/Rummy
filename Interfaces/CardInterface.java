package Interfaces;

import Interfaces.*;
import Sources.*;
import javax.swing.*;


public interface CardInterface extends Comparable {


   final static char[] suit = {'c','d','h','s'};
   final static char [] rank = {'a','2','3','4','5','6','7','8','9','t','j','q','k'};
   final public static String directory = "cards/";

  /**
   * Creates a new playing card initialize the three instance data members of this class
   * @param suit the suit value of this card.
   * @param rank the rank value of this card.
   * @param cardFace the face image of this card.
   */
  // public Card( char suit, char rank, ImageIcon cardFace );
   
  /**
   * Creates a new playing card initializin the ImageIcon
   * @param suit the suit value of this card.
   * @param rank the rank value of this card
   */
 //  public Card(char suit,char rank);
  /**
   * Returns the Index of the Suit in the defined static array suit
   * @param suit the suit value of this card.
   *
   */
 // public static int getSuitIndex(char suit);
  /**
   * Returns the Index of the rank in the defined static array rank
   * @param rank the rank value of this card
   */
//	public static int getRankIndex(char rank);

	  /**
   * Returns a String iby the name of the directory named above
   * concatonated with the toString method of Card and ".gif"
   */
	public String getImageFile();


  /**
   * Returns the suit of the card.
   * @return a char representing the suit value of the card.
   */
   public char getSuit();


  /**
   * Returns the rank of the card.
   * @return a Rank constant representing the rank value of the card.
   */
   public char getRank();


  /**
   * Returns the graphic image of the card.
   * @return an icon containing the graphic image of the card.
   */
   public ImageIcon getCardImage();


  /**
   * Returns a two character String with rank being represented by one of the following chars -  a,1,2,3,4,5,6,7,8,9,t,j,q,k -
   * and suit being represented by one of the following chars - c,d,h,s
   * @return the name of the card.
   */
   public String toString();

  /**
   * Compares two cards for the purposes of sorting.
   * Cards should be ordered by their rank index as defined in the char static array named rank.
   * @param otherCardObject the other card
   * @return a negative integer, zero, or a positive integer if this card is
   * less than, equal to, or greater than the referenced card.
   */
   public int compareTo( Object otherCardObject );


}
