(ns mmch
  (:require [core :refer [rosalind-solve parse-fasta]]))

(defn factorial
  [n]
  (reduce * (map bigint (range 1 (inc n)))))

(defn max-matching
  [x y]
  (quot (factorial (max x y)) (factorial (- (max x y) (min x y)))))

(defn mmch
  [xs]
  (let [rna (->> xs parse-fasta (map last) first)
        f (frequencies rna)]
    (* (max-matching (f \A) (f \U))
       (max-matching (f \C) (f \G)))))

(rosalind-solve)
