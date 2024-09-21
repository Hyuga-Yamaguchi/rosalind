(ns cons
  (:require [core :refer [rosalind-solve parse-fasta]]
            [clojure.string :as string]))

(defn cons
  [xs]
  (let [s (->> xs parse-fasta)
        freq (->> s
                  vals
                  (map #(partition 1 %))
                  (apply map vector)
                  (map flatten)
                  (map frequencies))
        consensus (->> freq
                       (map #(key (apply max-key val %)))
                       (apply str))
        output (fn [nuc] (str nuc ": " (string/join " " (map #(get % nuc 0) freq))))]
    (string/join "\n" (clojure.core/cons consensus (map output [\A \C \G \T])))))

(rosalind-solve)
