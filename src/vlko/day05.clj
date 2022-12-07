(ns vlko.day05
  (:require [clojure.java.io :as io]
            [clojure.string :as s]))

(defn parse-stacks [stacks]
  (let [space-char? (fn [c] (= c \space))]
    (->> stacks
         drop-last
         (apply map list)
         (map #(drop-while space-char? %))
         (remove #(or (empty? %) (#{\[ \]} (first %))))
         (into []))))

(defn parse-move
  [input]
  (->> (s/split input #" ")
       (partition 2)
       (map (fn [[k v]] [(keyword k) (Integer/valueOf v)]))
       (into {})))

(defn parse-moves
  [input]
  (map parse-move input))

(defn rearrange-crates
  [crane-fn]
  (let [[stacks-input moves-input] (split-with (complement s/blank?) (s/split (slurp (io/resource "input5.txt")) #"\n"))
        stacks (parse-stacks stacks-input)
        moves (parse-moves (rest moves-input))]
    (reduce crane-fn stacks moves)))

(defn move-crate-single
  [stacks orig-idx dest-idx]
  (let [stack (partial nth stacks)
        crate (first (stack orig-idx))]
    (assoc stacks
           orig-idx (rest (stack orig-idx))
           dest-idx (cons crate (stack dest-idx)))))

(defn move-crates-one-by-one
  [stacks {:keys [move from to]}]
  (loop [stacks stacks
         num-crates move]
    (if (zero? num-crates)
      stacks
      (recur
       (move-crate-single stacks (dec from) (dec to))
       (dec num-crates)))))

(defn move-crate-batch
  [stacks orig-idx dest-idx batch-size]
  (let [stack (partial nth stacks)
        batch (take batch-size (stack orig-idx))]
    (assoc stacks
           orig-idx (drop batch-size (stack orig-idx))
           dest-idx (concat batch (stack dest-idx)))))

(defn move-crates-in-batch
  [stacks {:keys [move from to]}]
  (move-crate-batch stacks (dec from) (dec to) move))

(defn solution [] (map first (rearrange-crates move-crates-one-by-one)))
(defn solution-bonus [] (map first (rearrange-crates move-crates-in-batch)))
