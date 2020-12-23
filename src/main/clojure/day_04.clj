(ns day-04
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

(defn has-required-fields? [candidate passport-keys]
  (set/superset? (set (keys candidate)) passport-keys))

(defn valid-passport-one? [candidate passport-keys]
  (has-required-fields? candidate passport-keys))

(defn in-range? [number min max]
  (let [number' (Integer/parseInt number)]
    (and (>= number' min)
         (<= number' max))))

(defn valid-birth-year? [birth-year]
  (in-range? birth-year 1920 2002))

(defn valid-issue-year? [issue-year]
  (in-range? issue-year 2010 2020))

(defn valid-expiration-year? [expiration-year]
  (in-range? expiration-year 2020 2030))

(defn valid-height? [height]
  (let [[_ number unit] (re-find #"(\d+)(cm|in)" height)]
    (cond
      (= "cm" unit) (in-range? number 150 193)
      (= "in" unit) (in-range? number 59 76)
      :else false)))

(defn valid-hair-color? [hair-color]
  (some? (re-find #"#[0-9a-f]{6}" hair-color)))

(defn valid-eye-color? [eye-color]
  (contains? #{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"} eye-color))

(defn valid-passport-id? [passport-id]
  (some? (re-matches #"\d{9}" passport-id)))

(defn valid-passport-two? [candidate passport-keys]
  (and (has-required-fields? candidate passport-keys)
       (valid-birth-year? (get candidate "byr"))
       (valid-issue-year? (get candidate "iyr"))
       (valid-expiration-year? (get candidate "eyr"))
       (valid-height? (get candidate "hgt"))
       (valid-hair-color? (get candidate "hcl"))
       (valid-eye-color? (get candidate "ecl"))
       (valid-passport-id? (get candidate "pid"))))

(defn part-one [raw-passports]
  (->> (str/split raw-passports #"\n\n")
       (map parse)
       (filter #(valid-passport-one? % passport-keys))
       count))

(defn part-two [raw-passports]
  (->> (str/split raw-passports #"\n\n")
       (map parse)
       (filter #(valid-passport-two? % passport-keys))
       count))

(comment
  (= 226 (part-one input))
  (= 160 (part-two input))
  ,)