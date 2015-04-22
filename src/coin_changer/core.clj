(ns coin-changer.core)

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
                (concat result (repeat num-coins denom))))
       result))))
