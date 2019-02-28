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
