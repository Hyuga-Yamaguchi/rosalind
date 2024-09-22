(ns revp
  (:require [core :refer [rosalind-solve parse-fasta]]
            [clojure.string :as string]))

(defn revc
  [s]
  (apply str (reverse (map {\A \T \T \A \G \C \C \G} s))))

(defn revp
  [xs]
  (let [fasta-dna (->> xs parse-fasta (map last) first)
        len-dna (count fasta-dna)]
    (->> (for [i (range len-dna)
               j (range (+ i 4) (min (+ i 13) (inc len-dna)))
               :let [substring (subs fasta-dna i j)]
               :when (= substring (revc substring))]
           [(inc i) (- j i)])
         (map #(str (first %) " " (second %)))
         (string/join "\n"))))

(rosalind-solve)
