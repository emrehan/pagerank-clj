(ns pagerank.matrix_generator
  (:use [clojure.core.matrix]
        [clojure.java.io :only [reader]]
        [clojure.string :only [split]]
        [local-file :only [file*]]))

; core.matrix settings
(set-current-implementation :vectorz)

; generates a sparse matrix of n to n
; n is 875713 in the given file
(defn generate-matrix [n]
  (new-sparse-array [n n]))

; returns a reader of the edges data
(defn get-reader []
  (reader (file* "resources/web-Google.txt")))

; returns lines of the edge declerations
(defn get-lines []
  (map #(split % #"\s+") (drop 4 (line-seq (get-reader)))))

; populates a sparse matrix using an reader of edge to edge
(defn populate-matrix [m]
  (doall (for [line (get-lines)] 
    (mset! m (Integer/parseInt (first line)) (Integer/parseInt (second line)) 1))))

; return a matrix populated by the given edges in the file
; 1000000 is more than the biggest node id in the given data
; sparse-matrix is mutable
(defn get-matrix []
	(let [m (generate-matrix 1000000)]
    (populate-matrix m) m))