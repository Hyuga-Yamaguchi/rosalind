(ns sset 
  (:require [clojure.string :as string]
            [core :refer [rosalind-solve]]))

(def modulo 1000000)

(defn sset
  [xs]
  (let [n (-> xs string/trim-newline Long/parseLong)]
    (reduce (fn [acc _] (mod (* acc 2) modulo)) 1 (repeat n 2))))

(rosalind-solve)
