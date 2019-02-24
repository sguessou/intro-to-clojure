(ns intro-to-clojure.core
  (:require [bakery.core :refer :all]))

(defn error [& args]
  (apply println args)
  :error)

;; Exercise 7
;; Add a recipe for every baked good X5 has learned so far.
(def baking {:recipes {:cake {:ingredients {:egg 2
                                            :flour 2
                                            :sugar 1
                                            :milk 1}
                              :steps [[:add :all]
                                      [:mix]
                                      [:pour]
                                      [:bake 25]
                                      [:cool]]}
                       {:cookies {:ingredients {:egg 1
                                               :flour 1
                                               :sugar 1
                                               :butter 1}
                              :steps [[:add :all]
                                      [:mix]
                                      [:pour]
                                      [:bake 30]
                                      [:cool]]}}
                       {:brownies {:ingredients {:egg 2
                                                :flour 2
                                                :sugar 1
                                                :cocoa 2
                                                :milk 1
                                                :butter 2}
                              :steps [[:add :butter]
                                      [:add :sugar]
                                      [:add :cocoa]
                                      [:mix]
                                      [:add :flour]
                                      [:add :egg]
                                      [:add :milk]
                                      [:mix]
                                      [:pour]
                                      [:bake 35]
                                      [:cool]]}}}})

;; Exercise 1
;; Write a function perform that takes a vector as argument. If the first element of the vector is :cool, run cool-pan.

(defn perform_1 [action]
  (when (= (first action) :cool)
    (cool-pan)))

;; Ex 2
;; Modify perform to also call mix if the first element of the vector is :mix.
(defn perform_2 [step]
  (let [action (first step)]
    (cond
      (= action :cool)
      (cool-pan)
      (= action :mix)
      (mix)
      :else
      (error "Unknown action" action))))

;; Ex 3
;; Modify perform to pour the bowl into the pan when the first element is :pour. 
;; Also, perform should bake when the first element is :bake.
;; The number of minutes will be the second element.
(defn perform_3 [step]
  (let [action (first step)]
    (cond
      (= action :cool)
      (cool-pan)
      (= action :mix)
      (mix)
      (= action :pour)
      (pour-into-pan)
      (= action :bake)
      (bake-pan (second step))
      :else
      (error "Unknown action" action))))

(def baking {:recipes {:cake {:ingredients {:egg 2
                                            :flour 2
                                            :sugar 1
                                            :milk 1}
                              :steps [[:add :all]
                                      [:mix]
                                      [:pour]
                                      [:bake 25]
                                      [:cool]]}}}) 
;; Ex 4 
;; Modify perform to add ingredients to the bowl if the first element of the vector is :add.
;; How it operates depends on the rest of the arguments.
;; If there is one argument and it is the keyword :all, then add all ingredients in the recipe.
;; If there is one argument and it is the name of an ingredient in the recipe, add the amount specified in the ingredient list.
;; If there are two arguments, the first is the name of the ingredient and the second is the amount to add.
(defn perform [ingredients step]
  (cond
    (= (first step) :cool)
    (cool-pan)
    (= (first step) :mix)
    (mix)
    (= (first step) :pour)
    (pour-into-pan)
    (= (first step) :bake)
    (bake-pan (second step))
    (= (first step) :add)
    (cond
      (and (= (count step) 2)
           (= (second step) :all))
      (doseq [kv ingredients]
        (add (first kv) (second kv)))
      (and (= (count step) 2)
           (contains? ingredients (second step)))
      (add (second step) (get ingredients (second step)))
      (= (count step) 3)
      (add (second step) (nth step 2))) 
    :else
    (error "Unknown action" (first step))))

;; Exercise 5
;; Write a function bake-recipe which takes a recipe, performs all of the steps, and returns the cooling rack id where the item is placed.
(defn bake-recipe [recipe]
  (last
   (for [step (get recipe :steps)] 
     (perform (get recipe :ingredients) step))))


(defn bake [item]
  (let [recipes (get baking :recipes)]
    (if (contains? recipes item)
      (bake-recipe (get recipes item))
      (error "I don't know how to bake" item))))


;; Exercise 6
;; Rewrite bake to use bake-recipe. It should still return the cooling rack id.
(defn bake [item])

(defn add-egg []
  (grab :egg)
  (squeeze)
  (add-to-bowl))

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

