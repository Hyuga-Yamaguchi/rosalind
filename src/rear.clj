(ns rear
  (:require [core :refer [rosalind-solve]]
            [clojure.string :as string]))

(defn breakpoints
  [perm]
  (count (filter not (map #(= %1 (inc %2)) perm (rest perm)))))

(defn reverse-sublist
  [lst start end]
  (concat (take start lst)
          (reverse (subvec lst start (inc end)))
          (drop (inc end) lst)))

(defn find-best-reversal
  [perm]
  (let [n (count perm)
        best-move nil
        min-breakpoints (breakpoints perm)]
    (loop [i 0
           best-move best-move]
      (if (< i n)
        (recur (inc i)
               (loop [j (inc i)
                      best-move best-move
                      min-breakpoints min-breakpoints]
                 (if (< j n)
                   (let [new-perm (reverse-sublist perm i j)
                         new-bp (breakpoints new-perm)]
                     (if (< new-bp min-breakpoints)
                       (recur (inc j) [i j] new-bp)
                       (recur (inc j) best-move min-breakpoints)))
                   best-move)))
        best-move))))

(defn reversal-distance [perm goal]
  "順列 perm を goal に変換するための最小反転回数を求める"
  (loop [perm perm
         steps 0]
    (if (= perm goal)
      steps
      (let [[i j] (find-best-reversal perm goal)]
        (recur (reverse-sublist perm i j) (inc steps))))))

(defn rear
  [xs]
  (let [perms (->> xs
                   string/split-lines
                   (filter #(not= % ""))
                   (map #(string/split % #" "))
                   (partition 2))]))
