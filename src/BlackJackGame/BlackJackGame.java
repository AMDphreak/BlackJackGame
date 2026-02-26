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
        ui.printlnDelayed(GameUI.ANSI_PURPLE + GameUI.ANSI_BOLD + "Welcome to Black Jack!" + GameUI.ANSI_RESET);
        ui.printlnDelayed("You will be dealt 2 cards at the start.");
        ui.printlnDelayed("Get a hand total as close to 21 as possible without going over, while making sure you are higher than the dealer's hand.");

        int round = 0;
        boolean playAgain;

        do {
            round += 1;
            double bet = bettingSystem.placeBet(player.getMoney());

            deck = new Deck();
            player.clearHand();
            dealer.clearHand();
            int shuffles = (int) (100 + Math.random() * 900);
            ui.printlnDelayed("Shuffling deck " + shuffles + " times, one card at a time...");
            deck.shuffle(shuffles);

            // Player's initial draw
            Card c;
            c = deck.drawCard();
            player.getHand().add(c);
            c = deck.drawCard();
            player.getHand().add(c);
            // Check both initial cards for Ace
            if (player.getHand().card(0).name().equals("Ace") || player.getHand().card(1).name().equals("Ace")) {
                ui.displayAceInfo();
            }

            ui.displayPlayerHand(player.getHand());
            // Check for player Blackjack immediately after initial deal
            if (player.getHand().sum() == 21) {
                ui.printlnDelayed("You have Blackjack!");
                bettingSystem.playerWins(bet, round);
                player.addMoney(bet);
            } else {
                // Dealer's initial draw
                c = deck.drawCard();
                dealer.getHand().add(c);
                c = deck.drawCard();
                dealer.getHand().add(c);
                if (dealer.getHand().card(0).name().equals("Ace")) { // Only display if the visible card is an Ace
                    ui.displayAceInfo();
                }

                ui.displayDealerInitialHand(dealer.getHand());

                // Player's turn to draw cards
                boolean keepDrawing = true;
                do {
                    String playerAction = ui.getPlayerAction();

                    if (playerAction.equals("hit")) {
                        c = deck.drawCard();
                        player.getHand().add(c);
                        String drawMessage = "You drew " + ui.formatCard(c) + ".";
                        if (player.getHand().sum() > 21) {
                            ui.printlnDelayed(drawMessage); // Print draw message without total
                            ui.printlnDelayed("Your total is " + ui.formatTotal(player.getHand().sum()) + ". You busted!");
                        } else {
                            ui.printlnDelayed(drawMessage + " Your total is " + ui.formatTotal(player.getHand().sum()) + ".");
                        }
                    } else if (playerAction.equals("stay")) {
                        keepDrawing = false;
                    }

                    if (player.getHand().sum() > 21) {
                        bettingSystem.dealerWins(bet, round);
                        player.subtractMoney(bet);
                        keepDrawing = false;
                    }
                } while (keepDrawing);

                // Only proceed to dealer's turn if player hasn't busted
                if (player.getHand().sum() <= 21) {
                    // Dealer's turn
                    ui.displayDealerTurnStart();
                    ui.displayDealerHand(dealer.getHand());

                    // Check for dealer Blackjack immediately after revealing hand
                    if (dealer.getHand().sum() == 21) {
                        ui.printlnDelayed("Dealer has Blackjack!");
                        bettingSystem.dealerWins(bet, round);
                        player.subtractMoney(bet);
                    } else {
                        // Dealer draws cards until 17 or higher
                        if (dealer.shouldHit()) {
                            ui.printDelayed(".", ".", ".");
                            do {
                                Thread.sleep(1000);
                                c = deck.drawCard();
                                dealer.getHand().add(c);
                                ui.displayDealerDrawsCard(c, dealer.getHand().sum());
                                if (!dealer.shouldHit()) {
                                    ui.displayDealerStopsDrawing();
                                }
                                if (dealer.getHand().sum() > 21) {
                                    ui.displayDealerBust();
                                    bettingSystem.playerWins(bet, round);
                                    player.addMoney(bet);
                                }
                            } while (dealer.shouldHit() && dealer.getHand().sum() <= 21);
                        } else {
                            ui.displayDealerStopsDrawing();
                        }

                        // Compare hands only if dealer hasn't busted
                        if (dealer.getHand().sum() <= 21) {
                            if (dealer.getHand().sum() > player.getHand().sum()) {
                                ui.displayDealerWins();
                                bettingSystem.dealerWins(bet, round);
                                player.subtractMoney(bet);
                            } else if (dealer.getHand().sum() < player.getHand().sum()) {
                                ui.displayPlayerWins();
                                bettingSystem.playerWins(bet, round);
                                player.addMoney(bet);
                            } else { // Tie
                                bettingSystem.displayTie(round);
                            }
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