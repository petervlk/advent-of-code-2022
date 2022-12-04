(ns vlko.day02
  (:require
   [clojure.java.io :as io]
   [clojure.string :as s]))

(defn line->choices
  [line]
  (s/split line #" "))

(defn win?
  [opponents-choice mine-choice]
  (or
    (= (dec mine-choice) opponents-choice)
    (= (+ mine-choice 2) opponents-choice)))

(defn draw?
  [opponents-choice mine-choice]
  (= opponents-choice mine-choice))

(defn round-score
  [[opponents-choice mine-choice]]
  (+ mine-choice
     (cond
       (win? opponents-choice mine-choice)  6
       (draw? opponents-choice mine-choice) 3
       :else                                0)))

(defn expected-score [choice-mapper-fn]
  (with-open [rdr (io/reader (io/resource "input2.txt"))]
    (->> (line-seq rdr)
         (map (comp round-score choice-mapper-fn line->choices))
         (apply +))))

(defn choices->vals
  [choices]
  (map {"A" 1 "B" 2 "C" 3 "X" 1 "Y" 2 "Z" 3} choices))

(defn solution
  []
  (expected-score choices->vals))

(defn outcome->vals [[opponents-choice round-outcome]]
  (let [opponents-val ({"A" 1 "B" 2 "C" 3} opponents-choice)
        mine-val-fn (fn [opponents-val expected-outcome]
                   (case expected-outcome
                     "X" (if (= opponents-val 1) 3 (dec opponents-val))
                     "Y" opponents-val
                     "Z" (if (= opponents-val 3) 1 (inc opponents-val))))]
    [opponents-val (mine-val-fn opponents-val round-outcome)]))

(defn solution-bonus
  []
  (expected-score outcome->vals))
