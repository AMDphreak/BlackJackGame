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

	public boolean notAllAcesAre1() {
		boolean elevensExist = false;
		for (Card card : hand) {
			elevensExist = elevensExist || ( card.value() != 11 );
		}
		return elevensExist;
	}

	// Add up the card values!
	// @return The sum of the cards, counting an Ace as 11 if the total would be under 21, otherwise
	//   treats the Ace as a 1.
	public int sum() {
		int runningTotal = 0; // keep track of total
		for (Card card : hand) { // enhanced for-loop looks at each card in hand
			// add up the card values.
			runningTotal += card.value();

			// check the stack for Aces that are making the hand bust.
			// If the hand is busting, set an Ace's value to 1.
			if (runningTotal > 21 && notAllAcesAre1()) {
				// go back through, and set the value of an Ace to 1.
				// this works even if the user has more than one Ace card,
				// because it will only set a single card then break the
				// loop. If a user has a hand with 5, Ace, Ace, then Aces get set to 1
				// one at a time, until the runningTotal is <= 21
				for (Card c : hand) {
					if (runningTotal > 21) {
						if (c.value() == 11) {
							c.setValue(1);
              System.out.println("c's value was 11 and is now " + c.value());
              System.out.println("runningTotal was " + runningTotal);
							runningTotal -= 10;
						}
					}
				}
			}
		}
		return runningTotal;
	}
}
