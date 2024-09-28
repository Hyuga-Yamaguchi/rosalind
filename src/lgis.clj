(ns lgis
  (:require [core :refer [rosalind-solve]]
            [clojure.string :as string]))

(defn bisect
  "`cmp` is comparison function that defines the order (e.g., < for LIS, > for LDS)"
  [coll x cmp]
  (loop [low 0
         high (count coll)]
    (if (>= low high)
      low
      (let [mid (quot (+ low high) 2)]
        (if (cmp (nth coll mid) x)
          (recur low mid)
          (recur (inc mid) high))))))

(defn longest-subseq
  [coll cmp]
  (let [n (count coll)]
    (loop [i 0
           L []
           prev (vec (repeat n -1))
           index-in-subseq []]
      (if (< i n)
        (let [a (nth coll i)
              pos (bisect L a cmp)
              L-update (if (< pos (count L))
                          (assoc L pos a)
                          (conj L a))
              index-in-list-update (if (< pos (count index-in-subseq))
                                     (assoc index-in-subseq pos i)
                                     (conj index-in-subseq i))
              prev-update (if (> pos 0)
                            (assoc prev i (nth index-in-subseq (dec pos)))
                            prev)]
          (recur (inc i) L-update prev-update index-in-list-update))
        [L prev (last index-in-subseq)]))))

(defn reconstruct-subseq
  [coll prev last-index]
  (loop [index last-index
         subseq []]
    (if (= index -1)
      subseq
      (recur (nth prev index) (conj subseq (nth coll index))))))

(defn lis
  [coll]
  (let [[_ prev last-index] (longest-subseq coll <)]
    (reverse (reconstruct-subseq coll prev last-index))))

(defn lds
  [coll]
  (let [[_ prev last-index] (longest-subseq coll >)]
    (reverse (reconstruct-subseq coll prev last-index))))

(defn lgis
  [xs]
  (let [s (->> xs string/split-lines)
        pi (map #(Long/parseLong %) (string/split (second s) #" "))]
    (string/join "\n"
                 (map #(string/join " " %) [(lds pi) (lis pi)]))))

(rosalind-solve)
