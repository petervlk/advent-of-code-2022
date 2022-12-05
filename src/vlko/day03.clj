(ns vlko.day03
  (:require
   [clojure.java.io :as io]
   [clojure.set :as sets]))

(defn- error-item [data]
  (->> (split-at (/ (count data) 2) data)
       (map set)
       (apply sets/intersection)
       first))

(defn- item-priority [item]
  (let [base (if (<= (int \a) (int item) (int \z))
               {:char \a :offset 1}
               {:char \A :offset 27})]
    (+ (base :offset)
       (- (int item) (int (base :char))))))

(defn solution
  []
  (with-open [rdr (io/reader (io/resource "input3.txt"))]
    (->> (line-seq rdr)
         (map (comp item-priority error-item))
         (apply +))))

(defn solution-bonus
  []
  (with-open [rdr (io/reader (io/resource "input3.txt"))]
    (->> (vec (line-seq rdr))
         (partition 3)
         (map (comp
                item-priority
                first
                #(apply sets/intersection %)
                #(map set %)))
         (apply +))))
