(ns coin-changer.core-spec
  (:require [speclj.core :refer :all]
            [coin-changer.core :refer :all]))

(describe "Changing coins"
  (it "Should change 1 cent"
    (should= [1]
             (change-coins 1)))

  (it "Should change 4 cent"
    (should= [1 1 1 1]
             (change-coins 4)))

  (it "Should change 5 cents"
    (should= [5]
             (change-coins 5))))
