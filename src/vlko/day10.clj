(ns vlko.day10
  (:require
   [clojure.java.io :as io]
   [clojure.string :as s]))

(def state
  {:sum []
   :register 1
   :completed-cycles 0})

(defn instruction-cycles
  [instruction]
  (let [[_ x] (s/split instruction #" ")
        base-cycles [{:completed-cycles 1}]]
    (if (nil? x)
      base-cycles
      (conj base-cycles {:completed-cycles 1
                         :register (Integer/valueOf x)}))))

(defn reduce-fn
  [state instruction-cycle]
  (let [new-state (merge-with + state instruction-cycle)]
    (if-not (zero? (mod (- (:completed-cycles new-state) 20) 40))
      new-state
      (update new-state :sum conj (* (:completed-cycles new-state) (:register state))))))


(defn signal-strength-sum
  [instructions]
  (->> instructions
       (mapcat instruction-cycles)
       (reduce reduce-fn state)
       :sum
       (apply +)))

(defn solution
  []
  (->> (io/resource "input10.txt")
       slurp
       s/split-lines
       signal-strength-sum))
