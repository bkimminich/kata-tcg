(ns tcg-clojure.core-test
  (:require [clojure.test :refer :all]
            [tcg-clojure.core :refer :all]))

(deftest player-initially-has-30-health
  (testing "Player initially has 30 health."
    (is (contains? tcg-clojure.core.player :health))))
