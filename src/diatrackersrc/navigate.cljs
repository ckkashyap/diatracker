(ns diatrackersrc.navigate
  (:require
   [diatrackersrc.consts :as cnst]
   [diatrackersrc.dom :as dom]
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
    "page1" (do
        (set! (.-href js/location) "#page1")
        (println "Initializing page 1"))
    "page2" (do
              (set! (.-href js/location) "#page2")
        (let [
              dt (js/Date.)
              m (.getMonth dt)
              y (+ 1900 (.getYear dt))
              d (- (.getDate dt) 1)
              ml (dom/getElementById "page2MonthList")
              dl (dom/getElementById "page2DayList")
              yt (dom/getElementById "page2Year")
              ]
          (set! (.-selectedIndex ml) m)
          (set! (.-selectedIndex dl) d)
          (set! (.-value yt) y)
          (println d)
          (println m (+ 1900 y) d)
          )
        (println "Initializing page 2"))

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
  (let [
        _ (println from)
        _ (println to)
        fromDiv (.getElementById js/document from)
        toDiv (.getElementById js/document to)
        ]
      (set! (.-className fromDiv) "fullPageInvisible")
      (set! (.-className toDiv) "fullPageVisible")
      (initPage to)
      (dom/setCurrentPageNumber to)
))



(defn ^:export locationChanged [e]
  (let [
        url (.-href js/location)
        re (js/RegExp. ".*#(.+)$" "i")
        m (.exec re url)
        from (dom/getCurrentPageNumber)
        to (if m (aget m 1) "page1")
        ]
    (gotoPage from to)
    (println url)))
    
