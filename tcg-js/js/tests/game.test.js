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

    it("should give one mana slot to the active player at the beginning of a turn", function () {
        for (var i = 0; i < 10; i++) {
            var previousManaSlots = game.activePlayer.manaSlots;
            game.beginTurn();
            expect(game.activePlayer.manaSlots).toBe(previousManaSlots+1);

        }
    });

    it("should cap mana slots at 10", function () {
            game.activePlayer.manaSlots = 10;
            game.beginTurn();
            expect(game.activePlayer.manaSlots).toBe(10);

    });

    it("should refill mana to number of mana slots at the beginning of a turn", function () {
        for (var i = 0; i < 10; i++) {
            game.activePlayer.mana = 0;
            game.beginTurn();
            expect(game.activePlayer.mana).toBe(game.activePlayer.manaSlots);
        }

    });

    it("should switch the active player at the end of a turn", function () {
        var previousActivePlayer = game.activePlayer;
        var previousOpponentPlayer = game.opponentPlayer;

        game.endTurn();

        expect(game.activePlayer).toBe(previousOpponentPlayer);
        expect(game.opponentPlayer).toBe(previousActivePlayer);
    });

});
