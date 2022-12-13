(ns vlko.day10
  (:require
   [clojure.string :as s]
   [vlko.io]))

(defn instruction-cycles
  [instruction]
  (let [[_ x] (s/split instruction #" ")
        base-cycles [{:completed-cycles 1}]]
    (if (nil? x)
      base-cycles
      (conj base-cycles {:completed-cycles 1
                         :register (Integer/valueOf x)}))))

(defn signal-strength-collector
  [state instruction-cycle]
  (let [new-state (merge-with + state instruction-cycle)]
    (if-not (zero? (mod (- (:completed-cycles new-state) 20) 40))
      new-state
      (update new-state :sum conj (* (:completed-cycles new-state) (:register state))))))

(defn execute-instructions
  [init-state reduce-fn instructions]
  (->> instructions
      (mapcat instruction-cycles)
      (reduce reduce-fn init-state)))

(defn signal-strength-sum
  [instructions]
  (->> instructions
       (execute-instructions
         {:sum [] :register 1 :completed-cycles 0}
         signal-strength-collector)
       :sum
       (apply +)))

(defn solution
  []
  (signal-strength-sum (vlko.io/file-lines "input10.txt")))

(defn render-fn
  [state instruction-cycle]
  (let [current-row-idx (mod (:completed-cycles state) 40)
        display (if (zero? current-row-idx) (conj (:display state) []) (:display state))
        current-row (peek display)
        rendered-display-rows (pop display)
        sprite (:register state)
        pixel (if (<= (dec sprite) current-row-idx (inc sprite)) \# \.)]
    (merge-with
      +
      (assoc state :display (conj rendered-display-rows (conj current-row pixel)))
      instruction-cycle)))

(defn crt-renderer
  [instructions]
  (->> instructions
       (execute-instructions
         {:display [] :register 1 :completed-cycles 0}
         render-fn)
       :display
       (map #(apply str %))))

(defn solution-bonus
  []
  (crt-renderer (vlko.io/file-lines "input10.txt")))
