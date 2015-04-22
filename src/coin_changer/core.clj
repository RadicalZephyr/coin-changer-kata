(ns coin-changer.core)

(defn number-of-coins [denom amount]
  (quot amount denom))

(defn coins-for-denomination [denom num-coins]
  (repeat num-coins denom))

(defn value-of [coin-list]
  (reduce + coin-list))

(defn change-coins-iter [amount]
  (vec
   (loop [amount        amount
          denominations [25 10 5 1]
          result        []]
     (if (seq denominations)
       (let [[denom & denominations] denominations
             num-coins (number-of-coins denom amount)
             coin-list (coins-for-denomination denom num-coins)
             coins-value (value-of coin-list)]
         (recur (- amount coins-value)
                denominations
                (concat result coin-list)))
       result))))

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

(defn change-coins [amount]
  (change-coins-fun amount))
