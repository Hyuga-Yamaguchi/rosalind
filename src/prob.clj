(ns prob
  (:require [core :refer [rosalind-solve]]
            [clojure.string :as string]))

;; GC含有量がpであるとき、
;; Aの存在頻度: (1 - p)/2
;; Tの存在頻度: (1 - p)/2
;; Gの発生頻度: p/2
;; Cの発生頻度: p/2
;; 求める答えは、A, T, G, Cの個数をa, t, g, cと置くと、
;; {(1 - p)/2} ** a * {(1 - p)/2} ** t * {p/2} ** g * {p/2} ** c

(defn prob
  [xs]
  (let [[dna & probs] (->> xs string/trim-newline string/split-lines)
        probs (map #(Double/parseDouble %) (string/split (first probs) #" "))
        freq (frequencies dna)]
    (->> probs
         (map (fn [p]
                (Math/log10
                 (reduce * (map (fn [[base freq-count]]
                                  (Math/pow (/ (if (#{\A \T} base) (- 1 p) p) 2) freq-count))
                                freq)))))
         (string/join " "))))

(rosalind-solve)
