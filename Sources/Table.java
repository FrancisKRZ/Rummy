package Sources;

import Interfaces.*;

/**
*	This GUI assumes that you are using a 52 card deck and that you have 13 sets in the deck.
*	The GUI is simulating a playing table
	@author Patti Ordonez
*/
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.Random;

public class Table extends JFrame implements ActionListener {

	final static int numDealtCards = 9;
	// Added
	final static int numSets = 13;
	
	JPanel player1;
	JPanel player2;
	JPanel deckPiles;
	JLabel deck;
	JLabel stack;
	
	Deck cardDeck;
	MyStack stackDeck;

	SetPanel [] setPanels = new SetPanel[13];
	JLabel topOfStack;
	JLabel deckPile;
	JButton p1Stack;
	JButton p2Stack;

	JButton p1Deck;
	JButton p2Deck;

	JButton p1Lay;
	JButton p2Lay;

	JButton p1LayOnStack;
	JButton p2LayOnStack;

	Hand p1Hand;
	Hand p2Hand;
	
	// DefaultListModel<Set> sets = new DefaultListModel<Set>();
	Set p1Set = new Set();
	Set p2Set = new Set();


	// Player turns
	boolean p1Turn = true; boolean p2Turn = false;
	boolean drawFromDeck = false; boolean playerTurn = false;

	JList <Card> p1HandPile;
	JList <Card> p2HandPile;


	// Virtual Players
	int VirtualPlayers = 0;


	JButton p1FinishTurn;
	JButton p2FinishTurn;

	JButton p1PlaySet;
	JButton p2PlaySet;



	// Copies added card(s) of hand
	// Object addedCards = null;
	DefaultListModel<Card> addedCards = new DefaultListModel<Card> ();

	// List of discarded cards
	DefaultListModel<Card> discardedCards = new DefaultListModel<Card> ();
	// Object [] discardedCards = new Object [9];
	int discardCtr = 0;

	private void deal(Card [] cards) {

		for(int i = 0; i < cards.length; i ++)
			cards[i] = (Card)cardDeck.dealCard();
	}


	
	// When two Virtual Player's , calls this to automatically play the game
	public void TwoVP(){

		// System.out.println("TwoVP Called");

		while (p1Hand.getNumberOfCards() > 0 && p2Hand.getNumberOfCards() > 0 && !cardDeck.isEmpty() ){


			System.out.println("PLAYER 1:");
			Virtualized(p1Hand);
			printCards();
			System.out.println("HAND NOW: " + p1Hand.toString() );


			System.out.println("PLAYER 2:");
			Virtualized(p2Hand);
			printCards();
			System.out.println("HAND NOW: " + p2Hand.toString() );

		}

		if (cardDeck.isEmpty()){
			Gameover();
		}

		if (p1Hand.getNumberOfCards() < 1 || p2Hand.getNumberOfCards() < 1){
			Gameover();
		}
	
	}

	// Actions for Virtual Players
	private void Virtualized(Hand player){

		// 0 to 1	The AI will do either of the 2 Draw actions
		// 			and lay card(s)
		Random rand = new Random();
		int rng = rand.nextInt(2);	// [0 , n)

		Card tmp = player.getCard(0);
		player.removeCard(0);
		stackDeck.addCard(tmp);
		topOfStack.setIcon(tmp.getCardImage());

		discardedCards.addElement(tmp);
		
		Card card = cardDeck.dealCard();
		player.addCard(card);
		addedCards.addElement(card);

		Play(player, rng);

	}


