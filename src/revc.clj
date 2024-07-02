(ns revc
  (:require [core :refer [rosalind-solve]]))

(defn revc
  [s]
  (->> s
       reverse
       (map {\A \T \T \A \G \C \C \G})
       (apply str)))

(rosalind-solve)
