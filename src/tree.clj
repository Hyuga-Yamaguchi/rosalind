(ns tree
  (:require [core :refer [rosalind-solve]]
            [ubergraph.core :as uberc]
            [ubergraph.alg :as ubera]
            [clojure.string :as string]))

(defn tree
  [xs]
  (let [[n & edges] (->> xs string/trim-newline string/split-lines)
        edges (map #(string/split % #" ") edges)]
    (-> (apply uberc/graph edges)
        (uberc/add-nodes* (map str (range 1 (inc (Long/parseLong n)))))
        (ubera/connected-components)
        count
        dec)))

(rosalind-solve)
