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
                  (str (second f) (second m))]]
    (double (/ (count (remove #{"aa"} children)) 2))))

(def expected-dominant
  (into {} (for [g genotype-list]
             {g (calc-expected-dominant (partition 2 g))})))

(defn iev
  [xs]
  (let [genotype (zipmap genotype-list (map #(Long/parseLong %) (string/split (string/trim-newline xs) #" ")))]
    (->> genotype
         (map (fn [[k v]] (* v (get expected-dominant k))))
         (reduce +))))

(rosalind-solve)
