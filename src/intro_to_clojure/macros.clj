(ns intro-to-clojure.macros)

;; Macro 1
(defmacro square [x]
  `(let [x# ~x]
    (* x# x#)))

(square (rand-int 10))

(macroexpand-1 `(square 10))
