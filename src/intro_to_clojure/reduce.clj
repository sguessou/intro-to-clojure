(ns intro-to-clojure.reduce)

;; Implementing reduce.
(defn reduce* [f i coll]
  (if (empty? coll)
    i
    (let [[fst & rst] coll]
      (recur f (f i fst) rst))))

(reduce* + 0 (range 20))

;; Implementing map using reduce.
(defn map* [f ls]
  (reduce (fn [res v]
            (conj res (f v)))
          [] ls))

(map* inc (range 5))

;; Implementing filter with reduce
(defn filter* [f ls]
  (reduce (fn [res v]
            (if (f v)
              (conj res v)
              res))
          [] ls))

(filter even? (range 10))

;; reduce-kv example
(reduce-kv (fn [[kcount vcount] k v]
             [(+ kcount (count k))
              (+ vcount (count v))])
           [0 0] {"Saad" "Guessous"
                  "Elias" "Guessous"
                  "Isak" "Guessous"
                  "Lia" "Markelin"})

(reduce-kv (fn [m k v]
             (assoc m k v))
           {} (vec "hello-world!"))

;; reduced example
(defn longer-than? [n coll]
  (> (reduce (fn [res _]
             (let [res (inc res)]
               (if (> res n)
                 (reduced res)
                 res)))
           0 coll)
     n))

(longer-than? 3 [1 3 4 4])

(defn shorter-than? [n coll]
  (not (longer-than? n coll)))

(shorter-than? 5 [1 2 3])

(def shorter-than? (complement longer-than?))

(shorter-than? 5 [1 3 4 5 6 7])
