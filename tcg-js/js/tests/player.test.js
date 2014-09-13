describe("A Player", function () {
    var player;
    var opponent;

    beforeEach(function () {
        player = new Player("player");
        opponent = new Player("opponent");
    });

    it("should have an initial health of 30", function () {
        expect(player.health).toBe(30);
    });

    it("should have an initial mana of zero", function () {
        expect(player.mana).toBe(0);
    });

    it("should have an initial maximum mana of zero", function () {
        expect(player.manaSlots).toBe(0);
    });

    it("should have the 20 default cards in deck", function () {
        expect(player.deck).toEqual([0, 0, 1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 5, 5, 6, 6, 7, 8]);
    });

    it("should have no cards in hand", function () {
        expect(player.hand.length).toBe(0);
    });

    it("should move a card from the deck into the hand when drawing a card", function () {
        player.deck = [1, 2, 3];
        player.hand = [];

        player.drawCard();

        expect(player.deck.length).toBe(2);
        expect(player.hand.length).toBe(1);
    });

    it("should discard drawn card when hand already contains 5 cards", function () {
        player.deck = [1];
        player.hand = [4, 5, 6, 7, 8];

        player.drawCard();

        expect(player.deck.length).toBe(0);
        expect(player.hand).toEqual([4, 5, 6, 7, 8]);
    });

    it("should receive one damage when drawing from an empty deck", function () {
        player.health = 30;
        player.deck = [];

        player.drawCard();

        expect(player.health).toBe(29);
    });

    it("should deal damage equal to played card's value to opponent", function () {
        opponent.health = 30;
        player.hand = [8];

        player.playCard(8, opponent);

        expect(opponent.health).toBe(22);
    });

    it("should heal amount equal to played card's value to oneself", function () {
        player.health = 10;
        player.hand = [8];

        player.playCard(8, player);

        expect(player.health).toBe(18);
    });

    it("should cap healing at 30 health", function () {
        player.health = 28;
        player.hand = [5];

        player.playCard(5, player);

        expect(player.health).toBe(30);
    });

});
