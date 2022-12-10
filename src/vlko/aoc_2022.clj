(ns vlko.aoc-2022
  (:gen-class)
  (:require
   [vlko.day01 :as day01]
   [vlko.day02 :as day02]
   [vlko.day03 :as day03]
   [vlko.day04 :as day04]
   [vlko.day05 :as day05]
   [vlko.day06 :as day06]
   [vlko.day07 :as day07]
   [vlko.day08 :as day08]))

(defn report-day
  [day-label part1-res part2-res]
  (println day-label "Answer p1:" part1-res)
  (println day-label "Answer p2:" part2-res))

(defn -main
  "I don't do a whole lot ... yet."
  [& _args]
  (report-day "Day 01" (day01/solution) (day01/solution-bonus))
  (report-day "Day 02" (day02/solution) (day02/solution-bonus))
  (report-day "Day 03" (day03/solution) (day03/solution-bonus))
  (report-day "Day 04" (day04/solution) (day04/solution-bonus))
  (report-day "Day 05" (day05/solution) (day05/solution-bonus))
  (report-day "Day 06" (day06/solution) (day06/solution-bonus))
  (report-day "Day 07" (day07/solution) (day07/solution-bonus))
  (report-day "Day 08" (day08/solution) (day08/solution-bonus)))

(-main)
