(ns day-eight
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn parse-instruction
  [line]
  (let [[instruction argument] (str/split line #" ")]
    {:instruction instruction
     :argument (Integer/parseInt argument)}))

(defn next-index
  [current-index {:keys [instruction argument]}]
  (if (= instruction "jmp")
    (+ current-index argument)
    (inc current-index)))

(defn next-accumulator
  [current-accumulator {:keys [instruction argument]}]
  (if (= instruction "acc")
    (+ current-accumulator argument)
    current-accumulator))

(defn record-execution
  [executions index]
  (conj executions index))

(defn already-executed?
  [executions index]
  (contains? executions index))

(defn execute
  [index accumulator executions program]
  (if (already-executed? executions index)
    accumulator
    (let [instruction (nth program index)]
      (recur (next-index index instruction)
             (next-accumulator accumulator instruction)
             (record-execution executions index)
             program))))

(def sample-program "nop +0
acc +1
jmp +4
acc +3
jmp -3
acc -99
acc +1
jmp -4
acc +6")

(def input
  (->> "data/8-1.txt"
       io/resource
       slurp))

(defn part-one [program]
  (->> program
       str/split-lines
       (map parse-instruction)
       vec
       (execute 0 0 #{})))

(comment
  (= 5 (part-one sample-program))
  (= 1610 (part-one input)))