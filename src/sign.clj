(ns sign
  (:require [core :refer [rosalind-solve]]
            [clojure.string :as string]))

(defn permutations [s]
  (lazy-seq
   (if (next s)
     (for [head s
           tail (permutations (disj s head))]
       (cons head tail))
     [s])))

(defn signed-combination
  [coll]
  (reduce
   (fn [acc ele]
     (for [a acc e ele]
         (conj a e)))
   [[]]
   (map (fn[x] [x (- x)]) coll)))

(defn sign
  [xs]
  (let [n (Long/parseLong (string/trim-newline xs))
        signed-permutations (mapcat permutations (map set (signed-combination (range 1 (inc n)))))]
    (string/join "\n" (cons (count signed-permutations) (map #(string/join " " %) signed-permutations)))))

(rosalind-solve)
