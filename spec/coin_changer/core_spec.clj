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
             (change-coins 5)))

  (it "Should change 7 cents"
    (should= [5 1 1]
             (change-coins 7)))

  (it "Should change 18 cents"
    (should= [10 5 1 1 1]
             (change-coins 18))))

(describe "Coin changer state"
  (it "Should start out empty and with an amount"
    (let [amount 27
          cc (base-coin-changer amount)]
      (should= amount
               (:amount cc))
      (should= []
               (:coins  cc))))

  (it "Should process 1 cent correctly"
    (let [cc (base-coin-changer 1)]
      (should= {:amount 0
                :coins [1]}
               (update-coins-for-denomination cc 1))))

  (it "Should have 2 cents remaining for 7 updated with nickels"
    (let [cc (base-coin-changer 7)]
      (should= {:amount 2
                :coins [5]}
               (update-coins-for-denomination cc 5))))

  (it "Should have 12 cents remaining for 37 updated with quarters"
    (let [cc (base-coin-changer 37)]
      (should= {:amount 12
                :coins [25]}
               (update-coins-for-denomination cc 25)))))
