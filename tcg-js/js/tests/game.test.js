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

            expect(game.activePlayer.manaSlots).toBe(previousManaSlots + 1);
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

    it("should let the active player draw a card from his deck at the beginning of a turn", function () {
        var previousNumberOfHandCards = game.activePlayer.hand.length;
        var previousNumberOfDeckCards = game.activePlayer.deck.length;

        game.beginTurn();

        expect(game.activePlayer.hand.length).toBe(previousNumberOfHandCards + 1);
        expect(game.activePlayer.deck.length).toBe(previousNumberOfDeckCards - 1);
    });

    it("should switch the active player at the end of a turn", function () {
        var previousActivePlayer = game.activePlayer;
        var previousOpponentPlayer = game.opponentPlayer;

        game.endTurn();

        expect(game.activePlayer).toBe(previousOpponentPlayer);
        expect(game.opponentPlayer).toBe(previousActivePlayer);
    });

    it("should ask the active player which card to play in his turn", function () {
        game.activePlayer.hand = [2, 3, 7];
        game.activePlayer.mana = 4;
        spyOn(window, 'prompt').and.returnValue('3');

        game.playTurn();

        expect(game.activePlayer.mana).toBe(1);
        expect(game.activePlayer.hand).toEqual([2, 7]);
    });

    it("should not play any cards when cancelling the card choice", function () {
        game.activePlayer.hand = [1, 2];
        game.activePlayer.mana = 10;
        spyOn(window, 'prompt').and.returnValue(null);

        game.playTurn();

        expect(game.activePlayer.mana).toBe(10);
        expect(game.activePlayer.hand).toEqual([1, 2]);
    });

    it("should prevent playing card the active player does not have enough mana for", function () {
        game.activePlayer.hand = [1, 7];
        game.activePlayer.mana = 6;
        spyOn(window, 'prompt').and.returnValue('7');
        spyOn(window, 'confirm').and.returnValue(false);

        game.playTurn();

        expect(window.confirm).toHaveBeenCalledWith('Cannot play card 7 with only 6 mana!');
        expect(game.activePlayer.mana).toBe(6);
        expect(game.activePlayer.hand).toEqual([1, 7]);
    });

    it("should prevent playing card the active player does not have in his hand", function () {
        game.activePlayer.hand = [1, 2, 3];
        game.activePlayer.mana = 10;
        spyOn(window, 'prompt').and.returnValue('4');
        spyOn(window, 'confirm').and.returnValue(false);

        game.playTurn();

        expect(window.confirm).toHaveBeenCalledWith('4 is not present in hand!');
        expect(game.activePlayer.mana).toBe(10);
        expect(game.activePlayer.hand).toEqual([1, 2, 3]);
    });

    it("should reject illegal input for card choice", function () {
        game.activePlayer.hand = [1, 2, 3];
        game.activePlayer.mana = 3;
        spyOn(window, 'prompt').and.returnValue('abc');
        spyOn(window, 'confirm').and.returnValue(false);

        game.playTurn();

        expect(window.confirm).toHaveBeenCalledWith('abc is not a valid card!');
        expect(game.activePlayer.mana).toBe(3);
        expect(game.activePlayer.hand).toEqual([1, 2, 3]);
    });

});
