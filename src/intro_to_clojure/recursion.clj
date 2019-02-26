(ns intro-to-clojure.recursion)

;; Recursion 101

;; Example 1: length
(def ls0 [])
(def ls1 [1])
(def ls10 (range 10))

(defn length [ls]
  (if (empty? ls)
    0
    (+ 1 (length (rest ls)))))

(length nil)
(length ls0)
(length ls1)
(length ls10)

;; Example 2: map
(defn map* [f ls]
  (if (empty? ls)
    ()
    (cons (f (first ls)) (map* f (rest ls)))))

(map* inc [])
(map* inc ls10)
(map* str ls10)

;; Example 3: filter
(defn filter* [p? ls]
  (if (empty? ls)
    ()
    (if (p? (first ls))
      (cons (first ls) (filter* p? (rest ls)))
      (filter* p? (rest ls)))))

(filter* even? (range 50))
(filter* nil? (range 10))
(filter* (complement nil?) (range 10))

;; Example 3: recursion types (tail recursion & non tail recursion)
(defn filter* [p? ls]
  (if (empty? ls)
    ()
    (if (p? (first ls))
      (cons (first ls) (filter* p? (rest ls)))
      (recur p? (rest ls)))))

;; Example 4: filter with tail recursion
(defn filter*-helper [p? ls acc]
  (if (empty? ls)
    acc
    (if (p? (first ls))
      (recur p? (rest ls) (conj acc (first ls)))
      (recur p? (rest ls) acc))))

(defn filter* [p? ls]
  (filter*-helper p? ls []))

(filter* even? (range 10))

;; Example 5: map with tail recursion
(defn map*-helper [f ls acc]
  (if (empty? ls)
    acc
    (recur f (rest ls) (conj acc (f (first ls))))))

(defn map* [f ls]
  (map*-helper f ls []))

(map* inc (range 20))
