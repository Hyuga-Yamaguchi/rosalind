(ns perm
  (:require [core :refer [rosalind-solve]]
            [clojure.string :as string]))

(defn permutations [s]
  (lazy-seq
   (if (next s)
     (for [head s
           tail (permutations (disj s head))]
       (cons head tail))
     [s])))

(defn perm
  [xs]
  (let [origin-set (set (range 1 (inc (Long/parseLong (string/trim-newline xs)))))
        perms (permutations origin-set)]
    (string/join "\n" (cons
                       (count perms)
                       (map #(string/join " " %) perms)))))

(rosalind-solve)
