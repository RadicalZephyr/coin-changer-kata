(ns coin-changer.core)

(defn coins-for-denomination [denom num-coins]
  (repeat num-coins denom))

(defn change-coins [amount]
  (vec
   (loop [amount        amount
          denominations [25 10 5 1]
          result        []]
     (if (seq denominations)
       (let [[denom & denominations] denominations
             num-coins (int (/ amount denom))
             coins-value (* num-coins denom)]
         (recur (- amount coins-value)
                denominations
                (concat result (coins-for-denomination denom num-coins))))
       result))))
