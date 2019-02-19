(ns intro-to-clojure.core
  (:require [bakery.core :refer :all]))

(defn error [& rs]
  (apply println rs)
  :error)

;; Exercise 2
(defn add-flour []
  (grab :cup)
  (scoop :flour)
  (add-to-bowl)
  (release))

(defn add-milk []
  (grab :cup)
  (scoop :milk)
  (add-to-bowl)
  (release))

(defn add-sugar []
  (grab :cup)
  (scoop :sugar)
  (add-to-bowl)
  (release))

(defn add-butter []
  (grab :butter)
  (add-to-bowl))

(defn add-egg []
  (grab :egg)
  (squeeze)
  (add-to-bowl))

;; Exercise 3
;; Write a function bake-cake which uses the new add- functions.
(defn bake-cake []
  (add-flour)
  (add-flour)
  (add-egg)
  (add-egg)
  (add-milk)
  (add-sugar)
  (mix)
  (pour-into-pan)
  (bake-pan 25)
  (cool-pan))

(defn add [ingredient]
  (cond
    (= ingredient :egg)
    (add-egg)
    (= ingredient :milk)
    (add-milk)
    (= ingredient :flour)
    (add-milk)
    (= ingredient :sugar)
    (add-sugar)
    (= ingredient :butter)
    (add-butter)
    :else
    (println "Unknown ingredient:" ingredient)))

;; Exercise 4
;; Rewrite bake-cake using the new add function.
(defn bake-cake []
  (add :flour)
  (add :flour)
  (add :egg)
  (add :egg)
  (add :milk)
  (add :sugar)
  (mix)
  (pour-into-pan)
  (bake-pan 25)
  (cool-pan))

;; Exercise 5
;; Write a function scooped? which returns true if the given ingredient (the argument) needs scooping.
;; Otherwise it returns false.
(defn scooped? [ingredient]
  (cond 
    (or (= ingredient :flour) (= ingredient :sugar) (= ingredient :milk) (= ingredient :cocoa))
    true
    :else
    false))

;; Exercise 6
;; Write a function squeezed? which returns true if the given ingredient (the argument) needs squeezing.
;; Otherwise it returns false.
(defn squeezed? [ingredient] 
  (= ingredient :egg))

;; Exercise 7
;; Write a function simple? which returns true if the given ingredient (the argument) can be added without scooping or squeezing (basically butter).
;; Otherwise it returns false.
(defn simple? [ingredient]
  (= ingredient :butter))

;; Exercise 8
;; Write functions add-scooped, add-squeezed, and add-simple which conditionally add the respective ingredient types.
;; You will need to use if and do.
(defn add-scooped [ingredient]
  (if (scooped? ingredient)
    (do
      (grab :cup)
      (scoop ingredient)
      (add-to-bowl)
      (release))
    (error "This function only works on scooped ingredients. You asked me to scoop" ingredient)))

(defn add-squeezed [ingredient]
  (if (squeezed? ingredient)
    (do
      (grab ingredient)
      (squeeze)
      (add-to-bowl))
    (error "This function only works on squeezed ingredients. You asked me to squeeze" ingredient)))

(defn add-simple [ingredient]
  (if (simple? ingredient)
    (do
      (grab ingredient)
      (add-to-bowl))
    (error "This function only works on simple ingredients. You asked me to add" ingredient)))

(defn add [ingredient]
  (cond
    (squeezed? ingredient)
    (add-squeezed ingredient)
    (scooped? ingredient)
    (add-scooped ingredient)
    (simple? ingredient)
    (add-simple ingredient)
    :else
    (do
      (println "I do not know the ingredient" ingredient))))

(defn add-eggs [n]
  (dotimes [e n]
    (add-egg))
  :ok)

;; Exercise 9
;; Write the following functions using dotimes.
;; add-flour-cups
;; add-sugar-cups
;; add-milk-cups
;; add-butters
(defn add-flour-cups [n]
  (dotimes [e n]
    (add-flour))
  :ok)

(defn add-sugar-cups [n]
  (dotimes [e n]
    (add-sugar))
  :ok)

(defn add-milk-cups [n]
  (dotimes [e n]
    (add-milk))
  :ok)

(defn add-butters [n]
  (dotimes [e n]
    (add-butter))
  :ok)

;; Exercise 10
;; Rewrite bake-cake to use the new functions
(defn bake-cake []
  (add-flour-cups 2)
  (add-eggs 2)
  (add-milk-cups 1)
  (add-sugar-cups 1)
  (mix)
  (pour-into-pan)
  (bake-pan 25)
  (cool-pan))

(defn add-squeezed
  ([ingredient amount]
   (if (squeezed? ingredient)
     (do
       (dotimes [i amount]
         (grab ingredient)
         (squeeze)
         (add-to-bowl))
       :ok)
     (error "This function only works on squeezed ingredients. You asked me to squeeze" ingredient)))
  ([ingredient]
   (add-squeezed ingredient 1)))

(defn add-scooped
  ([ingredient amount]
   (if (scooped? ingredient)
     (do
       (dotimes [i amount]
         (grab :cup)
         (scoop ingredient)
         (add-to-bowl)
         (release))
       :ok)
     (error "This function only works on scooped ingredients. You asked me to scoop" ingredient)))
  ([ingredient]
   (add-scooped ingredient 1)))

(defn add-simple
  ([ingredient amount]
   (if (simple? ingredient)
     (do
       (dotimes [i amount]
         (grab ingredient)
         (add-to-bowl))
       :ok)
     (error "This function only works on simple ingredients. You asked me to add" ingredient)))
  ([ingredient]
   (add-simple ingredient 1)))

