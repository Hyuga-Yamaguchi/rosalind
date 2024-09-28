(ns tran
  (:require [core :refer [rosalind-solve parse-fasta]]))

;; transition
;; A <-> G, C <-> T
;; tranversion
;; A <-> C, A <-> T, G <-> C, G <-> T

(defn tran
  [xs] 
  (let [[s t] (->> xs parse-fasta (map last))
        pairs (map vector s t)
        is-transition? (fn [x y]
                         (or (and (= x \A) (= y \G))
                             (and (= x \G) (= y \A))
                             (and (= x \C) (= y \T))
                             (and (= x \T) (= y \C))))
        is-tranversion? (fn [x y]
                          (or (and (= x \A) (or (= y \C) (= y \T)))
                              (and (= x \G) (or (= y \C) (= y \T)))
                              (and (= x \C) (or (= y \A) (= y \G)))
                              (and (= x \T) (or (= y \A) (= y \G)))))]
    (double
     (/ (count (filter #(is-transition? (first %) (second %)) pairs))
        (count (filter #(is-tranversion? (first %) (second %)) pairs))))))

(rosalind-solve)
