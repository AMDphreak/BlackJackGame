package BlackJackGame;

public class Dealer {
    private Hand hand;

    public Dealer() {
        this.hand = new Hand();
    }

    public Hand getHand() {
        return hand;
    }

    public void clearHand() {
        this.hand = new Hand();
    }

    public boolean shouldHit() {
        return hand.sum() < 17;
    }
}