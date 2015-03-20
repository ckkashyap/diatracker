(defproject diatrackersrc "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2755"]]

  :node-dependencies [[source-map-support "0.2.8"]]

  :plugins [[lein-cljsbuild "1.0.4"]
            [lein-npm "0.4.0"]]

  :source-paths ["src" "target/classes"]

  :clean-targets ["out" "out-adv"]

  :cljsbuild {
    :builds [{:id "dev"
              :source-paths ["src"]
              :compiler {
                :main diatrackersrc.core
                :output-to "out/diatrackersrc.js"
                :output-dir "out"
                :optimizations :none
                :cache-analysis true
                :externs ["diatracker.extern.js"]         
                :source-map true}}
             {:id "release"
              :source-paths ["src"]
              :compiler {
                :main diatrackersrc.core
                :output-to "out-adv/diatrackersrc.min.js"
                :output-dir "out-adv"
                :optimizations :advanced
                :externs ["diatracker.extern.js"]         
                :pretty-print false}}]})
