(ns intro-to-clojure.game_of_json
  (:require [cheshire.core :as json]))

;; Using cheshire
(defonce books-raw (slurp "https://raw.githubusercontent.com/joakimskoog/AnApiOfIceAndFire/master/data/books.json"))

(defonce characters-raw (slurp "https://raw.githubusercontent.com/joakimskoog/AnApiOfIceAndFire/master/data/characters.json"))

(defonce houses-raw (slurp "https://raw.githubusercontent.com/joakimskoog/AnApiOfIceAndFire/master/data/houses.json"))

(defonce books-json (json/parse-string books-raw))

(defonce characters-json (json/parse-string characters-raw))

(defonce houses-json (json/parse-string houses-raw))

(first houses-json)
