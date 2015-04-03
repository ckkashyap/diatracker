(ns diatrackersrc.core
  (:require
   [diatrackersrc.consts :as consts]
   [diatrackersrc.dom :as dom]
   [diatrackersrc.storage :as store]
   [diatrackersrc.page2 :as page2]
  ))




(enable-console-print!)

(declare showHistory)

(defn initPage [pageNumber]
  (case pageNumber
    1 (do
        (println "Initializing page 1"))
    2 (do
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
    3 (do
        (showHistory)
        (println "Initializing page 3"))
    
    (println "Cannot initialize" pageNumber)
))

(defn ^:export gotoPage [from, to]
  (let [
        fromDiv (.getElementById js/document (str "page" from))
        toDiv (.getElementById js/document (str "page" to))
        ]
      (set! (.-className fromDiv) "fullPageInvisible")
      (set! (.-className toDiv) "fullPageVisible")
      (initPage to)
      (dom/setCurrentPageNumber to)
))

(defn clj->json
  [i]
  (.stringify js/JSON (clj->js i)))


(defn readLastNRecords [n]
  (let
      [
       storedMaxID (.getItem  js/localStorage "maxID")
       startID (- storedMaxID 1)
       _ (println startID)
       ]
  (loop [i n id startID v []]
    (if (or (= i 0) (< id 0))
      (do
        (println v)
        v)
      (let [
            key (str "reading" id)
            val (js->clj (js/JSON.parse (.getItem js/localStorage key)))
            ]
        
        (recur (dec i) (dec id) (conj v val)))))))
  
(defn ^:export saveDataAndSwitch []
  (if (page2/saveData)
    (gotoPage 2 1)))

(defn ^:export showHistory []
  (let [
        records (readLastNRecords 10)
        countOfRecords (count records)
       ]
       
  (dotimes [i countOfRecords]
    (println (records i)))))


    
(defn ^:export locationChanged [e]
  (let [
        url (.-href js/location)
        re (js/RegExp. ".*#([0-9]+)$" "i")
        m (.exec re url)
        from (dom/getCurrentPageNumber)
        to (if m (aget m 1) 1)
        ]
    (gotoPage from to)
    (println url)))
    
(defn ^:export setDummyData []
  (let [
        startDate (js/Date. 2013 0 1 8 0 0 0)
        newDate (fn [d ctr] (js/Date. (+ (* 1000 60 60 4 ctr) (.getTime startDate))))
        mealsCount (count consts/meals)
        typesCount (count consts/types)
        ]
    (store/clear)
    (dotimes [i 500]
      (let [
            dt (newDate startDate i)
            m (nth consts/months (js/parseInt (.getMonth dt)))
            d (.getDate dt)
            y (+ 1900 (.getYear dt))
            rand (js/Math.random)
            f (nth consts/meals (mod i mealsCount))
            t (nth consts/types (mod i typesCount))
            v (js/Math.round (+ 70 (* rand 100)))
            ]
        (page2/persistRecord m d y f v t "hello")
      ))))

