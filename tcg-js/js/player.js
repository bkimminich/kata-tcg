function Player(name) {
    this.name = name;
}

Player.prototype = {
    health: 30,
    mana: 0,
    maxMana: 0,
    deck: [0, 0, 1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 5, 5, 6, 6, 7, 8],
    hand: [],
    drawCard: function () {
        if (this.deck.length === 0) {
            this.health--; // bleedout rule
        } else {
            var cardIndex = Math.floor(Math.random() * this.deck.length);
            var card = this.deck.splice(cardIndex, 1)[0];
            if (this.hand.length < 5) { // check against overload rule
                this.hand.push(card);
            }
        }
    },
    playCard: function (card, target) {
        if (target === this) { // healing
            target.health += card;
            target.health = Math.min(target.health, 30); // healing caps at the initial value of 30
        } else { // attacking
            target.health -= card;
        }
    }
}