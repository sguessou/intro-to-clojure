(ns intro-to-clojure.emoji
  (:import [org.jsoup Jsoup]))

(defonce page (-> "https://unicode.org/emoji/charts/full-emoji-list.html"
              Jsoup/connect
              (.timeout 60000)
              (.maxBodySize (* 100 1024 1024))
              .get))

(def table (first (.select page "table")))
(type table)

(def trs (.select table "tr"))
(def trs-tds (for [tr trs]
               (vec (for [td (.select tr "td")]
                   (.text td)))))

(def emoji
  (for [row (filter not-empty trs-tds)]
    {:unicode (get row 1)
     :string (get row 2)
     :name (get row 13)
     :keywords (mapv clojure.string/trim
                     (clojure.string/split (get row 14) #" "))}))

(nth emoji 59)
(get (nth trs-tds 6) 1)

(nth  trs-tds 6)
