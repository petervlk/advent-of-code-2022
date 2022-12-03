(ns vlko.calorie-count
  (:require
   [clojure.java.io :as io]
   [clojure.string :as s]))

(defn- line->calories
  "Transform string 'line from file' into integer."
  [line]
  (when-not (s/blank? line) (Integer/valueOf line)))

(defn- increment-calorie-count-of-current-elf
  "Elf bags are represented as a list of numbers.
  Each number in a list represents a sum of calories carried by an elf.
  Only the value of the head of the list can be incremented. All values of
  the tail have already been processed."
  [[head & tail] value-to-add]
  (cons (+ head value-to-add) tail))

(defn- add-new-empty-bag
  "When all calories of items in current elf's bag is summed up,
  a new 'bag' for next elf is inserted into the list."
  [elf-bags]
  (cons 0 elf-bags))

(defn- process-file-line
  "Input from file is inspected. Non-blank values are added
  to the sum of current elfs calories.
  If empty line is found, new elf's bag is prepared for processing."
  [elf-bags caloric-item]
  (if-let [calories (line->calories caloric-item)]
    (increment-calorie-count-of-current-elf elf-bags calories)
    (add-new-empty-bag elf-bags)))

(defn- sum-calories-by-elf
  [file-reader]
  (reduce
    process-file-line      ;reducing function
    (list 0)               ;initial value. A list containing single int (first bag ready to be processed)
    (line-seq file-reader) ;stream of file lines
    ))

(defn solution
  "Entry point to solution calculation.
  Sums up calories carried by each elf and returns maximum value."
  []
  (with-open [rdr (io/reader (io/resource "input1.txt"))]
    (apply max (sum-calories-by-elf rdr))) )
