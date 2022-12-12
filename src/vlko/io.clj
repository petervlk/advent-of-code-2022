(ns vlko.io
  (:require [clojure.java.io :as io]
            [clojure.string :as s]))

(defn file-lines [file-name]
  (-> (io/resource file-name)
      slurp
      (s/split #"\n")))
