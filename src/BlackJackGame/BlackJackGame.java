package BlackJackGame;

import java.io.IOException;
import java.util.Scanner;

public class BlackJackGame {
    private Deck deck;
    private Player player;
    private Dealer dealer;
    private GameUI ui;
    private BettingSystem bettingSystem;

    public BlackJackGame(double initialMoney, Scanner scanner) {
        this.ui = new GameUI(scanner);
        this.bettingSystem = new BettingSystem(ui);
        this.player = new Player(initialMoney);
        this.dealer = new Dealer();
    }

    public void startGame() throws InterruptedException, IOException {
        ui.printlnDelayed("Welcome to Black Jack! Your goal is to get a hand total closer to 21 than the dealer, without going over 21.");
        ui.printlnDelayed("You start with $" + String.format("%.2f", player.getMoney()) + " of gambling money.");

        int round = 0;
        boolean playAgain;

        do {
            round += 1;
            double bet = bettingSystem.placeBet(player.getMoney());
            ui.printDelayed(".", ".", ".");

            ui.printlnDelayed("Creating a new deck.");
            deck = new Deck();
            player.clearHand();
            dealer.clearHand();

            ui.printDelayed(".", ".", ".");
            int times = (int)(100 + Math.random() * 900);
            deck.shuffle(times);
            ui.printlnDelayed("The deck was shuffled " + times + " times, one card at a time.");

            // Player's initial draw
            Card c;
            c = deck.drawCard(); ui.displayAceInfo(); player.getHand().add(c);
            c = deck.drawCard(); ui.displayAceInfo(); player.getHand().add(c);

            ui.displayPlayerHand(player.getHand());
            if (player.getHand().sum() == 21) {
                ui.printlnDelayed("Your total is " + player.getHand().sum() + ".");
                bettingSystem.playerWins(bet);
                player.addMoney(bet);
            } else {
                // Dealer's initial draw
                c = deck.drawCard(); ui.displayAceInfo(); dealer.getHand().add(c);
                c = deck.drawCard(); ui.displayAceInfo(); dealer.getHand().add(c);

                ui.displayDealerInitialHand(dealer.getHand());
                ui.displayPlayerTotal(player.getHand().sum());

                // Player's turn to draw cards
                boolean keepDrawing = true;
                do {
                    ui.printlnDelayed("Your total is now " + player.getHand().sum() + ".");
                    String playerAction = ui.getPlayerAction();

                    if (playerAction.equals("yes") || playerAction.isEmpty()) {
                        c = deck.drawCard();
                        player.getHand().add(c);
                        ui.printlnDelayed("You drew " + c.toString() + ". Your total is " + player.getHand().sum() + ".");
                        ui.displayAceInfo();
                    } else if (playerAction.equals("no")) {
                        keepDrawing = false;
                    }

                    if (player.getHand().sum() > 21) {
                        ui.displayPlayerBust(player.getHand().sum());
                        bettingSystem.dealerWins(bet);
                        player.subtractMoney(bet);
                        keepDrawing = false;
                    }
                } while (keepDrawing);

                if (player.getHand().sum() <= 21) {
                    // Dealer's turn
                    ui.displayDealerTurnStart();
                    ui.displayDealerHand(dealer.getHand());

                    if (dealer.getHand().sum() == 21) {
                        bettingSystem.dealerWins(bet);
                        player.subtractMoney(bet);
                    } else if (dealer.shouldHit()) {
                        ui.printDelayed(".", ".", ".");
                        do {
                            Thread.sleep(1000);
                            c = deck.drawCard();
                            ui.displayAceInfo();
                            dealer.getHand().add(c);
                            ui.displayDealerDrawsCard(c, dealer.getHand().sum());
                            if (!dealer.shouldHit()) {
                                ui.displayDealerStopsDrawing();
                            }
                            if (dealer.getHand().sum() > 21) {
                                ui.displayDealerBust();
                                bettingSystem.playerWins(bet);
                                player.addMoney(bet);
                            }
                        } while (dealer.shouldHit() && dealer.getHand().sum() <= 21);

                        // Compare hands
                        if (dealer.getHand().sum() > player.getHand().sum() && dealer.getHand().sum() <= 21) {
                            ui.displayDealerWins();
                            bettingSystem.dealerWins(bet);
                            player.subtractMoney(bet);
                        } else if (dealer.getHand().sum() < player.getHand().sum() || dealer.getHand().sum() > 21) {
                            ui.displayPlayerWins();
                            bettingSystem.playerWins(bet);
                            player.addMoney(bet);
                        } else if (dealer.getHand().sum() == player.getHand().sum() && dealer.getHand().sum() <= 21) {
                            bettingSystem.displayTie();
                        }
                    } else {
                        ui.displayDealerStopsDrawing();
                        // Compare hands if dealer didn't hit
                        if (dealer.getHand().sum() > player.getHand().sum() && dealer.getHand().sum() <= 21) {
                            ui.displayDealerWins();
                            bettingSystem.dealerWins(bet);
                            player.subtractMoney(bet);
                        } else if (dealer.getHand().sum() < player.getHand().sum() || dealer.getHand().sum() > 21) {
                            ui.displayPlayerWins();
                            bettingSystem.playerWins(bet);
                            player.addMoney(bet);
                        } else if (dealer.getHand().sum() == player.getHand().sum() && dealer.getHand().sum() <= 21) {
                            bettingSystem.displayTie();
                        }
                    }
                }
            }

            if (player.getMoney() > 0) {
                playAgain = ui.askPlayAgain(round);
            } else {
                ui.displayOutOfMoney();
                playAgain = false;
            }

        } while (playAgain);
    }
}