(ns day-three
  (:require [clojure.java.io :as io]))

(def forest
  (->> "data/3-1.txt"
       io/resource
       io/reader
       line-seq))

(defn tree? [spot] (= \# spot))

(defn travel [forest right down]
  (->> forest
       (take-nth down)
       (map-indexed (fn [index item]
                      (get item 
                           (mod (* right index)
                                (count item)))))))

(defn part-one [forest]
  (->> (travel forest 3 1)
       (filter tree?)
       count))

(defn part-two [forest]
  (reduce *
          (for [[right down] [[1 1] [3 1] [5 1] [7 1] [1 2]]]
            (->> (travel forest right down)
                 (filter tree?)
                 count))))

(comment
  (= 178 (part-one forest))
  (= 3492520200 (part-two forest)))
