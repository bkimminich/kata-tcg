describe("A Game", function () {
    var game;

    beforeEach(function () {
        game = new Game(new Player("player1"), new Player("player2"));
    });

    it("should have two players", function () {
        expect(game.activePlayer).not.toBe(undefined);
        expect(game.opponentPlayer).not.toBe(undefined);
    });

    it("should have a starting player with 3 cards in hand", function () {
        expect(game.activePlayer.hand.length).toBe(3);
        expect(game.activePlayer.deck.length).toBe(17);
    });

    it("should have a non-starting player with 4 cards in hand", function () {
        expect(game.opponentPlayer.hand.length).toBe(4);
        expect(game.opponentPlayer.deck.length).toBe(16);
    });

});
