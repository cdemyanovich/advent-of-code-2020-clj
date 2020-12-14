(ns day-eight
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn parse-instruction
  [line]
  (let [[operation argument] (str/split line #" ")]
    {:operation (keyword operation)
     :argument (Integer/parseInt argument)}))

(def initial-state {:instruction-pointer 0
                    :accumulator 0})

(defn acc
  [argument state]
  (-> state
      (update :instruction-pointer inc)
      (update :accumulator #(+ argument %))))

(defn jmp
  [argument state]
  (update state :instruction-pointer #(+ argument %)))

(defn nop
  [argument state]
  (update state :instruction-pointer inc))

(def operations {:acc acc :jmp jmp :nop nop})

(defn next-index
  [current-index {:keys [operation argument]}]
  (if (= operation :jmp)
    (+ current-index argument)
    (inc current-index)))

(defn next-accumulator
  [current-accumulator {:keys [operation argument]}]
  (if (= operation :acc)
    (+ current-accumulator argument)
    current-accumulator))

(defn record-execution
  [executions index]
  (conj executions index))

(defn already-executed?
  [executions index]
  (contains? executions index))

(defn execute
  ([index accumulator executions program]
   (if (already-executed? executions index)
     accumulator
     (let [instruction (nth program index)]
       (recur (next-index index instruction)
              (next-accumulator accumulator instruction)
              (record-execution executions index)
              program))))
  ([state executions program]
   (if (contains? executions (:instruction-pointer state))
     state
     (let [instruction (nth program (:instruction-pointer state))]
       (recur (((:operation instruction) operations) (:argument instruction) state)
              (conj executions (:instruction-pointer state))
              program)))))

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
       (execute initial-state #{})
       (:accumulator)))

(comment
  (= 5 (part-one sample-program))
  (= 1610 (part-one input)))