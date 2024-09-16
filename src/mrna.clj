(ns mrna
  (:require [core :refer [rosalind-solve rna-codon-table]]
            [clojure.string :as string]))

(def codon-freq
  (frequencies (vals rna-codon-table)))

(def modulo 1000000)

(defn mrna
  [xs]
  (->> xs
       string/trim-newline
       (map #(get codon-freq (str %)))
       (map #(mod % modulo))
       (reduce (fn [acc x] (mod (* acc x) modulo)) (get codon-freq ""))))

(rosalind-solve)
