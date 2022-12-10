(ns vlko.day08
  (:require [clojure.string :as s]
            [clojure.java.io :as io]))

(defn vec-visible?
  [xs idx]
  (let [before (take idx xs)
        after (drop (inc idx) xs)
        v (nth xs idx)]
    (or
      (some empty? [before after])
      (every? #(< % v) before)
      (every? #(< % v) after))))

(defn row-visible?
  [matrix row col]
  (vec-visible? (nth matrix row) col))

(defn col-visible?
  [matrix row col]
  (vec-visible? (mapv #(nth % col) matrix) row))

(defn visible?
  [matrix row col]
  (some true? ((juxt row-visible? col-visible?) matrix row col)))

(defn input []
  (->> (io/resource "input8.txt")
       slurp
       s/split-lines
       (mapv #(s/split % #""))
       (mapv (fn [row] (mapv #(Integer/valueOf %) row)))))

(defn viewing-distance
  [base trees]
  (if (every? #(< % base) trees)
    (count trees)
    (inc (count (take-while #(< % base) trees)))))

(defn directional-scenic-score
  [xs idx]
  (let [before (reverse (take idx xs))
        after (drop (inc idx) xs)
        v (nth xs idx)]
    (->> [before after]
         (map (partial viewing-distance v))
         (apply *))))

(defn scenic-score [matrix row col]
  (*
    (directional-scenic-score (nth matrix row) col)
    (directional-scenic-score (mapv #(nth % col) matrix) row)))

(defn solution
  []
  (let [matrix (input)]
    (->> (for [row (range (count matrix))
               col (range (count (first matrix)))]
           (visible? matrix row col))
         (filter true?)
         count)))

(defn solution-bonus
  []
  (let [matrix (input)]
    (->> (for [row (range (count matrix))
               col (range (count (first matrix)))]
           (scenic-score matrix row col))
         (apply max))))
