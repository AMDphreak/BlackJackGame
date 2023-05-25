package BlackJackGame;
import java.util.ArrayList;

public class Hand {
	ArrayList<Card> hand = new ArrayList<Card>();

	Hand() {
		// A hand is initially empty....You start the game with no cards
	}

	// @param Card adds a card.
	public void add(Card c) {
		hand.add(c);
	}
	
	// @param index get the n'th card in the hand
	public Card card(int index) {
		return hand.get(index);
	}
	
	public int size() {
		return hand.size();
	}
	
	// print all of the cards in a comma-separated list. returns a string. does not actually
	// print anything.
	public String toString() {
		String s = ""; // Fill this string up with our card name and suit as we iterate through
						// the hand.
		int cardIndex = 0;
		for(Card c : hand) { // enhanced for loop. for each Card in hand
			s += c.toString(); // get the card's description and stick it in the string
			if(cardIndex < hand.size() - 1) {
				// add a comma between multiple items, but only if it's not the last item.
				s += ", ";
			}
			cardIndex++;
		}
		return s;
	}

	// Add up the card values!
	// @return The sum of the cards, counting an Ace as 11 if the total would be under 21, otherwise
	//   treats the Ace as a 1.
	public int sum() {
		int runningTotal = 0; // keep track of total
		for (Card card : hand) { // enhanced for loop goes through list
			int cardValue;
			// check the card for an Ace and decide its value
			if(card.name() != null) { // if the card doesn't have a name, getName will return null
				if(card.name() == "Ace") { // Ace
					cardValue = 11;
					if (runningTotal + 11 > 21) {
						cardValue = 1;
					}
				} else { // King, Queen, or Jack
					cardValue = card.value();
				}
			} else {
				cardValue = card.value();
			}
			// add the new card's value
			runningTotal = runningTotal + cardValue;
		}
		return runningTotal;
	}
}
