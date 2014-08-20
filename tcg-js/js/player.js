if (typeof NS == 'undefined') { NS = {}; }

NS.player = function() {
    return {
        health: 30,
            mana: 0,
        maxMana: 0,
        deck: [0,0,1,1,2,2,2,3,3,3,3,4,4,4,5,5,6,6,7,8],
        hand: []
    }
}