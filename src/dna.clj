(ns dna
  (:require [core :refer :all]))

(defn nucleotide-count
  [freq nucleotide]
  (or (get freq nucleotide) 0))

(defn dna
  [s]
  (let [freq (frequencies s)]
    (format "%d %d %d %d"
            (nucleotide-count freq \A)
            (nucleotide-count freq \C)
            (nucleotide-count freq \G)
            (nucleotide-count freq \T))))

(-> (read-file "dna.txt")
    dna
    (output-file "dna.txt"))
