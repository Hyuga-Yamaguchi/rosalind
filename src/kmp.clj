(ns kmp
  (:require [core :refer [rosalind-solve parse-fasta]]
            [clojure.string :as string]))

(defn failure-array
  [t]
  (let [n (count t)]
    (loop [table (vec (concat [-1] (repeat (dec n) 0)))
           j -1
           i 0]
      (if (<= i (dec n))
        (let [j (loop [j j]
                  (if (and (>= j 0) (not= (nth t i) (nth t j)))
                    (recur (get table j))
                    j))]
          (recur (assoc table (inc i) (inc j))
                 (inc j)
                 (inc i)))
        table))))

(defn kmp
  [xs]
  (let [[_ dna] (->> xs parse-fasta first)]
    (string/join #" " (rest (failure-array dna)))))

(rosalind-solve)
