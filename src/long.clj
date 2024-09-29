(ns long
  (:require [core :refer [rosalind-solve parse-fasta]]
            [clojure.string :as string]
            [clojure.set :as cset]))

;; リードのオーバーラップを計算する
;; オーバーラップの最大を持つペアを結合し、繰り返す
;; 全てのリードが1つのsuperstringに結合されるまで続ける]

(defn prefix-list
  [s]
  (map #(subs s 0 %) (range 1 (inc (count s)))))

(defn suffix-list
  [s]
  (map #(subs s % (count s)) (range 0 (count s))))

(defn join-string
  [s t overlap-cache]
  (if-let [cached (get overlap-cache [s t])]
    cached
    (let [substrings (cset/intersection (set (prefix-list s)) (set (suffix-list t)))]
      (if (seq substrings)
        (let [long-string (apply max-key count substrings)
              result (str t (subs s (count long-string)))]
          [result (assoc overlap-cache [s t] result)])
        [(str t s) (assoc overlap-cache [s t] (str t s))]))))

(defn overlap-string
  [s t overlap-cache]
  (if (string/includes? t s)
    [t overlap-cache]
    (let [[res1 cache1] (join-string s t overlap-cache)
          [res2 cache2] (join-string t s cache1)]
      [(min-key count res1 res2) cache2])))

(defn min-superstring
  [coll overlap-cache]
  (reduce
   (fn [[best-s1 best-s2 best-os cache] [s1 s2]]
     (let [[overlap cache'] (overlap-string s1 s2 cache)]
       (if (< (count overlap) (count best-os))
         [s1 s2 overlap cache']
         [best-s1 best-s2 best-os cache])))
   [(first coll) (second coll) (overlap-string (first coll) (second coll) overlap-cache) overlap-cache]
   (for [i (range (count coll))
         j (range (inc i) (count coll))]
     [(nth coll i) (nth coll j)])))

(defn super-string
  [coll]
  (if (= 1 (count coll))
    (first coll)
    (let [[[s1 s2 os] overlap-cache] (min-superstring coll {})]
      (super-string (conj (remove (hash-set s1 s2) coll) os)))))

(defn long
  [xs]
  (let [dnas (->> xs parse-fasta (map #(last %)))]
    (super-string dnas)))

(rosalind-solve)
