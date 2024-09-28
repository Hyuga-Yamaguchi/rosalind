(ns sseq
  (:require [core :refer [rosalind-solve parse-fasta]]
            [clojure.string :as string]))

(defn sseq
  [xs]
  (let [[s t] (->> xs parse-fasta (map last) (map seq))]
    (letfn [(subseq [s t coll]
                    (if (empty? t)
                      coll
                      (let [[head & tail] (drop-while #(not= (first t) (second %)) s)]
                        (recur tail
                               (rest t)
                               (conj coll (first head))))))]
      (string/join " " (subseq (map-indexed (fn [idx item] [(inc idx) item]) s) t [])))))

(rosalind-solve)
