(ns grph
  (:require [core :refer [rosalind-solve parse-fasta]]
            [clojure.string :as string]))

(defn overlap?
  [s t]
  (and (not= s t)
       (= (drop (- (count s) 3) s) (take 3 t))))

(defn grph
  [xs]
  (let [fasta-map (->> xs parse-fasta)]
    (->> (for [[id1 dna1] fasta-map
               [id2 dna2] fasta-map
               :when (and (not= id1 id2)
                          (overlap? dna1 dna2))]
           [(name id1) (name id2)])
         (map #(string/join " " [(first %) (last %)]))
         (string/join "\n"))))

(rosalind-solve)
