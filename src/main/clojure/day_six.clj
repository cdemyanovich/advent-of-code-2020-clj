(ns day-six
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input (slurp (io/resource "data/6-1.txt")))

(defn part-one [answers]
  (->> (str/split answers #"\n\n")
       (map #(str/replace % #"\n" ""))
       (map set)
       (map count)
       (reduce +)))

(comment
  (= 7128 (part-one input)))