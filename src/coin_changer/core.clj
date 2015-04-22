(ns coin-changer.core)

;; Helper functions

;; These were extracted from the body of change-coins-iter largely for
;; readability and to give the concepts they represent names.
;; Conveniently, the first two were useful enough to be directly
;; reused in change-coins-fn

(defn number-of-coins [denom amount]
  (quot amount denom))

(defn coins-for-denomination [denom num-coins]
  (repeat num-coins denom))

(defn value-of [coin-list]
  (reduce + coin-list))


;; Imperative Style

(defn change-coins-iter [amount]
  (vec
   (loop [amount        amount
          denominations [25 10 5 1]
          result        []]
     (if (seq denominations)
       (let [[denom & denominations] denominations ; Use destructuring to separate denominations into a first and rest
             num-coins (number-of-coins denom amount)
             coin-list (coins-for-denomination denom num-coins)
             coins-value (value-of coin-list)]
         (recur (- amount coins-value)
                denominations  ; Use the implicit rest from the destructuring
                (concat result coin-list)))
       result))))


;; Stream-Oriented Style

;; The basic concept here is that we're explicitly modeling the state
;; of our coin changer as a map of amount and the coin list built so
;; far. Then we define a function that updates this application state
;; by applying the changes for a specific denomination.

(defn base-coin-changer [amount]
  {:amount amount
   :coins []})

(defn update-coins-for-denomination [coin-changer denom]
  (let [num-coins (number-of-coins denom (:amount coin-changer))]
    (-> coin-changer
        (update-in [:coins] #(concat % (coins-for-denomination denom num-coins)))
        (update-in [:amount] #(rem % denom)))))

(defn change-coins-fun [amount]
  (:coins (reduce update-coins-for-denomination
                  (base-coin-changer amount)
                  [25 10 5 1])))


;; Change which change-coins-* function is called here to test both.

(defn change-coins [amount]
  (change-coins-fun amount))
