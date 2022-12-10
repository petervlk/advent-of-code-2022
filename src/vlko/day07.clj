(ns vlko.day07
  (:require [clojure.string :as s]
            [clojure.java.io :as io]))

(defn dir-track
  [dirs dir]
  (if (dirs dir)
    dirs
    (assoc dirs dir {:size 0 :files #{}})))

(defn file-track
  [state file-name file-size]
  (if ((get-in state [:dirs (:path state) :files]) file-name)
    state
    (loop [state state
          path  (:path state)]
     (if (empty? path)
       state
       (recur (-> state
                  (update-in [:dirs path :size] + file-size)
                  (update-in [:dirs path :files] conj file-name))
              (pop path))))))

(defn dir-enter
  [state dir-name]
  (let [new-path (conj (:path state) dir-name)]
    (-> state
        (assoc :path new-path)
        (update :dirs dir-track new-path))))

(defn reduce-fn
  [state cmd]
  (let [cmd (s/split cmd #" ")]
    (cond
      (= cmd ["$" "cd" ".."])           (update state :path pop)
      (= ["$" "cd"] (vec (take 2 cmd))) (dir-enter state (nth cmd 2))
      (not (#{"$" "dir"} (first cmd)))  (file-track state (second cmd) (Integer/valueOf (re-find #"^\d*" (first cmd))))
      :else                             state)))

(defn solution []
  (->> (s/split (slurp (io/resource "input7.txt")) #"\n")
       (reduce reduce-fn {:path [] :dirs {}})
       :dirs
       vals
       (map :size)
       (filter #(>= 100000 %))
       (apply +)))

(defn solution-bonus
  []
  (let [data (s/split (slurp (io/resource "input7.txt")) #"\n")
        dir-sizes (reduce reduce-fn {:path [] :dirs {}} data)
        used-space (get-in dir-sizes [:dirs ["/"] :size])
        free-space (- 70000000 used-space)
        missing-space (- 30000000 free-space)]
    (->> dir-sizes
         :dirs
         vals
         (map :size)
         sort
         (filter #(<= missing-space %))
         (apply min))))
