(ns vlko.day04
  (:require
   [clojure.java.io :as io]
   [clojure.string :as s]))

(defn interval [s]
  (map #(Integer/valueOf %) (s/split s #"-")))

(defn line->intervals
  [line]
  (map interval (s/split line #",")))

(defn count-matches
  [filter-fn]
  (with-open [rdr (io/reader (io/resource "input4.txt"))]
    (->> (line-seq rdr)
         (map line->intervals)
         (filter filter-fn)
         count)))

(defn fully-contains?
  [[[s1 e1] [s2 e2]]]
  (or (<= s1 s2 e2 e1)
      (<= s2 s1 e1 e2)))

(defn overlaps?
  [[[s1 e1] [s2 e2]]]
  (or
    (<= s1 s2 e1)
    (<= s1 e2 e1)
    (<= s2 s1 e2)
    (<= s2 e1 e2)))

(defn solution [] (count-matches fully-contains?))
(defn solution-bonus [] (count-matches overlaps?))
