package BlackJackGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class GameUI {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_BOLD = "\u001B[1m";

    private Scanner scanner;

    public GameUI(Scanner scanner) {
        this.scanner = scanner;
    }

    public void printlnDelayed(String... lines) throws InterruptedException {
        for (String line : lines) {
            for (char c : line.toCharArray()) {
                System.out.print(c);
                Thread.sleep(20); // Delay for each character
            }
            System.out.println(); // Newline after the line is printed
        }
    }

    public void printDelayed(String... text) throws InterruptedException {
        for (String s : text) {
            for (char c : s.toCharArray()) {
                System.out.print(c);
                Thread.sleep(20); // Delay for each character
            }
        }
    }

    public boolean askPlayAgain(int round) throws InterruptedException {
        printlnDelayed("Would you like to play another round? Type " + ANSI_BOLD + ANSI_BLUE + "y" + ANSI_RESET + "es to play again, or " + ANSI_BOLD + ANSI_BLUE + "n" + ANSI_RESET + "o to quit.");
        String line;
        while (true) {
            line = scanner.nextLine().toLowerCase(); // Convert to lowercase for case-insensitive comparison
            if (line.equals("y") || line.equals("yes")) {
                return true;
            } else if (line.equals("n") || line.equals("no")) {
                return false;
            } else {
                printlnDelayed("Invalid input! Please type '" + ANSI_BOLD + ANSI_BLUE + "y" + ANSI_RESET + "' or '" + ANSI_BOLD + ANSI_BLUE + "n" + ANSI_RESET + "'.");
                System.out.print(">"); // Re-display the prompt
            }
        }
    }

    public double getBet(double money) throws InterruptedException, IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        double defaultBet = 20;

        printlnDelayed(ANSI_YELLOW + "How much do you want to bet this round? " + ANSI_RESET 
        + "You have " + ANSI_GREEN + "$" + formatMoney(money) + ANSI_RESET + ". "
        + "Default is " + ANSI_GREEN + "$" + String.format("%.0f", defaultBet) + ANSI_RESET + ". "
        + "You can bet decimal amounts. Press Enter to accept default amount.");

        while (true) {
            System.out.print(">");
            try {
                String line = reader.readLine();
                if (line.isEmpty()) {
                    if (defaultBet <= money) {
                        return defaultBet;
                    } else {
                        printlnDelayed("You don't have enough money for the default bet. Please enter a smaller amount.");
                    }
                } else {
                    double input = Double.parseDouble(line);
                    if (input >= 0 && input <= money) {
                        return input;
                    } else {
                        printlnDelayed("Invalid bet amount. Please enter a number between 0 and " + ANSI_GREEN + "$" + formatMoney(money) + ANSI_RESET + ".");
                    }
                }
            } catch (NumberFormatException e) {
                printlnDelayed("Invalid input! Please enter a number or accept the default.");
            }
        }
    }

    public void displayPlayerHand(Hand playerHand) throws InterruptedException {
        printlnDelayed("You were dealt " + formatHand(playerHand) + ". Total: " + formatTotal(playerHand.sum()));
    }

    public void displayDealerInitialHand(Hand dealerHand) throws InterruptedException {
        printlnDelayed("The dealer now deals his own cards. One card is " + formatCard(dealerHand.card(0)) + ". The other is face down.");
    }



    public void displayDealerTurnStart() throws InterruptedException {
        printlnDelayed("Now it's the dealer's turn.");
        printlnDelayed("The dealer must draw cards until their hand total is 17 or higher.");
    }

    public void displayDealerHand(Hand dealerHand) throws InterruptedException {
        printlnDelayed("His hand is " + formatHand(dealerHand) + ". His total is " + formatTotal(dealerHand.sum()));
    }

    public void displayDealerStopsDrawing() throws InterruptedException {
        printlnDelayed("The dealer has reached 17 points or greater and must now stop drawing cards.");
    }

    public void displayDealerDrawsCard(Card c, int total) throws InterruptedException {
        printlnDelayed("The dealer drew " + formatCard(c) + ". His total is now " + formatTotal(total));
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
        printlnDelayed("Would you like to Hit (draw another card) or Stay (keep your current hand)? Type '" + ANSI_BOLD + ANSI_BLUE + "h" + ANSI_RESET + "' to Hit, or '" + ANSI_BOLD + ANSI_BLUE + "s" + ANSI_RESET + "' to Stay.");
        System.out.print(">");
        String line;
        while (true) {
            line = scanner.nextLine().toLowerCase(); // Convert to lowercase for case-insensitive comparison
            if (line.equals("h") || line.equals("hit")) {
                return "hit";
            } else if (line.equals("s") || line.equals("stay")) {
                return "stay";
            } else {
                printlnDelayed("Not a valid command. Please type '" + ANSI_BOLD + ANSI_BLUE + "h" + ANSI_RESET + "' to Hit or '" + ANSI_BOLD + ANSI_BLUE + "s" + ANSI_RESET + "' to Stay.");
                System.out.print(">");
            }
        }
    }
    public String formatMoney(double amount) {
        if (amount == (long) amount) {
            return String.format("%.0f", amount);
        } else {
            return String.format("%.2f", amount);
        }
    }

    public String formatCard(Card card) {
        String color = "";
        if (card.suit().equals("Hearts") || card.suit().equals("Diamonds")) {
            color = ANSI_RED;
        } else {
            color = ANSI_CYAN;
        }
        return color + ANSI_BOLD + card.name() + " of " + card.suit() + ANSI_RESET;
    }

    public String formatHand(Hand hand) {
        StringBuilder formattedHand = new StringBuilder();
        for (int i = 0; i < hand.size(); i++) {
            formattedHand.append(formatCard(hand.card(i)));
            if (i < hand.size() - 1) {
                formattedHand.append(", ");
            }
        }
        return formattedHand.toString();
    }

    public String formatTotal(int total) {
        if (total > 21) {
            return ANSI_RED + ANSI_BOLD + total + ANSI_RESET;
        } else {
            return ANSI_GREEN + ANSI_BOLD + total + ANSI_RESET;
        }
    }
}