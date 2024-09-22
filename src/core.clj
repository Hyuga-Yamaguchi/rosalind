(ns core
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

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

(defn split-by-char
  [xs char]
  (->> (string/split xs (re-pattern (str "\\" char)))
       (filter seq)))

(defn trim-space
  [xs]
  (string/replace xs #"[ \t]+" ""))

(defn parse-fasta
  [fasta-sting]
  (->> (split-by-char fasta-sting \>)
       (map (fn [entry]
              (let [[label & seq-lines] (string/split-lines (trim-space entry))]
                [label (apply str seq-lines)])))))

(def rna-codon-table
  {"UUU" "F", "UUC" "F", "UUA" "L", "UUG" "L",
   "CUU" "L", "CUC" "L", "CUA" "L", "CUG" "L",
   "AUU" "I", "AUC" "I", "AUA" "I", "AUG" "M",
   "GUU" "V", "GUC" "V", "GUA" "V", "GUG" "V",
   "UCU" "S", "UCC" "S", "UCA" "S", "UCG" "S",
   "CCU" "P", "CCC" "P", "CCA" "P", "CCG" "P",
   "ACU" "T", "ACC" "T", "ACA" "T", "ACG" "T",
   "GCU" "A", "GCC" "A", "GCA" "A", "GCG" "A",
   "UAU" "Y", "UAC" "Y", "UAA" "", "UAG" "",
   "CAU" "H", "CAC" "H", "CAA" "Q", "CAG" "Q",
   "AAU" "N", "AAC" "N", "AAA" "K", "AAG" "K",
   "GAU" "D", "GAC" "D", "GAA" "E", "GAG" "E",
   "UGU" "C", "UGC" "C", "UGA" "", "UGG" "W",
   "CGU" "R", "CGC" "R", "CGA" "R", "CGG" "R",
   "AGU" "S", "AGC" "S", "AGA" "R", "AGG" "R",
   "GGU" "G", "GGC" "G", "GGA" "G", "GGG" "G"})

(def monoisotopi-mass-table
  {"A" 71.03711
   "C" 103.00919
   "D" 115.02694
   "E" 129.04259
   "F" 147.06841
   "G" 57.02146
   "H" 137.05891
   "I" 113.08406
   "K" 128.09496
   "L" 113.08406
   "M" 131.04049
   "N" 114.04293
   "P" 97.05276
   "Q" 128.05858
   "R" 156.10111
   "S" 87.03203
   "T" 101.04768
   "V" 99.06841
   "W" 186.07931
   "Y" 163.06333})
