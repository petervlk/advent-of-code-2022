(ns vlko.aoc-2022
  (:gen-class)
  (:require
   [vlko.day01 :as day01]
   [vlko.day02 :as day02]))

(defn -main
  "I don't do a whole lot ... yet."
  [& _args]
  (println "Day 01 Answer: " (day01/solution))
  (println "Day 01 Answer bonus: " (day01/solution-bonus))
  (println "Day 02 Answer: " (day02/solution))
  (println "Day 02 Answer bonus: " (day02/solution-bonus)))
