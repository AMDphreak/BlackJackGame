package BlackJackGame;
import java.util.Scanner;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class BlackJackGameSimulator {

	public static void main(String[] args) throws InterruptedException {
		// InterruptedException throw in function declaration is included for Thread.sleep() function.

		double money = 100.00; // 100 dollars
		int round = 0; boolean playAgain;
		boolean isFirstAce = false; // Keeps track of whether an Ace has been drawn this game
		// so the game can tell the user how the Ace works.
		Scanner scan = new Scanner(System.in);

		System.out.println("Black Jack Game");
		printlnDelayed("To win at Black Jack, you must have cards totaling 21 points or less "
				+ "and have more points than your opponent.");
		printlnDelayed("You start with $"+ String.format("%.2f", money) + " of gambling money.");

		do {

			round += 1;

			// take a bet
			double bet = placeBet(money);
			printDelayed(".",".",".");

			// Set up the game
			printlnDelayed("Creating a new deck.");
			Deck deck = new Deck();
			Hand playerHand = new Hand();
			Hand dealerHand = new Hand();

			printDelayed(".",".",".");
			// shuffle the deck
			int times = (int)(100+ Math.random() * 900);
			deck.shuffle(times);
			printlnDelayed("The deck was shuffled "+times+" times, one card at a time.");

			Card c; // this is a temporary variable used to move cards from deck to hand, etc.

			// draw 2 cards for the player and put them in his hand
			c = deck.drawCard(); checkforAce(c, isFirstAce); playerHand.add(c);
			c = deck.drawCard(); checkforAce(c, isFirstAce); playerHand.add(c);

			printlnDelayed("You were dealt " + playerHand.toString() );
			// check the cards to see if the player has drawn a perfect 21 and won the round
			if (playerHand.sum() == 21) {
				printlnDelayed("Your total is "+playerHand.sum()+".");
				playerWins(bet);
				money += bet;
			} else {
				// draw 2 cards for dealer and put them in his hand
				c = deck.drawCard(); checkforAce(c, isFirstAce); dealerHand.add(c);
				c = deck.drawCard(); checkforAce(c, isFirstAce); dealerHand.add(c);

				printlnDelayed("The dealer was dealt cards. One card is "
						+ dealerHand.card(0).toString() + ". The other is face down.");
				printlnDelayed("Your hand's total is " + playerHand.sum() + ". Don't exceed 21 or you lose!");

				/*
				 *  Now that the dealer's hand has been dealt, the player is given the opportunity
				 *  to draw cards to try to get as close to 21 as possible without busting.
				 *  The term is "hit" to draw a card or "stay" to keep the current total.
				 */

				// draw cards loop
				boolean keepDrawing = true;
				boolean isFirstDraw = true;
				do {
					if (isFirstDraw == false) {
						printlnDelayed("Your total is now " + playerHand.sum() + ".");
					}

					printlnDelayed("Would you like to draw another card? Hit Enter to "
							+ "draw, or type yes or no.");
					System.out.print(">");

					boolean valid = false;
					String line;
					do {
						line = scan.nextLine();
						if (line.isEmpty() || line.equals("yes") || line.equals("no")) {
							valid = true;
						} else {
							printlnDelayed("Not a valid command. Please answer the question like "
									+ "a normal human being.");
						}
					} while (valid == false);

					if (line.equals("yes") || line.isEmpty()) {
						c = deck.drawCard();
						playerHand.add(c);
						printlnDelayed("You drew "+ c.toString() +". Your total is "
								+ playerHand.sum() + ".");
						checkforAce(c, isFirstAce);
					} else if (line.equals("no")) {
						keepDrawing = false;
					}

					if(playerHand.sum() > 21) {
						printlnDelayed("Your total is "+playerHand.sum()+". You busted!");
						dealerWins(bet);
						money -= bet;
						keepDrawing = false;
					}
				} while(keepDrawing);

				if (playerHand.sum() <= 21) {
					// if the dealer's hand is a perfect 21 immediately, they win. Otherwise they have to 
					// draw cards until they have 17 points or more.
					printlnDelayed("Now it's the dealer's turn.");
					printlnDelayed("The dealer must draw cards until he has "
							+ "17 points or higher.");
					printlnDelayed("His hand is " + dealerHand.toString() + ". His total is "
							+ dealerHand.sum());
					if(dealerHand.sum() == 21) {
						dealerWins(bet);
						money -= bet;
					} else if (dealerHand.sum() >=17 && dealerHand.sum() < 21) {
						printlnDelayed("The dealer has 17 points or greater. He can no longer draw cards.");
					} else {
						System.out.print("Drawing cards");
						printDelayed(".",".",".");
						keepDrawing = true;
						do {
							Thread.sleep(1000); // wait 1 second (1000 milliseconds) then continue.
							c = deck.drawCard();
							checkforAce(c, isFirstAce);
							dealerHand.add(c);
							printlnDelayed("The dealer drew " + c.toString() + ". His total is now "
									+ dealerHand.sum());
							if(dealerHand.sum() >= 17) {
								printlnDelayed("The dealer has 17 points or greater. He can no longer draw cards.");
								keepDrawing = false;
							}
							if(dealerHand.sum() > 21) {
								// dealer busts!
								printlnDelayed("The dealer busts! You win!");
								playerWins(bet);
								money += bet;
							}
						} while(keepDrawing);
						
						// compare the hands, and see whose is higher
						if (dealerHand.sum() > playerHand.sum()) {
							printlnDelayed("The dealer had the better hand!");
							dealerWins(bet);
							money -= bet;
						} else if (dealerHand.sum() < playerHand.sum()) {
							printlnDelayed("You had the better hand! You win!");
							playerWins(bet);
							money += bet;
						} else {
							// tie!
							printlnDelayed("Well golly gee, it's a tie!");
							// for some reason, this line of code never runs, even
							// though the code logic and structure are solid.
						}
					}
				}
			}

			if(money > 0) {
				playAgain = playAgainPrompt(round, scan);
			} else {
				printlnDelayed("You have run out of money. Time to break open the piggy bank.");
				playAgain = false; // no more money
			}

		} while(playAgain);
		scan.close(); // get rid of the input listener.
	}

	public static void printlnDelayed(String... lines) throws InterruptedException {
		for (String line : lines) {
			Thread.sleep(200);
			System.out.println(line);
		}
	}
	public static void printDelayed(String... text) throws InterruptedException {
		for (String s : text) {			
			Thread.sleep(200);
			System.out.print(s);
		}
	}
	public static boolean playAgainPrompt(int round, Scanner scan) throws InterruptedException {
		printlnDelayed("Round "+round+" complete.");
		printlnDelayed("Want to go another round? Hit Enter to play again, or type yes or no.");
		boolean valid = false; // valid user input?
		String line;
		do {
			line = scan.nextLine();
			if(line.isEmpty() || line.equals("yes") || line.equals("no")) {
				valid = true;
			} else {
				printlnDelayed("Please enter a valid command. Try again. yes or no? Or hit Enter.");
			}
		} while(valid == false);

		if (line.isEmpty() || line.equals("yes")) {
			return true;
		} else {
			return false;
		}
	}
	public static void playerWins(double bet) throws InterruptedException {
		printlnDelayed("You won $"+String.format("%.2f", bet)+"! Look at you, all lucky and shit! Go buy a lotto ticket.");
	}
	public static void dealerWins(double bet) throws InterruptedException {
		printlnDelayed("The dealer wins. You lose $"+String.format("%.2f", bet)+"! You suck at this game!");
	}

	public static void checkforAce(Card c, boolean firstAce) throws InterruptedException {
		if(c.name().equals("Ace") && firstAce) {
			printlnDelayed("An Ace can be valued either 1 or 11, "
					+ "depending on whether an 11 will cause the hand to exceed 21.");
		}
	}

	public static double placeBet(double money) throws InterruptedException {
	    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	    double bet = 20;
	
	    printlnDelayed("How much would you like to bet? "
	            + "You can bet between 0 and $" + String.format("%.2f", money) + ".");
	    printlnDelayed("Enter the amount you would like to gamble. Be wise!");
	
	    boolean valid = false; // keep track of whether user has chosen a valid input
	    do {
	        System.out.print(">");
	        try {
	            String line = reader.readLine();
	            if (line.isEmpty() && bet <= money) {
	                printlnDelayed("You have accepted the default value of $" + String.format("%.2f", bet));
	                // If the user has accepted the default bet, then we must make sure they have enough money
	                valid = true;
	            } else if (line.isEmpty() && bet > money) {
	                printlnDelayed("You do not have sufficient money to accept the default bet. Please enter a valid number.");
	            } else {
	                double input = Double.parseDouble(line);
	                /* need to validate the input.
	                   User cannot bet more money than they have, so input must be less than or equal
	                   to money.
	                */
	                if (input >= 0 && input <= money) {
	                    valid = true;
	                    bet = input;
	                } else {
	                    printlnDelayed("Not a valid amount of money. Please input a number.");
	                }
	            }
	        } catch (Exception e) {
	            printlnDelayed("Please input a valid number or hit Enter to accept the default.");
	        }
	    } while (!valid);
	
	    return bet;
	}
}
