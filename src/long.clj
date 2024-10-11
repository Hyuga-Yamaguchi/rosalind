(ns long
  (:require [core :refer [rosalind-solve parse-fasta]]
            [clojure.string :as string]
            [clojure.set :refer [difference]]))

(defn overlap [s1 s2] ;; s1 の後ろに s2 がくっつく
  (let [half-len (quot (min (count s1) (count s2)) 2)]
    (or (some->> (range (inc half-len) (inc (count s2)))
                 (filter #(string/ends-with? s1 (subs s2 0 %)))
                 last)
         0)))

(defn find-start-idx
  [pairs l]
  (first (difference (set (range l))
                     (set (map second pairs)))))

(defn join-indexes
  [pairs]
  (let [dnas-num (count pairs)
        start-idx (find-start-idx pairs dnas-num)]
    (loop [cur-idx start-idx result [start-idx]]
      (if-let [next-idx (second (nth pairs cur-idx))]
        (recur next-idx (conj result next-idx))
        result))))

(defn merge-strings [s1 s2 overlap-len]
  (str s1 (subs s2 overlap-len)))

(defn long
  [xs]
  (let [dnas (->> xs parse-fasta (map last) vec)
        indexed-dnas (map-indexed vector dnas)]
    (->> indexed-dnas
         (map (fn [[idx1 dna1]]
                [idx1 (ffirst (for [[idx2 dna2] indexed-dnas
                                    :when (and (not= idx1 idx2)
                                               (>= (overlap dna1 dna2) 1))]
                                [idx2]))]))
         join-indexes
         (map dnas)
         (reduce (fn [s1 s2] (merge-strings s1 s2 (overlap s1 s2))) ""))))

(rosalind-solve)