	// The main table where everything happens
	public Table() {
		
		super("The Card Game of the Century");
		// System.out.println("Welcome to Rummy!!!");

		// Variable mas chiquitas porfis
		// Gets the Number of Players in the Current Session of The Rummy Game
		Scanner numOfPlayers = new Scanner(System.in);
		// Validates the Number of Players in the Current Session of The Rummy Game
		boolean validate = false;


		while( !validate ){

			System.out.println("Input the number of AI players: [0,1,2] ");
			VirtualPlayers = numOfPlayers.nextInt();
			System.out.println(" ");

			if (VirtualPlayers < 0 || VirtualPlayers > 2){
				validate = false;
				VirtualPlayers = 0;
			} else {
				validate = true;
			}
		}

		numOfPlayers.close();


		setLayout(new BorderLayout());
		setSize(1200,700);


		cardDeck = new Deck();

		for(int i = 0; i < Card.suit.length; i++){
			for(int j = 0; j < Card.rank.length; j++){
				Card card = new Card(Card.suit[i],Card.rank[j]);
				cardDeck.addCard(card);
			}
		}

		// Pile (?)
		cardDeck.shuffle();
		stackDeck = new MyStack();

		JPanel top = new JPanel();

		for (int i = 0; i < Card.rank.length;i++)
			setPanels[i] = new SetPanel(Card.getRankIndex(Card.rank[i]));


		top.add(setPanels[0]);
		top.add(setPanels[1]);
		// top.add(setPanels[2]);
		// top.add(setPanels[3]);

		player1 = new JPanel();

		player1.add(top);




		add(player1, BorderLayout.NORTH);
		JPanel bottom = new JPanel();


		bottom.add(setPanels[4]);
		bottom.add(setPanels[5]);
		bottom.add(setPanels[6]);
		bottom.add(setPanels[7]);
		bottom.add(setPanels[8]);

		player2 = new JPanel();




		player2.add(bottom);
		add(player2, BorderLayout.SOUTH);


		JPanel middle = new JPanel(new GridLayout(1,3));

		p1Stack = new JButton("Stack");
		p1Stack.addActionListener(this);
		p1Deck = new JButton("Deck");
		p1Deck.addActionListener(this);
		p1Lay = new JButton("Lay");
		p1Lay.addActionListener(this);
		p1LayOnStack = new JButton("LayOnStack");
		p1LayOnStack.addActionListener(this);


		// Action listeners for finish turn
		p1FinishTurn = new JButton("End Turn");
		p1FinishTurn.addActionListener(this);

		// Action listeners for Play sets
		p1PlaySet = new JButton("Play Melds");
		p1PlaySet.addActionListener(this);
		p2PlaySet = new JButton("Play Melds");
		p2PlaySet.addActionListener(this);

		Card [] cardsPlayer1 = new Card[numDealtCards];
		deal(cardsPlayer1);
		
		p1Hand = new Hand();

		// System.out.println("Initial Player 1: ");

		for(int i = 0; i < cardsPlayer1.length; i++){
			
			Card card = cardDeck.dealCard();
			p1Hand.addCard(card);

		}

		p1HandPile = new JList<Card>(p1Hand.getHand());


		middle.add(new HandPanel("Player 1", p1HandPile, p1Stack, p1Deck, p1Lay, p1LayOnStack, p1FinishTurn, p1PlaySet));
		// middle.add(new HandPanel("Player 1", p1HandPile, p1Stack, p1Deck, p1Lay, p1LayOnStack, p1PlaySet));



		deckPiles = new JPanel();
		deckPiles.setLayout(new BoxLayout(deckPiles, BoxLayout.Y_AXIS));
		deckPiles.add(Box.createGlue());
		JPanel left = new JPanel();
		left.setAlignmentY(Component.CENTER_ALIGNMENT);


		stack = new JLabel("Stack");
		stack.setAlignmentY(Component.CENTER_ALIGNMENT);

		left.add(stack);
		topOfStack = new JLabel();
		topOfStack.setIcon(new ImageIcon(Card.directory + "blank.gif"));
		topOfStack.setAlignmentY(Component.CENTER_ALIGNMENT);
		left.add(topOfStack);
		deckPiles.add(left);
		deckPiles.add(Box.createGlue());

		JPanel right = new JPanel();
		right.setAlignmentY(Component.CENTER_ALIGNMENT);

		deck = new JLabel("Deck");

		deck.setAlignmentY(Component.CENTER_ALIGNMENT);
		right.add(deck);
		deckPile = new JLabel();
		deckPile.setIcon(new ImageIcon(Card.directory + "b.gif"));
		deckPile.setAlignmentY(Component.CENTER_ALIGNMENT);
		right.add(deckPile);
		deckPiles.add(right);
		deckPiles.add(Box.createGlue());
		middle.add(deckPiles);


		p2Stack = new JButton("Stack");
		p2Stack.addActionListener(this);
		p2Deck = new JButton("Deck");
		p2Deck.addActionListener(this);
		p2Lay = new JButton("Lay");
		p2Lay.addActionListener(this);
		p2LayOnStack = new JButton("LayOnStack");
		p2LayOnStack.addActionListener(this);

		// p2 Buttons 
		p2FinishTurn = new JButton("End Turn");
		p2FinishTurn.addActionListener(this);


		Card [] cardsPlayer2 = new Card[numDealtCards];
		deal(cardsPlayer2);

		p2Hand = new Hand();

		for(int i = 0; i < cardsPlayer2.length; i++){
		
			Card card = cardDeck.dealCard();
			p2Hand.addCard(card);

		}

		p2HandPile = new JList<Card>(p2Hand.getHand());

		middle.add(new HandPanel("Player 2", p2HandPile, p2Stack, p2Deck, p2Lay, p2LayOnStack, p2FinishTurn, p2PlaySet));
		// middle.add(new HandPanel("Player 2", p2HandPile, p2Stack, p2Deck, p2Lay, p2LayOnStack, p2PlaySet));


		add(middle, BorderLayout.CENTER);

		JPanel leftBorder = new JPanel(new GridLayout(2,1));


		setPanels[9].setLayout(new BoxLayout(setPanels[9], BoxLayout.Y_AXIS));
		setPanels[10].setLayout(new BoxLayout(setPanels[10], BoxLayout.Y_AXIS));
		leftBorder.add(setPanels[9]);
		leftBorder.add(setPanels[10]);
		add(leftBorder, BorderLayout.WEST);

		JPanel rightBorder = new JPanel(new GridLayout(2,1));

		setPanels[11].setLayout(new BoxLayout(setPanels[11], BoxLayout.Y_AXIS));
		setPanels[12].setLayout(new BoxLayout(setPanels[12], BoxLayout.Y_AXIS));
		rightBorder.add(setPanels[11]);
		rightBorder.add(setPanels[12]);
		add(rightBorder, BorderLayout.EAST);




		// After all cards are dealt.
		System.out.println("INITIAL PLAYER 1: " + p1Hand.toString().toUpperCase() );
		System.out.println("INITIAL PLAYER 2: " + p2Hand.toString().toUpperCase() );

		if (VirtualPlayers == 2){
			TwoVP();
		}


		// Puts a card on stack at start of the game.
		Card k = cardDeck.dealCard();
		stackDeck.addCard( (Card) k );

		if ( k != null) 
			topOfStack.setIcon( k.getCardImage() );
		else
			topOfStack.setIcon( new ImageIcon(Card.directory + "blank.gif") );



		disableP1();
		p1Deck.setEnabled(true);
		disableP2();
		p2Deck.setEnabled(false);

	}


