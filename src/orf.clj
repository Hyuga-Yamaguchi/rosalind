(ns orf
  (:require [core :refer [rosalind-solve parse-fasta rna-codon-table]]
            [clojure.string :as string]))

(defn partition-codon
  [s start]
  (->> s
       (drop start)
       (partition 3)
       (map #(apply str %))))

(defn rna
  [s]
  (->> s
       (map {\A \A \T \U \G \G \C \C})
       (apply str)))

(defn revc
  [s]
  (->> s
       reverse
       (map {\A \T \T \A \G \C \C \G})
       (apply str)))

(defn get-orf
  [codon]
  (let [indices (keep-indexed (fn [idx v] (when (= v "M") idx)) codon)]
    (->> indices
         (map (fn [idx] (take-while #(not= "" %) (drop idx codon))))
         (filter #(not= "x" (last %)))
         (mapv #(apply str %)))))

(defn orf
  [xs]
  (let [s (->> xs parse-fasta vals first)
        rnas (mapcat
              (fn [f]
                (map #(-> s f rna (partition-codon %)) (range 3)))
              [identity revc])
        codons (map
                (fn [rs]
                  (conj (mapv #(get rna-codon-table %) rs) "x"))
                rnas)]
    (string/join "\n" (->> codons
                           (mapcat get-orf)
                           set))))

(rosalind-solve)
