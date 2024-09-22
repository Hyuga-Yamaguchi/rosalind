(ns mprt
  (:require [core :refer [rosalind-solve]]
            [clj-http.client :as client]
            [clojure.string :as string]))

(defn get-fasta
  [id]
  (let [id (-> id (string/split #"_") first)
        url (str "http://www.uniprot.org/uniprot/" id ".fasta")]
    (->> (client/get url)
         :body
         string/split-lines
         rest
         (apply str))))

(defn n-gly?
  [coll]
  (and (= \N (first coll))
       (not= \P (second coll))
       (contains? #{\S \T} (nth coll 2))
       (not= \P (last coll))))

(defn mprt
  [xs]
  (let [ids (->> xs string/split-lines)]
    (->> ids
         (into {} (map #(hash-map % (get-fasta %))))
         (reduce-kv (fn [m k v]
                      (assoc m k
                             (->> v
                                  (partition 4 1 nil)
                                  (map-indexed hash-map)
                                  (apply merge))))
                    {})
         (reduce-kv (fn [m k v]
                      (assoc m k
                             (->> v
                                  (filter (fn [[_ item]] (n-gly? item)))
                                  (map first)
                                  sort
                                  (map inc))))
                    {})
         (map (fn [[id idxs]]
                (when (not-empty idxs)
                  (string/join "\n" [id (string/join " " idxs)]))))
         (remove nil?)
         (string/join "\n"))))

(rosalind-solve)
