(ns splc
  (:require [core :refer [rosalind-solve parse-fasta rna-codon-table]]
            [clojure.string :as string]))

(defn rna
  [s]
  (apply str (map {\A \A \T \U \G \G \C \C} s)))

(defn splc
  [xs]
  (let [fasta (parse-fasta xs)
        [target-dna & intron] (map last fasta)
        exons (reduce #(string/replace %1 %2 "") target-dna intron)]
    (->> exons
         (partition 3)
         (map #(->> % (apply str) rna (get rna-codon-table)))
         (apply str))))

(rosalind-solve)
