# Code Kata: Trading Card Game (TCG)

In this [Code Kata](http://en.wikipedia.org/wiki/Kata_\(programming\)) you will be implementing a rudimentary two-player trading card game. The rules are loosely based on [Blizzard Hearthstone®](http://us.battle.net/hearthstone/en/). This Kata is intentionally slightly more complex and challenging than many of the well-known traditional Katas (like Roman Numbers, Bowling Game or FizzBuzz). It leaves room for different focuses (like playing cards based on user input and/or algorithmic decision logic) and can be approached iteratively with TDD from different starting points. The Kata is also well suited for rule extension and modification keeping it challenging for developers of all experience levels.

## TCG Rules

### Preparation

1. Each player starts the game with 30 _Health_ and 0 _Mana_ slots
2. Each player starts with a deck of 20 _Damage_ cards with the following Mana costs: 0,0,1,1,2,2,2,3,3,3,3,4,4,4,5,5,6,6,7,8
3. From the deck each player receives 3 random cards has his initial hand

### Basic Gameplay
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

When the normal game rules have become too easy/boring you might consider adding some additional rules like those described below:

1. Let Mana cost and damage dealt be different from each other thus making cheap powerful, expensive mediocre or entirely useless cards possible. This can add a whole new layer of play strategy as some cards might not be desired to be ever played, but eventually have to in order to free your hand for better cards.
2. Introduce a _Healing_ card type that restores a given amount of Health on the active player. Can be done with Mana cost equal to healing amount or independent for each other (see above).
3. Introduce _Card Drawer_ cards that cost Mana but don't do any damage. Instead they let you draw a given number of cards from your deck. Those cards can be used in the current turn or later on (just as if normally drawn at the beginning of the active player's turn).
4. Let players choose to play cards either as immediate damage _Spells_ (like all cards work in the Basic Gameplay rules) or as _Minions_ that are put on the board instead.
    * Minions might use the mana cost of their card as Health and Damage value. Health has to be tracked when they receive damage.
    * A Minion would sleep in the turn it was but on the board.
    * Starting from the next turn a Minion can be used to deal damage to the opponent player or an opponent Minion.
    * A Minion fighting another Minion will result in them dealing their damage value to each other simultaneously. Sleeping Minions will defend themselves in the same way when attacked by another Minion.
    * Players can choose to play a _Spell_ against a Minion. The attacked Minion will not defend itself in this case, thus the attacking player receives no damage from it.
    * When a Minions health drops to or below zero it is removed from the board.
5. Allow players to create their own decks of 20 cards from a larger _Card Pool_. Let those decks be saved to and loaded from disk before starting a game.