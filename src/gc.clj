(ns gc
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

(defn calc-gc-rate
  [dna]
  (double
   (/ (->> dna (filter #{\G \C}) count)
      (->> dna count))))

(defn gc
  [xs]
  (let [fasta (parse-fasta xs)
        gc-rates (into {} (map (fn [[k v]] {k (calc-gc-rate v)}) fasta))
        max-gc-rate (apply max-key val gc-rates)
        id (-> max-gc-rate first name)
        gc-rate (* 100 (last max-gc-rate))]
    (str id "\n" gc-rate)))

(rosalind-solve)
