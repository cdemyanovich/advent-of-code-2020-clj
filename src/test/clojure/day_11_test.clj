(ns day-11-test
  (:require [clojure.test :refer :all]
            [day-11 :as day-11]))

(deftest find-neighbors
  (testing "A cell in an empty grid has no neighbors"
    (is (= [] (day-11/neighbors [0 0] {}))))

  (testing "A cell in a 1x1 grid has no neighbors"
    (is (= [] (day-11/neighbors [0 0] {[0 0] \L}))))

  (testing "Each cell in a 2x2 grid has 3 neighbors"
    (is (= [\. \L \L] (day-11/neighbors [0 0] {[0 0] \L [0 1] \.
                                               [1 0] \L [1 1] \L})))

    (is (= [\L \L \L] (day-11/neighbors [0 1] {[0 0] \L [0 1] \.
                                               [1 0] \L [1 1] \L})))

    (is (= [\L \. \L] (day-11/neighbors [1 0] {[0 0] \L [0 1] \.
                                               [1 0] \L [1 1] \L})))

    (is (= [\L \. \L] (day-11/neighbors [1 1] {[0 0] \L [0 1] \.
                                               [1 0] \L [1 1] \L}))))

  #_(testing "A cell outside a 2x2 grid has no neighbors"
      (is (=  [] (day-11/neighbors [2 2] {[0 0] \L [0 1] \.
                                          [1 0] \L [1 1] \L})))))

(deftest transform-cell
  (testing "If a seat is empty (L) and there are no occupied seats adjacent to it, the seat becomes occupied."
    (is (= \# (day-11/transform-value \L [\L \L \L]))))
  
  (testing "If a seat is occupied (#) and four or more seats adjacent to it are also occupied, the seat becomes empty."
    (is (= \L (day-11/transform-value \# [\# \# \# \#])))
    (is (= \L (day-11/transform-value \# [\# \# \# \# \#]))))
  
  (testing "Otherwise, the seat's state does not change."
    (is (= \L (day-11/transform-value \L [\L \# \L])))
    (is (= \# (day-11/transform-value \# [\# \# \#]))))
  
  (testing "A floor space does not change."
    (is (= \. (day-11/transform-value \. [\L \# \L])))
    (is (= \. (day-11/transform-value \. [\# \L \#])))))
