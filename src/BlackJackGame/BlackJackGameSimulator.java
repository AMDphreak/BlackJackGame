package BlackJackGame;

import java.util.Scanner;
import java.io.IOException;

public class BlackJackGameSimulator {

	public static void main(String[] args) throws InterruptedException, IOException {
		Scanner scan = new Scanner(System.in);
		BlackJackGame game = new BlackJackGame(100.00, scan);
		game.startGame();
		scan.close();
	}
}
