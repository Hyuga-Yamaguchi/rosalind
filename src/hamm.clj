(ns hamm
  (:require [core :refer [rosalind-solve]]
            [clojure.string :as string]))

(defn hamm
  [xs]
  (let [[s t] (string/split-lines xs)]
    (->> (map = s t)
         (filter false?)
         count)))

(rosalind-solve)
