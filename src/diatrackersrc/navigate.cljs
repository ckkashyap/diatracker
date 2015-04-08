(ns diatrackersrc.navigate
  (:require
   [diatrackersrc.consts :as cnst]
   [diatrackersrc.dom :as dom]
   [diatrackersrc.pages.adddata :as adddata]
   [diatrackersrc.pages.mainpage :as mainpage]
   [diatrackersrc.storage :as store]

  ))

(defn ^:export showHistory []
  (let [
        records (store/readLastNRecords cnst/dataRecord 10)
        countOfRecords (count records)
       ]
       
  (dotimes [i countOfRecords]
    (println (records i)))))


    


(defn initPage [pageNumber]
  (case pageNumber
    "main" (do
        (set! (.-href js/location) "#main")
        (mainpage/initPage))

    "adddata" (adddata/initPage)

    "page3" (do
              (set! (.-href js/location) "#page3")
        (showHistory)
        (println "Initializing page 3"))

    "readingType" (do
              (set! (.-href js/location) "#readingType")
              (diatrackersrc.newreadingtype/initpage)
        (println "Initializing page readingType"))

    
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
    