(def scooped-ingredients #{:flour :sugar :milk :cocoa})

(defn scooped? [ingredient]
  (contains? scooped-ingredients ingredient))

(def squeezed-ingredients #{:egg})

(defn squeezed? [ingredient]
  (contains? squeezed-ingredients ingredient))

(def simple-ingredients #{:butter})

(defn simple? [ingredient]
  (contains? simple-ingredients ingredient))

(defn add-eggs [n]
  (dotimes [e n]
    (add-egg))
  :ok)

(defn add-flour-cups [n]
  (dotimes [e n]
    (add-flour))
  :ok)

(defn add-milk-cups [n]
  (dotimes [e n]
    (add-milk))
  :ok)

(defn add-sugar-cups [n]
  (dotimes [e n]
    (add-sugar))
  :ok)

(defn add-butters [n]
  (dotimes [e n]
    (add-butter))
  :ok)

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

(defn add
  ([ingredient]
   (add ingredient 1))
  ([ingredient amount]
   (cond
     (squeezed? ingredient)
     (add-squeezed ingredient amount)
     (scooped? ingredient)
     (add-scooped ingredient amount)
     (simple? ingredient)
     (add-simple ingredient amount)
     :else
     (error "I do not know the ingredient" ingredient))))

(def pantry-ingredients #{:flour :sugar :cocoa})

(defn from-pantry? [ingredient]
  (contains? pantry-ingredients ingredient))

(def fridge-ingredients #{:milk :egg :butter})

(defn from-fridge? [ingredient]
  (contains? fridge-ingredients ingredient))

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
      (error "I don't know where to get" ingredient))))

(defn load-up-amount [ingredient amount]
  (dotimes [i amount]
    (load-up ingredient)))

(defn unload-amount [ingredient amount]
  (dotimes [i amount]
    (unload ingredient)))

(def locations {:pantry pantry-ingredients
                :fridge fridge-ingredients})

(defn fetch-list [shopping-list]
  (doseq [location (keys locations)]
    (go-to location)
    (doseq [ingredient (get locations location)]
      (load-up-amount ingredient (get shopping-list ingredient 0))))

  (go-to :prep-area)
  (doseq [location (keys locations)]
    (doseq [ingredient (get locations location)]
      (unload-amount ingredient (get shopping-list ingredient 0)))))

(defn add-ingredients [a b]
  (merge-with + a b))

(defn multiply-ingredients [n ingredients]
  (into {}
    (for [kv ingredients]
      [(first kv) (* n (second kv))])))

;; Exercise 8
;; Modify order->ingredients to not refer to individual items in the code.
;; Instead, it should use the meaning given to the name of the item in the baking database.
(defn order->ingredients [order]
  (let [items (get order :items)
        recipes (get baking :recipes)]
    (reduce add-ingredients {}
            (for [kv items]
              (let [recipe (get recipes (first kv))
                    ingredients (get recipe :ingredients)]
                (multiply-ingredients (second kv) ingredients))))))

;; Exercise 9
;; Add a section to the baking database which contains information about all of our ingredients.
;; It should say where to find the ingredients and how to add them.
(def baking {:ingredients {:egg {:storage :fridge
                                 :usage :squeezed}
                           :milk {:storage :fridge
                                  :usage :scooped}
                           :butter {:storage :fridge
                                    :usage :simple}
                           :flour {:storage :pantry
                                   :usage :scooped}
                           :cocoa {:storage :pantry
                                   :usage :scooped}
                           :sugar {:storage :pantry
                                   :usage :scooped}}
             :recipes {:cake {:ingredients {:egg 2
                                :flour 2
                                :sugar 1
                                :milk 1}
                              :steps [[:add :all]
                                      [:mix]
                                      [:pour]
                                      [:bake 25]
                                      [:cool]]}

                       :cookies {:ingredients {:egg 1
                                               :flour 1
                                               :sugar 1
                                               :butter 1}
                                 :steps [[:add :all]
                                         [:mix]
                                         [:pour]
                                         [:bake 30]
                                         [:cool]]}
                       :brownies {:ingredients {:egg 2
                                                :flour 2
                                                :sugar 1
                                                :cocoa 2
                                                :milk 1
                                                :butter 2}
                                  :steps [[:add :butter]
                                          [:add :sugar]
                                          [:add :cocoa]
                                          [:mix]
                                          [:add :flour]
                                          [:add :egg]
                                          [:add :milk]
                                          [:mix]
                                          [:pour]
                                          [:bake 35]
                                          [:cool]]}}})





(defn orders->ingredients [orders]
  (reduce add-ingredients {}
          (for [order orders]
            (order->ingredients order))))

(defn bake-cake []
  (add :egg 2)
  (add :flour 2)
  (add :milk 1)
  (add :sugar 1)
  (mix)
  (pour-into-pan)
  (bake-pan 25)
  (cool-pan))

(defn bake-cookies []
  (add :egg 1)
  (add :flour 1)
  (add :sugar 1)
  (add :butter 1)
  (mix)
  (pour-into-pan)
  (bake-pan 30)
  (cool-pan))

(defn bake [item]
  (cond
    (= item :cake)
    (bake-cake)
    (= item :cookies)
    (bake-cookies)
    :else
    (error "I don't know how to bake" item)))

(defn day-at-the-bakery []
  (let [orders (get-morning-orders)
        ingredients (orders->ingredients orders)]
    (fetch-list ingredients)
    (doseq [order orders]
      (let [items (get order :items)
            racks (for [kv items
                        i (range (second kv))]
                    (bake (first kv)))
            receipt {:orderid (get order :orderid)
                     :address (get order :address)
                     :rackids racks}]
        (delivery receipt))))))

(defn bake-brownies []
  (add :sugar 1)
  (add :cocoa 2)
  (add :butter 2)
  (mix)
  (add :milk 1)
  (add :flour 2)
  (add :egg 2)
  (mix)
  (pour-into-pan)
  (bake-pan 35)
  (cool-pan))

(defn -main []
  (day-at-the-bakery))

