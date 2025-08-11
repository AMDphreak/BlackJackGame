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

    public void playerWins(double bet) throws InterruptedException {
        ui.printlnDelayed("Congratulations! You won $" + String.format("%.2f", bet) + "!");
    }

    public void dealerWins(double bet) throws InterruptedException {
        ui.printlnDelayed("The dealer wins this round. You lose $" + String.format("%.2f", bet) + ". Better luck next time!");
    }

    public void displayTie() throws InterruptedException {
        ui.printlnDelayed("It's a tie! Your bet is returned.");
    }
}