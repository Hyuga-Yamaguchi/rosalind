(ns pmch
  (:require [core :refer [rosalind-solve parse-fasta]]))

(defn factorial
  [n]
  (reduce * (map bigint (range 1 (inc n)))))

(defn pmch
  [xs]
  (let [fasta-dna (->> xs parse-fasta (map last) first)
        counts (frequencies fasta-dna)]
    (* (factorial (counts \A))
       (factorial (counts \C)))))

(rosalind-solve)