;; Exercise 11
;; Rewrite add to be variadic and to use the new add-functions
(defn add 
  ([ingredient amount]
   (cond
     (squeezed? ingredient)
     (add-squeezed ingredient amount)
     (scooped? ingredient)
     (add-scooped ingredient amount)
     (simple? ingredient)
     (add-simple ingredient amount)
     :else
     (do
       (println "I do not know the ingredient" ingredient))))
  ([ingredient]
   (add ingredient 1)))

;; Exercise 12
;; Rewrite bake-cake to use the new add function.
(defn bake-cake []
  (add :flour 2)
  (add :egg 2)
  (add :milk)
  (add :sugar)
  (mix)
  (pour-into-pan)
  (bake-pan 25)
  (cool-pan))

;; Exercise 13
;; Write a function bake-cookies to make it have a structure similar to the recipe.
(defn bake-cookies []
  (add :egg 1)
  (add :flour 1)
  (add :sugar 1)
  (add :butter)
  (mix)
  (pour-into-pan)
  (bake-pan 30)
  (cool-pan))

(def pantry-ingredients #{:flour :sugar})

(defn from-pantry? [ingredient]
  (contains? pantry-ingredients ingredient))

(from-pantry? :flour)

;; Day 2, Exercise 1
;; Write fridge-ingredients and from-fridge? using this new idiom.
(def fridge-ingredients #{:milk :butter :egg})

(defn from-fridge? [ingredient]
  (contains? fridge-ingredients ingredient))

;; D2 - Exercise 2
;; Refactor scooped?, squeezed?, and simple? to use this new idiom.
(def scooped-ingredients #{:flour :sugar :milk :cocoa})

(defn scooped? [ingredient]
  (contains? scooped-ingredients ingredient))

(def squeezed-ingredients #{:egg})

(defn squeezed? [ingredient]
  (contains? squeezed-ingredients ingredient))

(def simple-ingredient #{:butter})

(defn simple? [ingredient]
  (contains? simple-ingredient ingredient))

(defn fetch-from-pantry
  ([ingredient]
   (fetch-from-pantry ingredient 1))
  ([ingredient amount]
   (if (from-pantry? ingredient)
     (do
       (go-to :pantry)
       (dotimes [i amount]
         (load-up ingredient))
       (go-to :prep-area)
       (dotimes [i amount]
         (unload ingredient)))
     (error "This function only works on ingredients that are stored in the pantry. You asked me to fetch" ingredient))))

(defn fetch-from-fridge
  ([ingredient]
   (fetch-from-fridge ingredient 1))
  ([ingredient amount]
   (if (from-fridge? ingredient)
     (do
       (go-to :fridge)
       (dotimes [i amount]
         (load-up ingredient))
       (go-to :prep-area)
       (dotimes [i amount]
         (unload ingredient)))
     (error "This function only works on ingredients that are stored in the fridge. You asked me to fetch" ingredient))))

;; D2 - Ex 3
;; Write a function fetch-ingredient which takes the name of an ingredient and an amount and fetches that amount of the ingredient.
;; Also make it accept just the ingredient, with a default amount of 1.
(defn fetch-ingredient
  ([ingredient]
   (fetch-ingredient ingredient 1))
  ([ingredient amount]
   (cond
     (from-fridge? ingredient)
     (fetch-from-fridge ingredient amount)
     (from-pantry? ingredient)
     (fetch-from-pantry ingredient amount)
     :else
     (error "Unknown ingredient" ingredient))))

(def ingredients {:flour 10
                  :egg 7
                  :sugar 12
                  :milk 3
                  :butter 6})

(defn load-up-amount [ingredient amount]
  (dotimes [i amount]
    (load-up ingredient)))

(defn unload-amount [ingredient amount]
  (dotimes [i amount]
    (unload ingredient)))

(def locations {:pantry pantry-ingredients
                :fridge fridge-ingredients})

(keys locations)

(defn fetch-list [shopping]
  (doseq [location (keys locations)]
    (go-to location)
    (doseq [ingredient (get locations location)]
      (load-up-amount ingredient (get shopping ingredient 0))))
  (go-to :prep-area)
  (doseq [location (keys locations)]
    (doseq [ingredient (get locations location)]
      (unload-amount ingredient (get shopping ingredient 0)))))

(def orders {:orderid 123
             :address "323 Robot Ln"
             :items {:cake 14
                     :cookies 12}})
(get orders :items)
(def receipts {:orderid 123
               :address "323 Robot Ln"
               :rackids [:cooling-rack-345
                         :cooling-rack-675]})

;; D2, Ex 4
;; Write a function day-at-the-bakery which will represent what X5 does at the bakery.
;; Requirements: Get the orders + Bake items + Send receipts to the delivery bot
(defn day-at-the-bakery []
  (let [orders (get-morning-orders)]
    (doseq [order orders]
      (let [items (get order :items)]
        (dotimes [i (get items :cake 0)]
          (fetch-list {:egg 2 
                       :flour 2 
                       :milk 1 
                       :sugar 1})
          (let [rack-id (bake-cake)]
            (delivery {:orderid (get order :orderid)
                       :address (get order :address)
                       :rackids [rack-id]})))
        (dotimes [i (get items :cookies 0)]
          (fetch-list {:egg 1
                       :flour 1
                       :sugar 1
                       :butter 1})
          (let [rack-id (bake-cookies)]
            (delivery {:orderid (get order :orderid)
                       :address (get order :address)
                       :rackids [rack-id]})))))))

(defn -main []
  (day-at-the-bakery)
  (status))
