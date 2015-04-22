(ns coin-changer.core-spec
  (:require [speclj.core :refer :all]
            [coin-changer.core :refer :all]))

(describe "Changing coins"
  (it "Should change 1 cent"
    (should= [1]
             (change-coins 1))))
