(ns inod
  (:require [core :refer [rosalind-solve]]
            [clojure.string :as string]))

(defn inod
  [xs]
  (- (Long/parseLong (string/trim-newline xs)) 2))

(rosalind-solve)
