(ns fibd
  (:require [core :refer [rosalind-solve]]
            [clojure.string :as string]))

(defn fibd
  [xs]
  (let [[n m] (map #(Integer/parseInt %) (-> xs string/trim-newline (string/split #" ")))]
    (->> (cons 1N (repeat (dec m) 0N))
         (iterate #(cons (reduce + (rest %)) (butlast %)))
         (take n)
         last
         (reduce +))))

(rosalind-solve)
