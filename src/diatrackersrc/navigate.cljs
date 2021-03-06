(ns diatrackersrc.navigate
  (:require
   [diatrackersrc.consts :as cnst]
   [diatrackersrc.dom :as dom]
   [diatrackersrc.pages.adddata :as adddata]
   [diatrackersrc.pages.settings :as settings]
   [diatrackersrc.pages.main :as main]
   [diatrackersrc.storage :as store]

  ))

(defn ^:export showHistory []
  (let [
        records (store/readLastNRecords "table" 10 {:m "" :c "" :t "" :f "" :v ""})
        countOfRecords (count records)
       ]
       
  (dotimes [i countOfRecords]
    (println (records i)))))


(defn initPage [pageNumber]
  (case pageNumber
    "main" (do
        (set! (.-href js/location) "#main")
        (main/initPage))

    "adddata" (adddata/initPage)

    "settings" (settings/initPage "settings")
    "settingsType" (settings/initPage "settingsType")
    "settingsTime" (settings/initPage "settingsTime")

    "page3" (do
              (set! (.-href js/location) "#page3")
        (showHistory)
        (println "Initializing page 3"))

    
    (println "Cannot initialize " pageNumber)
))

(defn ^:export gotoPage [from, to]
  (initPage to)
  (dom/setCurrentPageNumber to))



(defn ^:export locationChanged [e]
  (let [
        url (.-href js/location)
        re (js/RegExp. ".*#(.+)$" "i")
        m (.exec re url)
        from (dom/getCurrentPageNumber)
        to (if m (aget m 1) "main")
        ]
    (gotoPage from to)
    (println url)))
    
