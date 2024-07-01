(ns core)

(defn read-file
  [filename]
  (slurp (str "input/" filename)))

(defn output-file
  [output filename]
  (spit (str "output/" filename) output))

(defn rosalind-solve
  [func]
  (let [file (str func ".txt")]
    (-> (read-file file)
        func
        (output-file file))))
