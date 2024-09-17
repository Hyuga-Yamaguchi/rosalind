(ns prtm
  (:require [core :refer [rosalind-solve monoisotopi-mass-table]]
            [clojure.string :as string]))

(defn prtm
  [xs]
  (->> xs
       string/trim-newline
       (map #(get monoisotopi-mass-table (str %)))
       (reduce +)))

(rosalind-solve)
