# BlackJack Game

Command-line BlackJack Game with simulated dealer. This is a complete working Java command-line game that I created while tutoring someone else in Java for their accelerated bootcamp. The game seems simple by description, but the code is fairly long and complex for such a simple-sounding game.

Some interesting features I added were time-delays to make the game feel like an old-school Gameboy Color game. Gameboy games always have delays in their text printouts to make the games feel more suspenseful and realistic.

It seems I have lost the PDF that described what the instructor wanted the program to behave like, so here's an informal description:

The game of Black Jack is also called "Black Jack 21" or "21". Two players (a player and dealer) deal cards until they either add up to 21 or a lower number, or they automatically lose, if it's more than 21. They are dealt a hand of 2 cards. Dealer's second card is hidden from view. The Player can "hit" to pull cards, and he tries to get as close to 21 as possible without going over. When he decides to stop pulling cards, the dealer reveals his card, and pulls cards if his cards don't add up to 14 at the very least. After he exceeds that point, he stops drawing cards, and they compare their totals. Whoever has the higher amount wins.

Cards have values corresponding to their card number, and the following rules apply: Face cards (Jack, Queen, King) - 10 each (some BlackJack variants allow them to be 1, 2 or 3, but that is nonstandard) Ace - 11 (default) or 1 (if desired, or if 11 would cause you to bust).

The game also gives the non-dealer player an amount of money at the start to place his bets, and if he wins a round, he wins back his bet + that same amount.

