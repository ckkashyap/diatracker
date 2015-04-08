(ns diatrackersrc.settings
  (:require
   [diatrackersrc.consts :as consts]
   [diatrackersrc.dom :as dom]
   [diatrackersrc.storage :as store]
  ))


(defn html [] (str ""))



(defn initPage []
  (let [
        _ (set! (.-innerHTML (dom/getElementById "fullscreen")) (html))
        ]
  (println "init settings page")))
