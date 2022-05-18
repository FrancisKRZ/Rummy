package Sources;

import Interfaces.*;
import java.util.*;

public class Deck {

   // https://docs.oracle.com/javase/8/docs/api/java/util/LinkedList.html
   java.util.LinkedList deck;
   private int index;


  /**
   * Creates an empty deck of cards.
   */
   public Deck() {

         deck = new LinkedList();
         // Hand H = new Hand();
         // Card C = new Card('0','0');

      //   final static char[] suit = { 'c', 'd', 'h', 's' };
     //    final static char[] rank = { 'a', '2', '3', '4', '5', '6', '7', '8', '9', 't', 'j', 'q', 'k' };
     //     4 Suits, 13 Ranks

         // for (int i = 0; i < H.getTotalSuits(); i++){

         //    for (int k = 0; k < H.getTotalRanks(); k++){

         //       C = new Card(Card.suit[i], Card.rank[k]);
         //       deck.add(C);
         //    }
         // }

         Collections.shuffle(deck);

      }

   public Card peek() {

      return deck.size() > 0 ? (Card) deck.getLast() : null;
	}

   public void addCard( Card card ) {
      deck.add( card );
   }


   public int getSizeOfDeck() {
      return deck.size();
   }

   public Card dealCard() {
	 if ( deck.size() == 0 )
         return null;
      else
		return (Card) deck.removeFirst();
   }

   
   public Card removeCard() {
 
	if (deck.size() == 0)
         return null;
      else{
		
         return (Card) deck.removeLast( );
	}
   }


  /**
   * Shuffles the cards present in the deck.
   */
   public void shuffle() {
      Collections.shuffle( deck );
   }


  /**
   * Looks for an empty deck.
   * @return <code>true</code> if there are no cards left to be dealt from the deck.
   */
   public boolean isEmpty() {
		return deck.size() == 0;
   }



  /**
   * Restores the deck to "full deck" status.
   */
  public void restoreDeck() {
	//not sure if kosher
      deck.removeAll(deck);
   }
   

}
