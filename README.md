# Code Kata: Trading Card Game (TCG)

In this [Code Kata](http://en.wikipedia.org/wiki/Kata_(programming) you will be implementing a rudimentary two-player trading card game. The rules are loosely based on [Blizzard Hearthstone](http://us.battle.net/hearthstone/en/). This Kata is intentionally slightly more complex and challenging than the [well-known traditional Katas](http://codingdojo.org/cgi-bin/wiki.pl?KataCatalogue). It leaves room for different focuses (like playing cards based on user input and/or algorithmic decision logic) and can easily be modified or extended with additional rules.

## TCG Rules

### Preparation

1. Each player starts the game with 30 life and 0 mana
2. Each player starts with deck of 15 cards with the following mana costs: 1,1,2,2,3,3,3,4,4,5,5,6,6,7,8
3. From the deck each player receives 3 random cards has his initial hand

### Gameplay
1. The active player receives 1 mana slot up to a maximum of 10 total slots
2. The active player's empty mana slots are refilled
3. The active player draws a random card from his deck
4. The active player can play as many cards as he can afford. Any played card empties mana slots and deals immediate damage  to the opponent player equal to its mana cost.
5. If the opponent player's life drops to or below zero the active player wins the game
6. If the active player can't (by either having no cards left in his hand or lacking sufficient mana to pay for any hand card) or simply doesn't want to play another card, the opponent player becomes active

### Special Situations
1. Bleeding Out: If a player's card deck is empty before the game is over he receives 1 damage instead of drawing a card when it's his turn
2. Overload: If a player draws a card that lets his hand size become >5 that card is discarded instead of being put into his hand
