(ns subs
  (:require [core :refer [rosalind-solve]]
            [clojure.string :as string]))

(defn subs
  [xs]
  (let [[dna substirng] (string/split-lines xs)
        substring-length (count substirng)]
    (->> dna
        (partition substring-length 1 nil)
         (map #(apply str %))
         (map-indexed (fn [idx item] {item idx}))
         (filter #(contains? % substirng))
         (map #(get % substirng))
         (map inc)
         (string/join " "))))

(rosalind-solve)
