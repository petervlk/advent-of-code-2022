(ns vlko.calorie-count
  (:require
   [clojure.java.io :as io]
   [clojure.string :as s]))

(defn- line->calories
  [line]
  (when-not (s/blank? line) (Integer/valueOf line)))

(defn- increment-calorie-count-of-current-elf
  [[head & tail] valeu-to-add]
  (cons (+ head valeu-to-add) tail))

(defn- add-new-empty-bag
  [elf-bags]
  (cons 0 elf-bags))

(defn- increment-calorie-count-of-last-elf-bag
  [elf-bags caloric-item]
  (if-let [calories (line->calories caloric-item)]
    (increment-calorie-count-of-current-elf elf-bags calories)
    (add-new-empty-bag elf-bags)))

(defn- sum-calories-by-elf
  [rdr]
  (reduce
    increment-calorie-count-of-last-elf-bag
    (list 0)
    (line-seq rdr)))

(defn solution
  []
 (with-open [rdr (io/reader (io/resource "input1.txt"))]
   (apply max (sum-calories-by-elf rdr))) )
