(ns diatrackersrc.newreadingtype
  (:require
   [diatrackersrc.consts :as consts]
   [diatrackersrc.dom :as dom]
   [diatrackersrc.storage :as store]
   [diatrackersrc.navigate :as navigate]
  ))



(defn initpage []
  (println "init reading page"))

(defn ^:export addNewType []
  (let [
        storedMaxID (store/getItem "typeMaxID")
        maxID (js/parseInt (if storedMaxID storedMaxID "0"))
        _ (println maxID)
        _  (store/setItem "typeMaxID" (+ 1 maxID))
        key (str "type" maxID)
        ]
  (println "saving new type")))


