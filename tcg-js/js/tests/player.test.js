describe("A Player", function() {
    var player = NS.player();

    it("should have an initial health of 30", function() {
        expect(player.health).toBe(30);
    });

    it("should have an initial mana of zero", function() {
        expect(player.mana).toBe(0);
    });

    it("should have an initial maximum mana of zero", function() {
        expect(player.maxMana).toBe(0);
    });

    it("should have the 20 default cards in deck", function() {
        expect(player.deck).toEqual([0,0,1,1,2,2,2,3,3,3,3,4,4,4,5,5,6,6,7,8]);
    });

    it("should have no cards in hand", function() {
        expect(player.hand.length).toBe(0);
    });

});
