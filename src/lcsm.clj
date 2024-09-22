(ns lcsm
  (:require [core :refer [rosalind-solve parse-fasta]]
            [clojure.string :as string]))

(defn common-substring
  [s len]
  (->> (range 0 (inc (- (count s) len)))
       (map #(subs s % (+ % len)))))

(defn substring-in-all?
  [substr others]
  (every? #(string/includes? % substr) others))

(defn find-common-for-length
  [first-seq others len]
  (some #(when (substring-in-all? % others) %)
        (common-substring first-seq len)))

(defn longest-common-substring
  [dna-seq]
  (let [first-seq (first dna-seq)
        others (rest dna-seq)]
    (some (fn [len] (find-common-for-length first-seq others len))
          (range (count first-seq) 0 -1))))

(defn lcsm
  [xs]
  (let [dnas (->> xs parse-fasta (map last))]
    (longest-common-substring dnas)))

(rosalind-solve)
