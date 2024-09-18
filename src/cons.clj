(ns cons
  (:require [core :refer [rosalind-solve]]
            [clojure.string :as string]))

(defn split-by-char
  [xs char]
  (let [parts (string/split xs (re-pattern (str "\\" char)))]
    (filter (complement empty?) parts)))

(defn parse-fasta
  [fasta-sting]
  (let [entries (split-by-char fasta-sting \>)
        process-entry (fn [entry]
                        (let [lines (string/split-lines entry)
                              label (keyword (first lines))
                              sequence (apply str (rest lines))]
                          {label sequence}))]
    (apply merge (map process-entry entries))))

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
