(ns vlko.day06
  (:require [clojure.java.io :as io]))

(defn marker-fn
  [marker-length index char-group]
  (when (= marker-length (count (distinct char-group)))
    (+ index marker-length)))

(defn identify-marker [marker-length]
  (->> (slurp (io/resource "input6.txt"))
       (partition marker-length 1)
       (keep-indexed (partial marker-fn marker-length))
       first))

(defn solution [] (identify-marker 4))
(defn solution-bonus [] (identify-marker 14))
