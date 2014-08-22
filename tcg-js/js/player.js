"use strict"

function Player() {
    this.health = 30;
    this.mana = 0;
    this.maxMana = 0;
    this.deck = [0, 0, 1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 5, 5, 6, 6, 7, 8];
    this.hand = [];
}

Player.prototype = {
    drawCard: function () {
        if (this.deck.length === 0) {
            this.health--;
        } else {
            var cardIndex = Math.floor(Math.random() * this.deck.length);
            this.hand.push(this.deck.splice(cardIndex, 1));
        }
    }
}