	// When a condition that triggers the end of game, ends the game
	// i.e hand / deck are empty
	public void Gameover() {

		System.out.println("GAME HAS ENDED!");
		int p1 = p1Hand.evaluateHand();
		int p2 = p2Hand.evaluateHand();


		System.out.println("PLAYER 1 TOTAL POINTS: " + p1);
		System.out.println("PLAYER 2 TOTAL POINTS: " + p2);

		if (p1 > p2){
			System.out.println("PLAYER 2 WON");
		}

		else if (p2 > p1){
			System.out.println("PLAYER 1 WON");
		}
		else {
			System.out.println("IT IS A TIE!");
		}

		// Terminate the program
		System.exit(0);
	}


	// Prints the added / discarded cards of that current play
	private void printCards(){

		System.out.println("ADDED: "     + addedCards.toString().toUpperCase() );
		System.out.println("DISCARDED: " + discardedCards.toString().toUpperCase() );

		addedCards.clear();
		discardedCards.clear();

	}

	// Announces the player turn ending
	private void PrintPlayerTurn(){
		
		if(p1Turn){
			System.out.println("PLAYER 1 ");
			p1Turn = false;
			p2Turn = true;
		} else {
			System.out.println("PLAYER 2 ");
			p1Turn = true;
			p2Turn = false;
		}

		System.out.println(" FINISHED THEIR TURN!\n");

		return;
	}

