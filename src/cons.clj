(ns cons
  (:require [core :refer [rosalind-solve parse-fasta]]
            [clojure.string :as string]))

(defn cons
  [xs]
  (let [s (parse-fasta xs)
        freq (->> s
                  (map last)
                  (apply map vector)
                  (map frequencies))
        consensus (->> freq
                       (map #(-> (apply max-key val %) key))
                       (apply str))
        output (fn [nuc]
                 (str nuc ": " (string/join " " (map #(get % nuc 0) freq))))]
    (string/join "\n" (clojure.core/cons consensus (map output [\A \C \G \T])))))

(rosalind-solve)
