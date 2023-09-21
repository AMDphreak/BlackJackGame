package BlackJackGame;
import java.util.ArrayList;

public class Deck {

	// The Deck is just an ArrayList of Cards.
	// We use the ArrayList implementation of the List interface, because
	// It has performance characteristics similar to an array, rather than a
	// Linked List, which has worse seeking performance.
	ArrayList<Card> deck = new ArrayList<Card>();

	// Deck constructor
	Deck(){

		// let's put some cards into the deck. Deck has 52 cards.
		// 13 Hearts, 13 Spades, 13 Clubs, 13 Diamonds
		// put in non-numeric cards
		String[] suits = {"♥️","♠️","♣️","♦️"};
		//using an enhanced for loop to loop through the array of suits
		for (String suit : suits) {
			// Add the Ace
			deck.add(new Card("Ace", suit, 11));
			// Now add the fixed-value cards
			for (int value=2; value<=13; value++) {
				switch (value) {
				// Jack
				case 11: deck.add(new Card("Jack", suit, 10)); break;
				// Queen
				case 12: deck.add(new Card("Queen", suit, 10)); break;
				// King
				case 13: deck.add(new Card("King", suit, 10)); break;
				// Numeric
				default: deck.add(new Card(Integer.toString(value), suit, value));
				}
			}
		}
	}
	
	public String toString() {
		String s = ""; // Fill this string up with our card name and suit as we iterate through
						// the hand.
		int num = 0;
		for(Card c : deck) {
			s += c.toString(); // get the card's description and stick it in the string
			if(num < deck.size() - 1) {
				// add a comma between multiple items, but only if it's not the last item.
				s += ", ";
			}
			num++;
		}
		return s;
	}

	// Shuffle Deck
	// @param int times - number of times to shuffle the deck
	public void shuffle(int times) {
		for (int counter = 0; counter < times ; counter++) {
			// Pick a random card in the deck
			int randomCardInDeck = (int)(Math.random() * deck.size());
				// Use the (int) before the data to convert it to integer using type-casting. This
				// performs a truncation of the decimal...so it doesn't round up--ever. Always rounds down.
				// A deck size of 52 * random number between 0 and 1.0 (not including 1.0) yields a number
				// between 0 and 51, inclusive, which correlates to the range of valid indices in an array.

			// Now that we have a random card picked, we have to pull it out of the deck. In order to do
			// that, we copy the card to a temporary variable called 'c', then we delete the card from
			// the deck.
			Card c = deck.get(randomCardInDeck);
			deck.remove(randomCardInDeck);
			// Now that we removed the card, we need to put it back into the deck. We'll add the card
			// back to the deck, and it will get placed at the end.
			deck.add(c);
			// Now all we need to do is the same thing over and over until the deck is somewhat random.
			// This whole process is enclosed in a for loop to do the same process over and over.
			// and over and over, and over, and over, and over, and over
			// and
			// over
			// and over and over and over andover andover andoverandoverandoverandover
			// andfnas;lkdhvopuweln;lknasdvxnz.c,nvc,xns,danfknas
			// ANDOR from Star Wars
		}
	}
	
	// Draw a Card from the Deck
	// In order to do this, the function must copy the card to a temporary variable,
	// then delete the card from the deck, and then return the temporary variable.
	public Card drawCard() {
		Card pulled = deck.get(0); // it gets the first card (card at index 0)
		deck.remove(0); //removed!!!
		return pulled;
	}
}
