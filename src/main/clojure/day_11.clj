(ns day-11
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn read-seat-grid
  [file-name]
  (->> (str "data/" file-name)
       (io/resource)
       (slurp)))

(defn parse-seat-grid
  [seat-grid]
  (str/split-lines seat-grid))

(defn ->seat-map
  [grid]
  (into {} (map-indexed
            (fn [row line]
              (into {} (map-indexed (fn [col char]
                                      {[row col] char}) 
                                    line)))
            grid)))

(defn neighbors
  [[x y] grid]
  (for [dx [-1 0 1] :let [i (+ x dx)]
        dy [-1 0 1] :let [j (+ y dy)]
        :when (and (not= dx dy 0) (not (nil? (grid [i j]))))]
    (grid [i j])))

(defn occupied? [cell] (= \# cell))

(defn transform-value
  [cell neighbors]
  (case cell
    \. cell
    \L (if (= 0 (count (filter occupied? neighbors))) \# cell)
    \# (if (<= 4 (count (filter occupied? neighbors))) \L cell)))

; Adapted from _Clojure Cookbook_, 2.21. Applying Functions to Maps.
(defn map-kv
  "Given a map and a function of three arguments, returns the map resulting from applying the function to each of its entries and the map. The provided function must return a pair (a two-element sequence.)"
  [m f]
  (into {} (map (fn [entry]
                  (f (key entry) (val entry) m))
                m)))

(defn transform-entry
  [k v m]
  [k (transform-value v (neighbors k m))])

(defn evolve
  [grid]
  (map-kv grid transform-entry))

(defn find-fixed-point
  [seat-maps]
  (->> seat-maps
       (partition 2 1)
       (filter (fn [[one two]] (= one two)))
       (first)
       (first)))

(defn count-occupied
  [seat-map]
  (count (filter occupied? (vals seat-map))))

(defn part-one
  [input]
  (->> input
       (parse-seat-grid)
       (->seat-map)
       (iterate evolve)
       (find-fixed-point)
       (count-occupied)))

(comment
  (= 37 (part-one (read-seat-grid "day_11_sample.txt")))
  (= 2346 (part-one (read-seat-grid "day_11.txt")))
  ,)
