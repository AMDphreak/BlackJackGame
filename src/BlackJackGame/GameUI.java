package BlackJackGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class GameUI {

    private Scanner scanner;

    public GameUI(Scanner scanner) {
        this.scanner = scanner;
    }

    public void printlnDelayed(String... lines) throws InterruptedException {
        for (String line : lines) {
            Thread.sleep(200);
            System.out.println(line);
        }
    }

    public void printDelayed(String... text) throws InterruptedException {
        for (String s : text) {
            Thread.sleep(200);
            System.out.print(s);
        }
    }

    public boolean askPlayAgain(int round) throws InterruptedException {
        printlnDelayed("Round " + round + " complete.");
        printlnDelayed("Would you like to play another round? Type 'yes' to play again, or 'no' to quit.");
        boolean valid = false;
        String line;
        do {
            line = scanner.nextLine();
            if (line.isEmpty() || line.equals("yes") || line.equals("no")) {
                valid = true;
            } else {
                printlnDelayed("Invalid input. Please type 'yes' or 'no'.");
            }
        } while (!valid);

        return line.isEmpty() || line.equals("yes");
    }

    public double getBet(double money) throws InterruptedException, IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        double defaultBet = 20;

        printlnDelayed("You have $" + String.format("%.2f", money) + ".");
        printlnDelayed("How much would you like to bet for this round? (Enter a number between 0 and $" + String.format("%.2f", money) + ")");
        printlnDelayed("Press Enter to bet the default of $" + String.format("%.2f", defaultBet) + ".");

        boolean valid = false;
        double bet = defaultBet;
        do {
            System.out.print(">");
            try {
                String line = reader.readLine();
                if (line.isEmpty()) {
                    if (defaultBet <= money) {
                        printlnDelayed("You have accepted the default value of $" + String.format("%.2f", defaultBet));
                        valid = true;
                    } else {
                        printlnDelayed("You don't have enough money for the default bet. Please enter a smaller amount.");
                    }
                } else {
                    double input = Double.parseDouble(line);
                    if (input >= 0 && input <= money) {
                        valid = true;
                        bet = input;
                    } else {
                        printlnDelayed("Invalid bet amount. Please enter a number between 0 and $" + String.format("%.2f", money) + ".");
                    }
                }
            } catch (NumberFormatException e) {
                printlnDelayed("Invalid input. Please enter a number or press Enter for the default bet.");
            }
        } while (!valid);

        return bet;
    }

    public void displayPlayerHand(Hand playerHand) throws InterruptedException {
        printlnDelayed("You were dealt " + playerHand.toString());
    }

    public void displayDealerInitialHand(Hand dealerHand) throws InterruptedException {
        printlnDelayed("The dealer was dealt cards. One card is " + dealerHand.card(0).toString() + ". The other is face down.");
    }

    public void displayPlayerTotal(int total) throws InterruptedException {
        printlnDelayed("Your current hand total is " + total + ". Remember, going over 21 means you 'bust' and lose!");
    }

    public void displayPlayerBust(int total) throws InterruptedException {
        printlnDelayed("Your total is " + total + ". You busted!");
    }

    public void displayDealerTurnStart() throws InterruptedException {
        printlnDelayed("Now it's the dealer's turn.");
        printlnDelayed("The dealer must draw cards until their hand total is 17 or higher.");
    }

    public void displayDealerHand(Hand dealerHand) throws InterruptedException {
        printlnDelayed("His hand is " + dealerHand.toString() + ". His total is " + dealerHand.sum());
    }

    public void displayDealerStopsDrawing() throws InterruptedException {
        printlnDelayed("The dealer has reached 17 points or greater and must now stop drawing cards.");
    }

    public void displayDealerDrawsCard(Card c, int total) throws InterruptedException {
        printlnDelayed("The dealer drew " + c.toString() + ". His total is now " + total);
    }

    public void displayDealerBust() throws InterruptedException {
        printlnDelayed("The dealer busts! You win!");
    }

    public void displayDealerWins() throws InterruptedException {
        printlnDelayed("The dealer had the better hand!");
    }

    public void displayPlayerWins() throws InterruptedException {
        printlnDelayed("You had the better hand! You win!");
    }

    public void displayTie() throws InterruptedException {
        printlnDelayed("It's a tie! Your bet is returned.");
    }

    public void displayOutOfMoney() throws InterruptedException {
        printlnDelayed("You have run out of money. Time to break open the piggy bank.");
    }

    public void displayAceInfo() throws InterruptedException {
        printlnDelayed("An Ace can be valued as 1 or 11. It will automatically adjust to 1 if an 11 would make your hand exceed 21.");
    }

    public String getPlayerAction() throws InterruptedException {
        printlnDelayed("Would you like to 'Hit' (draw another card) or 'Stay' (keep your current hand)? Type 'yes' to Hit, or 'no' to Stay.");
        System.out.print(">");
        boolean valid = false;
        String line;
        do {
            line = scanner.nextLine();
            if (line.isEmpty() || line.equals("yes") || line.equals("no")) {
                valid = true;
            } else {
                printlnDelayed("That's not a valid command. Please type 'yes' to Hit or 'no' to Stay.");
            }
        } while (!valid);
        return line;
    }
}