(ns day-one
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]
            [clojure.math.combinatorics :as combinatorics]))

(def input
  (->> "data/1-1.txt"
       io/resource
       io/reader
       line-seq))

(def entries
  (map edn/read-string input))

(defn in-pairs [entries]
  (combinatorics/selections entries 2))

(defn in-triples [entries]
  (combinatorics/selections entries 3))

(defn sums-to-2020? [terms]
  (= 2020 (reduce + terms)))

(defn part-one [entries]
  (->> entries
       in-pairs
       (filter sums-to-2020?)
       first
       (reduce *)))

(defn part-two [entries]
  (->> entries
       in-triples
       (filter sums-to-2020?)
       first
       (reduce *)))

(comment
  (part-one entries))

(comment
  (part-two entries))