function Game(player1, player2) {
    this.activePlayer = Math.random() >= 0.5 ? player1 : player2;
    this.opponentPlayer = this.activePlayer === player1 ? player2 : player1;

    for (var i = 0; i < 3; i++) {
        this.activePlayer.drawCard();
        this.opponentPlayer.drawCard();
    }
    this.opponentPlayer.drawCard(); // extra card to compensate for not playing first turn
}


