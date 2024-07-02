(ns rna
  (:require [core :refer [rosalind-solve]]))

(defn rna
  [s]
  (->> s
      (map {\A \A \T \U \G \G \C \C})
      (apply str)))

(rosalind-solve)
