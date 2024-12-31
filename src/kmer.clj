(ns kmer
  (:require [core :refer [rosalind-solve parse-fasta]]
            [clojure.string :as string]))

(defn generate-kmer
  [k]
  (let [base [\A \C \G \T]]
    (reduce
     (fn [acc _] (for [prefix acc letter base] (str prefix letter)))
     base
     (range (dec k)))))

(defn overlap-re-seq
  [regex s]
  (when-let [match (re-find regex s)]
    (cons match (overlap-re-seq regex (subs s (inc (string/index-of s match)))))))

(defn kmer-composition
  [s k]
  (let [kmers (generate-kmer k)]
    (map (fn [kmr] (count (overlap-re-seq (re-pattern kmr) s))) kmers)))

(defn kmer
  [xs]
  (let [[_ dna] (first (parse-fasta xs))]
    (->> (kmer-composition dna 4)
         (string/join #" "))))

(rosalind-solve)
