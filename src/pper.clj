(ns pper
  (:require [core :refer [rosalind-solve]]
            [clojure.string :as string]))

(defn pper
  [xs]
  (let [[n k] (map #(Long/parseLong %) (string/split (string/trim-newline xs) #" "))
        modulo 1000000]
    (mod (reduce #(mod (* %1 %2) modulo) (range (inc (- n k)) (inc n)))
         modulo)))

(rosalind-solve)
