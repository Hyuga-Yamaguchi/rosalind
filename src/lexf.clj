(ns lexf
  (:require [core :refer [rosalind-solve]]
            [clojure.string :as string]))

(defn lexf
  [xs]
  (let [s (->> xs string/split-lines)
        alphabet (sort (string/split (first s) #" "))
        n (Long/parseLong (second s))]
    (letfn [(lexi [cnt]
                  (if (= cnt 0)
                    alphabet
                    (for [prefix (lexi (dec cnt))
                          letter alphabet]
                      (str prefix letter))))]
      (string/join "\n" (lexi (dec n))))))

(rosalind-solve)
