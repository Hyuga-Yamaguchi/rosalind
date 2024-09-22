(ns grph
  (:require [core :refer [rosalind-solve parse-fasta]]
            [clojure.string :as string]))

(defn overlap?
  [s t]
  (= (drop (- (count s) 3) s) (take 3 t)))

(defn grph
  [xs]
  (let [fasta (parse-fasta xs)]
    (->> (for [[id1 dna1] fasta
               [id2 dna2] fasta
               :when (overlap? dna1 dna2)]
           [(name id1) (name id2)])
         (remove #(= (first %) (second %)))
         (map #(string/join " " [(first %) (last %)]))
         (string/join "\n"))))

(rosalind-solve)
