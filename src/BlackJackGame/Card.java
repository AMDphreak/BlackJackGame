package BlackJackGame;

public class Card {
	private String suit;
	private String name;
	private int value;
	
	public void setSuit(String s) {
		suit = s;
	}
	public String suit() {
		return suit;
	}
	public void setName(String n) {
		name = n;
	}
	public String name() {
		return name;
	}
	public void setValue(int v) {
		value = v;
	}
	public int value() {
		return value;
	}
	public String toString() {
		return name + " of " + suit;
	}
	
	// In order to create a card, it must have a suit, name, and value.
	// Use this for face cards and numeric cards other than an Ace
	// @param suit - Diamonds, Hearts, Spades, Clubs
	// @param name - Jack, Queen, King, or 1, 2, 3.. etc stored as a string
	// @param value - Value of the card. 2 through 13
	Card(String name, String suit, int value) {
		setName(name);
		setSuit(suit);
		setValue(value);
	}
	
	// If it's an Ace, don't set the value. Use this constructor.
	// @param suit - Diamonds, Hearts, Spades, Clubs
	// @param name - Ace
	Card(String name, String suit) {
		setName(name);
		setSuit(suit);
	}
}