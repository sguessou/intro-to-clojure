(defproject intro-to-clojure "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [lispcast/bakery "1.0.0"]]
  :repl-options {:init (do
                         (use 'bakery.core)
                         (use 'intro-to-clojure.core))
                 :skip-default-init true}
  :main intro-to-clojure.core)
