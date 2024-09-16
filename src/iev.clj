(ns iev
  (:require [core :refer [rosalind-solve]]
            [clojure.string :as string]))

(def genotype-list
  ["AAAA" "AAAa" "AAaa" "AaAa" "Aaaa" "aaaa"])

(defn calc-expected-dominant
  [[f m]]
  (let [children [(str (first f) (first m))
                  (str (first f) (second m))
                  (str (second f) (first m))
                  (str (second f) (second m))]
        recessive "aa"]
    {(str f m) (/ (count (filter #(not= recessive %) children)) 2)}))

(def expected-dominant
  (->> genotype-list
       (map #(partition 2 %))
       (map (fn [pair] (map #(apply str %) pair)))
       (map calc-expected-dominant)
       (into {})))

(defn iev
  [xs]
  (let [genotype (zipmap genotype-list (map #(Integer/parseInt %) (string/split (string/trim-newline xs) #" ")))]
    (->> genotype
         (map (fn [[k v]] (* v (get expected-dominant k))))
         (reduce +)
         double)))

(rosalind-solve)
