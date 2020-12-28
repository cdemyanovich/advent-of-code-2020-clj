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

; inspired by https://github.com/JoshCheek/advent_of_code/tree/64848a82e30fdc8d614c2ebefd12407e1df7d3cd/2020/10
(defn part-two
  [joltages]
  (loop [counts {0 1}
         joltages (sort joltages)]
    (if (= 0 (count joltages))
      (last (vals (sort counts)))
      (recur (assoc counts (first joltages) (+ (get counts (- (first joltages) 1) 0)
                                               (get counts (- (first joltages) 2) 0)
                                               (get counts (- (first joltages) 3) 0)))
             (rest joltages)))))

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
  (= 99214346656768 (part-two (read-joltages)))
  ,)

; Other interesting approaches:
; * https://github.com/ocisly/advent2020/blob/a2de1307c5d5527702d2d426b10735a3c69c0e51/day-10.clj
; * https://cestlaz.github.io/post/advent-2020-day0910/
; * https://www.reddit.com/r/adventofcode/comments/kh2nr8/2020_day_10_part_2_a_closedform_mathematical/
; * https://www.reddit.com/r/adventofcode/comments/kabi91/2020_day_10_closedform_mathematical_solution/
