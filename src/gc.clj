(ns gc
  (:require [core :refer [rosalind-solve parse-fasta]]))

(defn calc-gc-rate
  [dna]
  (let [gc-count (count (filter #{\G \C} dna))
        total-count (count dna)]
    (/ gc-count total-count)))

(defn gc
  [xs]
  (let [fasta (parse-fasta xs)
        gc-rates (map (fn [[id seq]] [id (calc-gc-rate seq)]) fasta)
        [id max-rate] (apply max-key second gc-rates)]
    (str (name id) "\n" (* 100.0 max-rate))))

(rosalind-solve)
