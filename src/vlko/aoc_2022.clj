(ns vlko.aoc-2022
  (:gen-class)
  (:require [vlko.calorie-count :as calorie-count]))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Answer1: " (calorie-count/solution))
  (println "Answer1 bonus: " (calorie-count/solution-bonus))
  )