	// Changes the player turn active buttons
	private void enableP1(){

		p1Deck.setEnabled(false);
		p1Stack.setEnabled(( !stackDeck.isEmpty() ));

		p1Lay.setEnabled(true);
		p1LayOnStack.setEnabled( true);
		p1PlaySet.setEnabled(true);

		return;
	}

	private void disableP1(){

		p1Deck.setEnabled(false);
		p1Stack.setEnabled(false);

		p1Lay.setEnabled(false);
		p1LayOnStack.setEnabled(false);
		p1PlaySet.setEnabled(false);

		return;
	}

	private void enableP2(){

		p2Deck.setEnabled(false);
		p2Stack.setEnabled(( !stackDeck.isEmpty() ));

		p2Lay.setEnabled(true);
		p2LayOnStack.setEnabled( true);
		p2PlaySet.setEnabled(true);

		return;
	}

	private void disableP2(){

		p2Deck.setEnabled(false);
		p2Stack.setEnabled(false);

		p2Lay.setEnabled(false);
		p2LayOnStack.setEnabled(false);
		p2PlaySet.setEnabled(false);

		return;
	}



	// Player Draw from Decks
	// Given a Hand (i.e p1Hand)
	// Picks a card from the deck after evaluating certain conditions
	private void ActionDrawFromDeck(Hand player){

		if (player.getNumberOfCards() > 10){
			System.out.println("TOO MANY CARDS D: ");
			return;
		}

		
		 Card card = cardDeck.dealCard();
		if (card == null){
			return;
		}


		addedCards.addElement(card);
		player.addCard(card);


		if (cardDeck.isEmpty()){
			deckPile.setIcon(new ImageIcon(Card.directory + "blank.gif"));
			Gameover();
		}

	}

	// Player Draw from Stacks
	// Given a hand (i.e p1Hand)
	// Picks a card from the stack after evaluating certain conditions
	private void ActionDrawFromStack(Hand player){

		if (player.getNumberOfCards() >= 10 || stackDeck.isEmpty() ){
			System.out.println("TOO MANY CARDS D: OR EMPTY D:");
			return;
		}
	
		Card card = stackDeck.peek();

		if (card == null){
			topOfStack.setIcon(new ImageIcon(Card.directory + "blank.gif"));
		} else {
			topOfStack.setIcon( card.getCardImage() );
		}

		addedCards.addElement(card);
		player.addCard(card);

	}


	// Player lays card for real players
	// Given a hand (i.e p1Hand) and its hand pile (i.e p1HandPile).
	// Lays down a / several selected card(s) to the table,
	// if the lay is a play / run, evaluates the ranks of the play
	// and ends the game.
	private void ActionLay(Hand player, JList<Card> handPile){

		List<Card> cards = handPile.getSelectedValuesList();
		
		if (cards.isEmpty()) return;

		if (cards.size() > 2 && cards.size() < 5){

			boolean valid = true;
			Card card = cards.get(0);
			char rankCard = card.getRank();

			for (int i = 0; i < cards.size(); i++){

				if (rankCard != cards.get(i).getRank()){
					System.out.println("ALL CARDS MUST BE SAME RANK FOR THIS ACTION");
					return;
				}
				addedCards.addElement(cards.get(i));
			}

			if (valid){
				ActionPlayRuns();
			}
		}
		
		for (int i = 0; i < cards.size(); i++) { 

			Card card = cards.get(i);
			layCard(card);

			discardedCards.addElement(card);
			player.removeCard(card);
		}


		if (player.isEmpty()) Gameover();
	}


