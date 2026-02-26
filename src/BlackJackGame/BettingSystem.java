package BlackJackGame;

import java.io.IOException;

public class BettingSystem {

    private GameUI ui;

    public BettingSystem(GameUI ui) {
        this.ui = ui;
    }

    public double placeBet(double money) throws InterruptedException, IOException {
        return ui.getBet(money);
    }

    public void playerWins(double bet, int round) throws InterruptedException {
        ui.printlnDelayed("Round " + round + " complete. Congratulations! You won " + GameUI.ANSI_GREEN + "$" + String.format("%.2f", bet) + GameUI.ANSI_RESET + "!");
    }

    public void dealerWins(double bet, int round) throws InterruptedException {
        ui.printlnDelayed("Round " + round + " complete. The dealer wins this round. You lose " + GameUI.ANSI_RED + "$" + String.format("%.2f", bet) + GameUI.ANSI_RESET + ". Better luck next time!");
    }

    public void displayTie(int round) throws InterruptedException {
        ui.printlnDelayed("Round " + round + " complete. It's a tie! Your bet is returned.");
    }
}