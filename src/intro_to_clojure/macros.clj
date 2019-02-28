(ns intro-to-clojure.macros)

;; Macro 1
(defmacro square [x]
  `(let [x# ~x]
    (* x# x#)))

(square (rand-int 10))

(macroexpand-1 `(square 10))

;; Expanding a macro
(defmacro square [x]
  `(let [x# ~x]
     (when (number? x#)
       (* x# x#))))

(macroexpand-1 `(square (rand-int 10)))
(macroexpand `(square (rand-int 10)))
(clojure.walk/macroexpand-all `(square (rand-int 10)))

;; Building a when macro
;; (when* (even? x) (println "It's even!")) => (if (even? x) (println "It's even!") nil)
;; (when* (even? x) (println "It's even!") x) => (if (even? x) (do (println "It's even!") x) nil)
(defmacro when* [test & body]
  `(if ~test
     (do
       ~@body)
     nil))

(macroexpand `(when* (even? x) (println "It's even!") x))

;; Building a while macro
;; (while* (> @x 0) (swap! x dec)) => (loop [] (when (> @x 0) (swap! x dec) (recur)))
(defmacro while* [test & body]
  `(loop []
     (when ~test
       ~@body
       (recur))))

(def x (atom 10))

(while* (> @x 0)
  (println @x)
  (swap! x dec))

@x
(clojure.walk/macroexpand-all `(while* (> @x 0) (println @x) (swap! x dec)))
