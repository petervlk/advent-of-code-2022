(ns vlko.day09
  (:require
   [clojure.string :as s]
   [vlko.io]))

(def moves
  {"R" [0 1]
   "L" [0 -1]
   "U" [1 0]
   "D" [-1 0]})

(defn new-tail-coord-diagonal-movement
  [new-head old-tail]
  (if (= 2 (abs (- new-head old-tail)))
    ((comp #(quot % 2) +) new-head old-tail)
    new-head))

(def new-tail-coord-line-movement (comp #(quot % 2) +))

(defn follow-head
  [new-head old-tail]
  (let [delta (map - new-head old-tail)]
    (cond
      (not (some #(= 2 (abs %)) delta)) old-tail
      (some zero? delta)                (mapv new-tail-coord-line-movement     new-head old-tail)
      :else                             (mapv new-tail-coord-diagonal-movement new-head old-tail))))

(defn move-head
  [state move]
  (let [[old-head & old-tail] (peek state)
        new-head (mapv + old-head move)]
    (reduce (fn [state tail]
              (let [moving-rope (peek state)
                    last-moved-part (peek moving-rope)]
                (conj
                 (pop state)
                 (conj moving-rope (follow-head last-moved-part tail)))))
            (conj state [new-head]) old-tail)))

(defn process-line
  [state line]
  (let [[direction n] (s/split line #" ")
        num-moves (Integer/valueOf n)
        move (get moves direction)]
    (reduce move-head state (repeat num-moves move))))

(defn count-distinct-tail-positions
  [instructions rope-length]
  (let [init-state [(vec (repeat rope-length [0 0]))]]
    (->> (reduce process-line init-state instructions)
         (map peek)
         distinct
         count)))

(defn solution
  []
  (count-distinct-tail-positions (vlko.io/file-lines "input9.txt") 2))

(defn solution-bonus
  []
  (count-distinct-tail-positions (vlko.io/file-lines "input9.txt") 10))
