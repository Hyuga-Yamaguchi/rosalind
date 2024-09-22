(ns orf
  (:require [core :refer [rosalind-solve parse-fasta rna-codon-table]]
            [clojure.string :as string]))

(defn partition-codon
  [s start]
  (->> (drop start s)
       (partition 3)
       (map #(apply str %))))

(defn rna
  [s]
  (apply str (map {\A \A \T \U \G \G \C \C} s)))

(defn revc
  [s]
  (apply str (map {\A \T \T \A \G \C \C \G} (reverse s))))

(defn get-orf
  [codon]
  (->> (keep-indexed (fn [idx v] (when (= v "M") idx)) codon)
       (map (fn [idx]
              (take-while #(not= "" %) (drop idx codon))))
       (filter #(not= "x" (last %)))
       (mapv #(apply str %))))

(defn orf
  [xs]
  (let [fasta (->> xs parse-fasta (map last) first)
        rnas (mapcat (fn [f] (map #(-> fasta f rna (partition-codon %)) (range 3)))
              [identity revc])
        codons (mapv #(conj (mapv rna-codon-table %) "x") rnas)]
    (->> codons
         (mapcat get-orf)
         set
         (string/join "\n"))))

(rosalind-solve)
