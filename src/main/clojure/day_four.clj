(ns day-four
  (:require [clojure.java.io :as io]
            [clojure.set :as set]
            [clojure.string :as str]))


(def input (slurp (io/resource "data/4-1.txt")))

(def record-keys #{"byr" "cid" "ecl" "eyr" "hcl" "hgt" "iyr" "pid"})

(def passport-keys (disj record-keys "cid"))

(defn parse [raw-passport]
  (->> (str/replace raw-passport #"\n" " ")
       (re-seq #"(\w{3}):(\S+)")
       (map rest)
       flatten
       (apply array-map)))

(defn valid-passport? [candidate passport-keys]
  (set/superset? (set (keys candidate)) passport-keys))

(defn part-one [raw-passports]
  (->> (str/split raw-passports #"\n\n")
       (map parse)
       (filter #(valid-passport? % passport-keys))
       count))

(comment
  (= 226 (part-one input)))
