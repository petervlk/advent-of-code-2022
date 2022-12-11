(ns vlko.day09
  (:require
   [clojure.string :as s]
   [clojure.java.io :as io]))

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
  (let [[old-head old-tail] (peek state)
        new-head (mapv + old-head move)]
    (conj state [new-head (follow-head new-head old-tail)])))

(defn process-line
  [state line]
  (let [[direction n] (s/split line #" ")
        num-moves (Integer/valueOf n)
        move (get moves direction)]
    (reduce move-head state (repeat num-moves move))))

(defn count-distinct-tail-positions
  [instructions]
 (->> (reduce process-line [[[0 0][0 0]]] instructions)
      (map second)
      distinct
      count))

(defn solution
  []
  (-> (slurp (io/resource "input9.txt"))
      (s/split #"\n")
      count-distinct-tail-positions))
