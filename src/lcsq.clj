(ns lcsq
  (:require [core :refer [rosalind-solve parse-fasta]]))

(defn lcs
  [s t]
  (let [m (count s)
        n (count t)
        dp (make-array Long (inc m) (inc n))
        ptr (make-array Object (inc m) (inc n))]
    (doseq [i (range (inc m))
            j (range (inc n))]
      (aset dp i j 0)
      (aset ptr i j nil))
    (doseq [i (range 1 (inc m))
            j (range 1 (inc n))]
      (if (= (nth s (dec i)) (nth t (dec j)))
        (do
          (aset dp i j (inc (aget dp (dec i) (dec j))))
          (aset ptr i j :diag))
        (let [up (aget dp (dec i) j)
              left (aget dp i (dec j))]
          (if (>= up left)
            (do
              (aset dp i j up)
              (aset ptr i j :up))
            (do
              (aset dp i j left)
              (aset ptr i j :left))))))
    (loop [i m j n result []]
      (if (or (zero? i) (zero? j))
        (apply str (reverse result))
        (case (aget ptr i j)
          :diag (recur (dec i) (dec j) (conj result (nth s (dec i))))
          :up (recur (dec i) j result)
          :left (recur i (dec j) result))))))

(defn lcsq
  [xs]
  (let [[s t] (->> xs parse-fasta (map last))]
    (lcs s t)))

(rosalind-solve)
