(ns day-06
  (:require [clojure.java.io :as io]
            [clojure.set :as set]
            [clojure.string :as str]))

(def input (slurp (io/resource "data/6-1.txt")))

(defn part-one [answers]
  (->> (str/split answers #"\n\n")
       (map #(str/replace % #"\n" ""))
       (map set)
       (map count)
       (reduce +)))

(defn part-two [answers]
  (->> (str/split answers #"\n\n")
       (map str/split-lines)
       (map #(map set %))
       (map #(apply set/intersection %))
       (map count)
       (reduce +)))

(comment
  (= 7128 (part-one input))
  (= 3640 (part-two input)))