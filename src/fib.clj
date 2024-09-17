(ns fib
  (:require [core :refer [rosalind-solve]]
            [clojure.string :as string]))

;; f_0 = 0
;; f_1 = 1
;; f_{n} = k * f_{n - 1} + f_{n - 2}

(defn fib
  [xs]
  (let [[n k] (map #(Long/parseLong %) (string/split xs #"\s"))]
    (->> [0 1]
         (iterate (fn [[a b]] [(+ a b) (* k a)]))
         (take (inc n))
         last
         first)))

(rosalind-solve)
