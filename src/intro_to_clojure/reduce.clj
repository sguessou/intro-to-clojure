(ns intro-to-clojure.reduce)

;; implementing reduce
(defn reduce* [f i coll]
  (if (empty? coll)
    i
    (let [[fst & rst] coll]
      (recur f (f i fst) rst))))

(reduce* + 0 (range 20))
