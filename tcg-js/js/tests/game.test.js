describe("A Game", function () {
    var game;

    beforeEach(function () {
        game = new Game(new Player("player1"), new Player("player2"));
    });

    it("should have two players", function () {
        expect(game.player1).not.toBe(undefined);
        expect(game.player2).not.toBe(undefined);
    });

});
