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

;; fnil
(def inc-default0 (fnil inc 0))
(inc-default0 nil)

(defn inc-default0 [x]
  (if (nil? x)
    (inc 0)
    (inc x)))

(defn fnil* [f default]
  (fn [x & xs]
    (if (nil? x)
      (apply f default xs)
      (apply f x xs))))

(def words (clojure.string/split "Some of these words are repeated. Some are not." #"\W"))
(reduce (fn [counts word]
          (update counts word (fnil inc 0)))
        {}
        words)

;; comp -- function composition
(def person {:name "Luke Skywalker"
             :address {:street "22 Skywalker Way"
                       :planer "Tatouine"
                       :postal-code "01600T"}})

(defn postal-code [person]
  (:postal-code (:address person)))
(postal-code person)
((comp :postal-code :address) person)
(map (comp :postal-code :address) [person])