	// Used only for Virtual Players
	// Lays down a card to the table
	private void ActionLayVP(Hand player){

		if (player.getNumberOfCards() == 0) Gameover();

		Card card = player.getCard(0);
		player.removeCard(0);
		layCard(card);
		discardedCards.addElement(card);
	}



	// Player lays card on the Stack Deck
	// Given a hand (i.e p1Hand) and a hand pile (i.e p1HandPile)
	// Lays down the selected card from the hand to the stack.
	private void ActionLayOnStack(Hand player, JList<Card> handPile){

		if (player.isEmpty()) Gameover();
		int [] num  = handPile.getSelectedIndices();

		if (num.length == 1) {

			Object obj = handPile.getSelectedValue();
			discardedCards.addElement( (Card) obj );

			if (obj != null){
				player.removeCard( (Card) obj );
				Card card = (Card) obj;


				stackDeck.addCard(card);
				topOfStack.setIcon(card.getCardImage());

			}
		}

	}

	// Lay on Stack for Virtual Player(s)
	// Given a hand (i.e p2Hand), lays down a card to the stack deck.
	private void ActionLayOnStackVP(Hand player){

		if (player.getNumberOfCards() == 0) Gameover();

		Card card = player.getCard(0);
		player.removeCard(0);
		stackDeck.addCard(card);
		topOfStack.setIcon(card.getCardImage());
		discardedCards.addElement(card);
	}


	// Evaluates by Rank
	// Calls Gameover, which evaluates the two hands by ranks.
	private void ActionPlayRuns(){
		Gameover();
	}

	// Evaluates by Suits
	// Similarly to ActionPlayRuns, evaluates the two hands by suits.
	private void ActionPlayMelds(Hand player, JList<Card> handPile){

		
		List<Card> cards = handPile.getSelectedValuesList();
		if (cards.isEmpty()) return;

		if (cards.size() > 3 && cards.size() < 5){

			boolean valid = true;
			Card card = cards.get(0);
			char rankCard = card.getRank();

			for (int i = 0; i < cards.size(); i++){

				if (rankCard != cards.get(i).getSuit()){
					System.out.println("ALL CARDS MUST BE SAME SUIT FOR THIS ACTION");
					return;
				}
				addedCards.addElement(cards.get(i));
			}

			if (valid){
				ActionPlayRuns();
			}
		}
		
		for (int i = 0; i < cards.size(); i++) { 

			Card card = cards.get(i);
			layCard(card);

			discardedCards.addElement(card);
			player.removeCard(card);
		}

		printCards();

		if (player.isEmpty()) Gameover();
	}

	// Where the Virtual Player(s) decision progress takes place.
	// Given a random action, they'll lay their cards or play their hands
	// after picking up a card from either the stack or deck.
	private void Play(Hand player, int randAction){

		// Covers all 4 main actions + additional play runs / set
		// System.out.println("We're inside Play()");

		if (player.isEmpty()) Gameover();

		switch(randAction){

			case 0:
				ActionLayOnStackVP(player);
				return;

			case 1:
				ActionLayVP(player);
				return;

			default:
				System.out.println("");
				break;
		}
		
		return;
	}


