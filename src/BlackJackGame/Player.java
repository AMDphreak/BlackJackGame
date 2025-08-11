package BlackJackGame;

public class Player {
    private Hand hand;
    private double money;

    public Player(double initialMoney) {
        this.money = initialMoney;
        this.hand = new Hand();
    }

    public Hand getHand() {
        return hand;
    }

    public double getMoney() {
        return money;
    }

    public void addMoney(double amount) {
        this.money += amount;
    }

    public void subtractMoney(double amount) {
        this.money -= amount;
    }

    public void clearHand() {
        this.hand = new Hand();
    }
}