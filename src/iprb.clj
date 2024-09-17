(ns iprb
  (:require [core :refer [rosalind-solve]]
            [clojure.string :as string]))

(defn iprb
  [s]
  (let [[k m n] (map #(Long/parseLong %) (string/split s #"\s")) 
        all (+ k m n)]
    (- 1.0 (/ (+ (* (/ 1 4) (* m (dec m)))
                 (* m n)
                 (* n (dec n)))
              (* all (dec all))))))

(rosalind-solve)
