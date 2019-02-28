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

