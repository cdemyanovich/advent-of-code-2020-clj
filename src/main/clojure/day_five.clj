(ns day-five
  (:require [clojure.java.io :as io]))

(def input
  (->> "data/5-1.txt"
       io/resource
       io/reader
       line-seq))

(defn choose [positions identifier]
  (let [midpoint (/ (count positions) 2)
        groups (split-at midpoint positions)]
    (cond
      (#{\F \L} identifier) (first groups)
      (#{\B \R} identifier) (last groups))))

(defn find-position [positions identifiers]
  (if (= 1 (count positions))
    (first positions)
    (recur (choose positions (first identifiers)) (rest identifiers))))

(defn seat-id [row column]
  (+ (* 8 row) column))

(defn ->seat-id [boarding-pass rows columns]
  (let [[row-pattern column-pattern] (split-at 7 boarding-pass)
        row (find-position rows row-pattern)
        column (find-position columns column-pattern)
        seat-id (seat-id row column)]
    seat-id))

(def possible-rows (range 0 128))

(def possible-columns (range 0 8))

(defn part-one [boarding-passes possible-rows possible-columns]
  (->> boarding-passes
       (map #(->seat-id % possible-rows possible-columns))
       (apply max)))

(comment
  (= 998 (part-one input possible-rows possible-columns)))

(comment
  (split-at 7 "BBFFBBFRLL")
  (= 70 (find-position possible-rows "BFFFBBF"))
  (= 14 (find-position possible-rows "FFFBBBF"))
  (= 102 (find-position possible-rows "BBFFBBF"))
  (= 7 (find-position possible-columns "RRR"))
  (= 4 (find-position possible-columns "RLL"))
  (= 119 (seat-id (find-position possible-rows "FFFBBBF") (find-position possible-columns "RRR")))
  (= 567 (seat-id (find-position possible-rows "BFFFBBF") (find-position possible-columns "RRR")))
  (= 820 (seat-id (find-position possible-rows "BBFFBBF") (find-position possible-columns "RLL"))))