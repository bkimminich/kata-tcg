(ns game_test
  (use game)
  (use clojure.test)
  )

(deftest player-test
  (is (= (player :health) 30))
  (is (= (player :mana) 0))
  (is (= (player :maxMana) 0))
  (is (= (player :deck) (list 0 0 1 1 2 2 2 3 3 3 3 4 4 4 5 5 6 6 7 8)))
  (is (= (player :hand) (list)))
  )

(run-tests)