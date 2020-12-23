(ns day-02
  (:require [clojure.java.io :as io]))

(def input
  (->> "data/2-1.txt"
       io/resource
       io/reader
       line-seq))

(def password-record-pattern #"(\d+)-(\d+) (\w): (\w+)")

(def password-record-keys [:num1 :num2 :character :password])

(defn parse
  "Parse the record into a vector. Discard the first item in the vector, since it's the whole record."
  [password-record]
  (rest (re-find password-record-pattern password-record)))

(defn transform [password-entry]
  (assoc password-entry
         :num1 (Integer/parseInt (:num1 password-entry))
         :num2 (Integer/parseInt (:num2 password-entry))
         :character (first (seq (:character password-entry)))))

(defn ->password-entry [password-record]
  (->> password-record
       parse
       (zipmap password-record-keys)
       transform))

(defn valid-password-1? [password-entry]
  (let [frequencies (frequencies (:password password-entry))
        character-frequency (get frequencies (:character password-entry) 0)]
    (and (>= character-frequency (:num1 password-entry)) 
         (<= character-frequency (:num2 password-entry)))))

(defn valid-password-2? [password-entry]
  (let [index1 (- (:num1 password-entry) 1)
        index2 (- (:num2 password-entry) 1)
        character (:character password-entry)
        password (:password password-entry)
        characters-at-positions [(.charAt password index1) 
                                 (.charAt password index2)]]
    (= 1 (get (frequencies characters-at-positions) character))))

(defn part-one [password-records]
  (->> password-records
       (map ->password-entry)
       (filter valid-password-1?)
       count))

(defn part-two [password-records]
  (->> password-records
       (map ->password-entry)
       (filter valid-password-2?)
       count))

(comment
  (part-one input))

(comment
  (part-two input))
