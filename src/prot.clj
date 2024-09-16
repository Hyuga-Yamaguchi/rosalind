(ns prot
  (:require [core :refer [rosalind-solve rna-codon-table]]))

(defn prot
  [xs]
  (->> xs
       (partition 3)
       (map #(apply str %))
       (map #(get rna-codon-table %))
       (apply str)))

(rosalind-solve)
