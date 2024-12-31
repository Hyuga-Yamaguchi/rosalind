(ns pdst
  (:require [core :refer [rosalind-solve parse-fasta]]
            [clojure.string :as string]))

(defn calc-p-distance
  [s t]
  (double
   (/ (count (filter false? (map = s t)))
      (count s))))

(defn pdst
  [xs]
  (let [dnas (map last (parse-fasta xs))]
    (->> (map (fn [i] (map (fn [j] (calc-p-distance i j)) dnas)) dnas)
         (map #(string/join #" " %))
         (string/join "\n"))))

(rosalind-solve)
