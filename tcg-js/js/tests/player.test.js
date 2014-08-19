describe("A Player", function() {
    var player = NS.player;

    it("should have an initial health of 30", function() {
        expect(player.health).toBe(30);
    });

    it("should have an initial mana of zero", function() {
        expect(player.mana).toBe(0);
    });

    it("should have an initial maximum mana of zero", function() {
        expect(player.maxMana).toBe(0);
    });

    it("should have no cards in hand", function() {
        expect(player.hand.length).toBe(0);
    });
});