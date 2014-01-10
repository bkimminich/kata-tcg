# Code Kata: Trading Card Game (TCG)

In this [Code Kata](http://en.wikipedia.org/wiki/Kata_(programming) you will be implementing a rudimentary two-player trading card game. The rules are loosely based on [Blizzard Hearthstone](http://us.battle.net/hearthstone/en/). This Kata is intentionally slightly more complex and challenging than the [well-known traditional Katas](http://codingdojo.org/cgi-bin/wiki.pl?KataCatalogue). It leaves room for different focuses (like playing cards based on user input and/or algorithmic decision logic) and can be approached iteratively with TDD from different starting points. The Kata should also be pretty easy to modify and extend with things like additional card types (e.g. healing) or rules.

## TCG Rules

### Preparation

1. Each player starts the game with 30 _Health_ and 0 _Mana_ slots
2. Each player starts with a deck of 20 _Damage_ cards with the following Mana costs: 0,0,1,1,2,2,2,3,3,3,3,4,4,4,5,5,6,6,7,8
3. From the deck each player receives 3 random cards has his initial hand

### Gameplay
1. The active player receives 1 Mana slot up to a maximum of 10 total slots
2. The active player's empty Mana slots are refilled
3. The active player draws a random card from his deck
4. The active player can play as many cards as he can afford. Any played card empties Mana slots and deals immediate damage  to the opponent player equal to its Mana cost.
5. If the opponent player's Health drops to or below zero the active player wins the game
6. If the active player can't (by either having no cards left in his hand or lacking sufficient Mana to pay for any hand card) or simply doesn't want to play another card, the opponent player becomes active

### Special Rules
1. _Bleeding Out_: If a player's card deck is empty before the game is over he receives 1 damage instead of drawing a card when it's his turn.
2. _Overload_: If a player draws a card that lets his hand size become >5 that card is discarded instead of being put into his hand.
3. _Dud Card_: The 0 Mana cards can be played for free but don't do any damage either. They are just annoyingly taking up space in your hand.

## Advanced Variations

When the normal game rules have become too boring you might consider adding some additional rules like those described below:

1. Let Mana cost and damage dealt be different from each other thus making cheap powerful, expensive mediocre or entirely useless cards possible. This can add a whole new layer of play strategy as some cards might not be desired to be ever played, but eventually have to in order to free your hand for better cards.
2. Introduce a _Healing_ card type that restores a given amount of Health on the active player. Can be done with Mana cost equal to healing amount or independent for each other (see above).
3. Introduce _Card Drawer_ cards that cost Mana but don't do any damage. Instead they let you draw a given number of cards from your deck. Those cards can be used in the current turn or later on (just as if normally drawn at the beginning of the active player's turn).
4. Allow players to create their own decks of 20 cards from a larger _Card Pool_. Let those decks be saved to and loaded from disk[1] before starting a game.
5. Let the game be played via network[1] against other players.

[1] If you want to do these variations with TDD, this might be the (latest) point where you should get familiar with a good [Mocking](http://en.wikipedia.org/wiki/Mock_object) framework (like [Mockito](https://code.google.com/p/mockito/))