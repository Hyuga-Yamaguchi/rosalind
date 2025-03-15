(ns lexv
  (:require [clojure.string :as string]
            [core :refer [rosalind-solve]]))

(defn lexv
  [xs]
  (let [[a n] (string/split-lines xs)
        a (string/split a #" ")
        n (Integer/parseInt n)]
    (letfn [(dfs [prefix]
              (cons prefix
                    (when (< (count prefix) n)
                      (mapcat #(dfs (str prefix %)) a))))]
      (->> (rest (dfs ""))
           (string/join "\n")))))

(rosalind-solve)