	// The action listeners lies here, used if 0-1 players are virtuals.
	public void actionPerformed(ActionEvent e){

		Object src = e.getSource();

		// Draw from Deck Actions
		if(p1Deck == src|| p2Deck == src){

			if (src == p1Deck && p1Hand.getNumberOfCards() < 10 ){

				if (addedCards.size() > 0){
					System.out.println("YOU'VE ALREADY PICKED A CARD!");
					p1Deck.setEnabled(false);
				} else {
					ActionDrawFromDeck(p1Hand);
					enableP1();
				}
			}

			if (src == p2Deck && p2Hand.getNumberOfCards() < 10 ){

				if (addedCards.size() > 0){
					System.out.println("YOU'VE ALREADY PICKED A CARD!");
					p2Deck.setEnabled(false);
				} else {
					ActionDrawFromDeck(p2Hand);
					enableP2();
				}
			}
		}


		// Draw from Stack Actions
		if(p1Stack == src || p2Stack == src){

			if (p1Stack == src && p1Hand.getNumberOfCards() < 10 ){
				ActionDrawFromStack(p1Hand);
			}

			if (src == p2Stack && p2Hand.getNumberOfCards() < 10 ){
				ActionDrawFromStack(p2Hand);
			}
		}

		// Lay Actions
		if(p1Lay == src || p2Lay == src){
			
			if (p1Lay == src){
				ActionLay(p1Hand, p1HandPile);		
			}

			if (src == p2Lay){
				ActionLay(p2Hand, p2HandPile);
			}
		}


		if (p1LayOnStack == src || p2LayOnStack == src){

			if (p1LayOnStack == src){
				ActionLayOnStack(p1Hand, p1HandPile);
			}

			if (src == p2LayOnStack){
				ActionLayOnStack(p2Hand, p2HandPile);
			}
		}


		if (p1FinishTurn == src || p2FinishTurn == src) {

			printCards();

			if (p1FinishTurn == src){
				disableP1();
				p2Deck.setEnabled(true);
			}
			if (p2FinishTurn == src){
				disableP2();
				p1Deck.setEnabled(true);
			}


			if (VirtualPlayers == 1){
				// System.out.println("VT");
				PrintPlayerTurn();
				Virtualized(p2Hand);
				printCards();
				disableP1();
				p2Deck.setEnabled(true);
				PrintPlayerTurn();
			} else {
				// System.out.println("NVT");
				PrintPlayerTurn();
				// disableP1();
				// enableP2();
			}
		} // EO

	
	}

	// public static void main(String args[])
	// {
	// 	Table t = new Table();
	// 	t.setVisible(true);
	// 

	void layCard(Card card)
	{
		char rank = card.getRank();
		char suit = card.getSuit();
		int suitIndex =  Card.getSuitIndex(suit);
		int rankIndex =  Card.getRankIndex(rank);
		//setPanels[rankIndex].array[suitIndex].setText(card.toString());
		// System.out.println("laying " + card);
		setPanels[rankIndex].array[suitIndex].setIcon(card.getCardImage());
	}

}

class HandPanel extends JPanel {

	public HandPanel(String name,JList<Card> hand, JButton stack, JButton deck, JButton lay, JButton layOnStack, JButton FinishTurn, JButton playSet) {
	// public HandPanel(String name,JList<Card> hand, JButton stack, JButton deck, JButton lay, JButton layOnStack, JButton playSet) {

		//model = hand.createSelectionModel();

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//		add(Box.createGlue());
		JLabel label = new JLabel(name);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(label);
		stack.setAlignmentX(Component.CENTER_ALIGNMENT);
//		add(Box.createGlue());
		add(stack);
		deck.setAlignmentX(Component.CENTER_ALIGNMENT);
//		add(Box.createGlue());
		add(deck);
		lay.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(lay);
		layOnStack.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(layOnStack);

		// playSet.setAlignmentX(Component.CENTER_ALIGNMENT);
		// add(playSet);

		FinishTurn.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(FinishTurn);

		add(Box.createGlue());
		add(hand);
		add(Box.createGlue());
	}

}

class SetPanel extends JPanel {
	private Set data;
	JButton [] array = new JButton[4];

	public SetPanel(int index)
	{
		super();
		data = new Set(Card.rank[index]);

		for(int i = 0; i < array.length; i++){
			array[i] = new JButton("   ");
			add(array[i]);
		}
	}


}