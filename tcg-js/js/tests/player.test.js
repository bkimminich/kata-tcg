describe("A Player", function() {
    var player = NS.player;

    it("should have an initial health of 30", function() {
        expect(player.health).toBe(30);
    });
});