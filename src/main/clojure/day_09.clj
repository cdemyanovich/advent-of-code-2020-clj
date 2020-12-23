(ns day-09
  (:require [clojure.java.io :as io]
            [clojure.math.combinatorics :as combinatorics]
            [clojure.string :as str]))

(defn find-non-sum
  [numbers]
  (let [target-sum (last numbers)
        candidates (butlast numbers)
        pairs (combinatorics/combinations candidates 2)
        sums (map #(reduce + %) pairs)]
    (when (not (contains? (set sums) target-sum))
      target-sum)))

;; partition list into lists of size preamble + 1
;; process each sub-list as follows:
;; set candidates to all items but the last item
;; set target sum to the last item
;; group numbers in distinct pairs ([1 2] is valid; [1 1] and [2 2] are not)
;; if the set of candidates contains no distinct pair whose sum is the target sum, return the target sum; else return nil
;; return first non-nil item
(defn part-one [numbers preamble-size]
  (->> numbers
       (partition (+ 1 preamble-size) 1)
       (map find-non-sum)
       (filter #(not (nil? %)))
       first))

(defn sums-to?
  [numbers target-sum]
  (= target-sum (reduce + numbers)))

(defn part-two [numbers preamble-size]
  (let [target-sum (part-one numbers preamble-size)]
    (loop [size 2]
      (let [partitions (partition size 1 numbers)
            found-partition (first (filter #(sums-to? % target-sum) partitions))]
        (if found-partition
          (+ (apply min found-partition) (apply max found-partition))
          (recur (inc size)))))))

(def sample-input [35
                   20
                   15
                   25
                   47
                   40
                   62
                   55
                   65
                   95
                   102
                   117
                   150
                   182
                   127
                   219
                   299
                   277
                   309
                   576])

(def input
  (->> "data/9-1.txt"
       io/resource
       slurp
       str/split-lines
       (map #(Long/parseLong %))))

(comment
  (= 127 (part-one sample-input 5))
  (= 552655238 (part-one input 25))
  (= 62 (part-two sample-input 5))
  (= 70672245 (part-two input 25))
  ,)
