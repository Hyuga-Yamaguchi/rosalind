(ns gc
  (:require [core :refer [rosalind-solve parse-fasta]]))

(defn calc-gc-rate
  [dna]
  (double
   (/ (->> dna (filter #{\G \C}) count)
      (->> dna count))))

(defn gc
  [xs]
  (let [fasta (parse-fasta xs)
        gc-rates (into {} (map (fn [[k v]] {k (calc-gc-rate v)}) fasta))
        max-gc-rate (apply max-key val gc-rates)
        id (-> max-gc-rate first name)
        gc-rate (* 100 (last max-gc-rate))]
    (str id "\n" gc-rate)))

(rosalind-solve)
