(ns lia
  (:require [core :refer [rosalind-solve]]
            [clojure.string :as string]))

;; AaBb - AaBb から生まれる子供の組み合わせ

;; Xx - Xx: XX, Xx, Xx, xx

;; AABB 1/4 * 1/4 = 1/16
;; AABb 1/4 * 1/2 = 1/8
;; AAbb 1/4 * 1/4 = 1/16
;; AaBB 1/8
;; AaBb 1/4 (ターゲット)
;; Aabb 1/8
;; aaBB 1/16
;; aaBb 1/8
;; aabb 1/16

(defn factorial
  [n]
  (reduce * (map bigint (range 1 (inc n)))))

(defn binomial-coefficient
  [n k]
  (/ (factorial n)
     (* (factorial k) (factorial (- n k)))))

(defn binomial-pmf
  [n k p]
  (* (binomial-coefficient n k)
     (Math/pow p k)
     (Math/pow (- 1 p) (- n k))))

(defn binomial-cdf
  [n p x]
  (reduce + (map #(binomial-pmf n % p) (range 0 (inc x)))))

(defn lia
  [xs]
  (let [[k N] (map #(Long/parseLong %) (string/split xs #"\s"))
        n (bigint (bit-shift-left 1 k))
        p 1/4]
    (- 1 (binomial-cdf n p (dec N)))))

(rosalind-solve)
