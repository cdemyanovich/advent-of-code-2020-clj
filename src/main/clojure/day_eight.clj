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

(defn execute
  [state executions program]
  (if (contains? executions (:instruction-pointer state))
    state
    (let [instruction (nth program (:instruction-pointer state))]
      (recur (((:operation instruction) operations) (:argument instruction) state)
             (conj executions (:instruction-pointer state))
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
       (execute initial-state #{})
       (:accumulator)))

(comment
  (= 5 (part-one sample-program))
  (= 1610 (part-one input)))