(ns intro-to-clojure.combinators)

;; identity
(defn identity [x]
  x)

;; constantly
(def always5 (constantly 5))

(always5 690)

(defn constantly* [x]
  (fn [& _]
    x))

((constantly* 100))
(map (constantly "hello") (range 20))

;; complement
(def non-pos? (complement pos?))
(non-pos? 0)

(def non-neg? (complement neg?))
(non-neg? 10) 

(defn complement* [f]
  (fn [& args]
    (not (apply f args))))

;; partial
(def add3 (partial + 3))

(add3 10 10)

(def one-over (partial / 1))
(one-over 2)

(defn partial* [f & xs]
  (fn [& ys]
    (apply f (concat xs ys))))

