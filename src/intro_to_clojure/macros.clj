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

