(ns day-10
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def sample-joltages-1-1 [16
                          10
                          15
                          5
                          1
                          11
                          7
                          19
                          6
                          12
                          4])

(def sample-joltages-1-2 [28
                          33
                          18
                          42
                          31
                          14
                          46
                          20
                          48
                          47
                          24
                          23
                          49
                          45
                          19
                          38
                          39
                          11
                          1
                          32
                          25
                          35
                          8
                          17
                          7
                          9
                          4
                          2
                          34
                          10
                          3])

(def charging-outlet-joltage 0)

(defn highest-adapter-joltage
  [adapter-joltages]
  (apply max adapter-joltages))

(defn device-joltage
  [highest-joltage]
  (+ 3 highest-joltage))

(defn transform-joltages
  [joltages]
  (->> joltages
       sort
       (cons charging-outlet-joltage)
       vec
       (#(conj % (device-joltage (highest-adapter-joltage %))))))

(defn differences
  [joltages]
  (->> joltages
       (partition 2 1)
       (map (comp (partial apply -) reverse))))

(defn select-vals
  [keyseq map]
  (vals (select-keys map keyseq)))

(defn part-one
  [joltages]
  (->> joltages
       transform-joltages
       differences
       frequencies
       (select-vals [1 3])
       (apply *)))

(defn read-joltages
  []
  (->> "data/10-1.txt"
       io/resource
       slurp
       str/split-lines
       (map #(Integer/parseInt %))))

(comment
  (= 35 (part-one sample-joltages-1-1))
  (= 220 (part-one sample-joltages-1-2))
  (= 2240 (part-one (read-joltages)))
  ,)
