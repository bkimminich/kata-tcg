function Game(player1, player2) {
    this.activePlayer = Math.random() >= 0.5 ? player1 : player2;
    this.opponentPlayer = this.activePlayer === player1 ? player2 : player1;

    for (var i = 0; i < 3; i++) {
        this.activePlayer.drawCard();
        this.opponentPlayer.drawCard();
    }
    this.opponentPlayer.drawCard(); // extra card to compensate for not playing first turn
}

Game.prototype = {
    beginTurn : function () {
        this.activePlayer.manaSlots = Math.min(this.activePlayer.manaSlots + 1, 10);
        this.activePlayer.mana = this.activePlayer.manaSlots;
        this.activePlayer.drawCard();
    },

    playTurn: function () {
        while (hasEnoughManaForCardInHand.call(this)) {
            var choice = window.prompt(this.activePlayer.name + ", please choose a card to play from " + this.activePlayer.hand);
            if (choice) {
                var chosenCard = parseInt(choice, 10);
                if (isNaN(chosenCard)) {
                    if (!window.confirm(choice + " is not a valid card!")) return;
                } else if (chosenCard > this.activePlayer.mana) {
                    if (!window.confirm("Cannot play card " + chosenCard + " with only " + this.activePlayer.mana + " mana!")) return;
                } else if (this.activePlayer.hand.indexOf(chosenCard)  == -1) {
                    if (!window.confirm(chosenCard + " is not present in hand!")) return;
                } else {
                    this.activePlayer.playCard(chosenCard, this.opponentPlayer);
                }
            } else {
                return;
            }
        }

        function hasEnoughManaForCardInHand() {
            return this.activePlayer.mana >= Math.min.apply(Math, this.activePlayer.hand);
        }

    },

    endTurn : function () {
        switchPlayers.call(this);

        function switchPlayers() { // ECMA6: [activePlayer, opponentPlayer] = [opponentPlayer, activePlayer]
            var tmp = this.activePlayer;
            this.activePlayer = this.opponentPlayer;
            this.opponentPlayer = tmp;
        }
    }
};

