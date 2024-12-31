(ns rstr
  (:require [core :refer [rosalind-solve]]
            [clojure.string :as string]))

;; 1. sと一致するランダムDNA文字列が生成される確率p
;; GC含有率をxとして、GまたはC: p = x/2, AまたはT: p = (1-x)/2
;;
;; 2. N個のランダム文字列が生成されたとき、少なくとも1つがsと一致する確率Pは、
;; P = 1 - (1 - p) ** N

(defn nucleotide-prob
  [nucleotide x]
  (case nucleotide
    \A (/ (- 1 x) 2)
    \T (/ (- 1 x) 2)
    \G (/ x 2)
    \C (/ x 2)))

(defn sequence-prob
  [s x]
  (reduce * (map #(nucleotide-prob % x) s)))

(defn rstr
  [xs]
  (let [[n x s] (->> (string/split-lines xs)
                     (mapcat #(string/split % #" ")))
        [n x] [(Integer/parseInt n) (Double/parseDouble x)]
        p (sequence-prob s x)]
    (- 1 (Math/pow (- 1 p) n))))

(rosalind-solve)
