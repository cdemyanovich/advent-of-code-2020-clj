(ns day-two
  (:require [clojure.java.io :as io]))

(def input
  (->> "data/2-1.txt"
       io/resource
       io/reader
       line-seq))

(def password-record-pattern #"(\d+)-(\d+) (\w): (\w+)")

(def password-record-keys [:min :max :character :password])

(defn ->password-entry [password-record]
  (->> password-record
       (re-find password-record-pattern)
       rest ;; ignore the first item in the vector from re-find, since it's the whole string
       (zipmap password-record-keys)))

(defn valid-password? [password-entry]
  (let [min (Integer/parseInt (:min password-entry))
        max (Integer/parseInt (:max password-entry))
        character (first (seq (:character password-entry)))
        password (:password password-entry)
        frequencies (frequencies password)
        character-frequency (get frequencies character 0)]
    (and (>= character-frequency min) (<= character-frequency max))))

(defn part-one [password-records]
  (->> password-records
       (map ->password-entry)
       (filter valid-password?)
       count))

(comment
  (part-one input))