(ns core
  (:require [clojure.java.io :as io]))

(defn read-file
  [filename]
  (slurp (str "input/" filename)))

(defn output-file
  [output filename]
  (spit (str "output/" filename) output))

(defn rosalind-solve
  []
  (let [download_path (str (System/getProperty "user.home") "/Downloads")
        file (str *ns* ".txt")
        rosalind_file (str download_path "/rosalind_" file)]
    (io/copy (io/file rosalind_file) (io/file (str "input/" file)))
    (-> (read-file file)
        ((ns-resolve *ns* (symbol (str *ns*))))
        (output-file file))))
