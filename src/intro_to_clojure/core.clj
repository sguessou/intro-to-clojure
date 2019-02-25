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

;; Exercise 14
;; We would like to replace the cond in perform with a map which follows the same pattern as the usage map we just wrote.
;; Create a map actions where the keys are action names and the values are functions implementing those actions.
(def actions {:cool (fn [ingredients step] (cool-pan))
              :mix (fn [ingredients step] (mix))
              :pour (fn [ingredients step] (pour-into-pan))
              :bake (fn [ingredients step] (bake-pan (second step)))
              :add (fn [ingredients step]
                     (cond
                       (and (= (count step) 2)
                            (= (second step) :all))
                       (doseq [kv ingredients]
                         (add (first step) (second step)))
                       (and (= (count step) 2)
                            (contains? ingredients (second step)))
                       (add (second step) (get ingredients (second step)))
                       (= (count step) 3)
                       (add (second step) (nth step 2))))})

;; Exercise 15
;; Rewrite perform to use the new actions map.
(defn perform [ingredients step]
  (let [f (get actions (first step) (fn [ingredients step] 
                                      (error "Unknown action" (first step))))]
    (f ingredients step)))

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

(def usage {:squeezed (fn [ingredient amount]
                        (dotimes [i amount]
                          (grab ingredient)
                          (squeeze)
                          (add-to-bowl)))
            :simple (fn [ingredient amount]
                      (dotimes [i amount]
                        (grab ingredient)
                        (add-to-bowl)))
            :scooped (fn [ingredient amount]
                       (grab :cup)
                       (dotimes [i amount]
                         (scoop ingredient)
                         (add-to-bowl))
                       (release))})

;; Exercise 13
;; Rewrite add to use the new usage map.
(defn usage-type [ingredient]
  (let [ingredients (get baking :ingredients)
        info (get ingredients ingredient)]
    (get info :usage)))

(defn add
  ([ingredient]
   (add ingredient 1))
  ([ingredient amount]
   (let [ingredient-type (usage-type ingredient)]
     (if (contains? usage ingredient-type)
       (let [f (get usage ingredient-type)]
         (f ingredient amount))
       (error "I do not know the ingredient" ingredient)))))


(defn load-up-amount [ingredient amount]
  (dotimes [i amount]
    (load-up ingredient)))

(defn unload-amount [ingredient amount]
  (dotimes [i amount]
    (unload ingredient)))

;; Exercise 11
;; There is a duplication between fetch-from-pantry and fetch-from-fridge.
;; Rewrite fetch-ingredient to not use these functions.
;; Then get rid of fetch-from-pantry and fetch-from-fridge.
(defn fetch-ingredient
  ([ingredient]
    (fetch-ingredient ingredient 1))
  ([ingredient amount]
    (let [ingredients (get baking :ingredients)
         info (get ingredients ingredient)]
      (if (contains? ingredients ingredient)
        (do
          (go-to (get info :storage))
          (load-up-amount ingredient amount)
          (go-to :prep-area)
          (unload-amount ingredient amount))
        (error "I dont know how the ingredient" ingredient)))))

;; Exercise 12
;; Rewrite fetch-list to remove the usage of the locations map and the sets pantry-ingredients and fridge-ingredients.
;; Hint: Use group-by.
(defn storage-location [item]
  (let [ingredients (get baking :ingredients)
        info (get ingredients item)]
    (get info :storage)))

(defn fetch-list [shopping-list]
  (let [locations (group-by (fn [item-amount] (storage-location (first item-amount))) shopping-list)]
      (doseq [location (keys locations)]
        (go-to location)
        (doseq [item-amount (get locations location)]
          (load-up-amount (first item-amount) (second item-amount))))
      (go-to :prep-area)
      (doseq [location (keys locations)]
        (doseq [item-amount (get locations location)]
          (unload-amount (first item-amount) (second item-amount))))))

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

;; Exercise 10 
;; Rewrite scooped?, squeezed?, and simple? to use the new database instead of their existing sets.
(defn scooped? [ingredient]
  (let [ingredients (get baking :ingredients)
        item (get ingredients ingredient)]
      (= :scooped (get item :usage))))

(defn squeezed? [ingredient]
  (let [ingredients (get baking :ingredients)
        item (get ingredients ingredient)]
      (= :squeezed (get item :usage))))

(defn simple? [ingredient]
  (let [ingredients (get baking :ingredients)
        item (get ingredients ingredient)]
      (= :simple (get item :usage))))


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

