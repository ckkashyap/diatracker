(ns diatrackersrc.core
  (:require
   [diatrackersrc.consts :as consts]
   [diatrackersrc.dom :as dom]
  ))



(enable-console-print!)

(declare showHistory)
(declare dingo)

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
))

(defn clj->json
  [i]
  (.stringify js/JSON (clj->js i)))

(defn persistRecord [m d y f v t c];month date year food value type  comment
  (let
      [ 
       r  { :m m :d d :y y :f f :v v :t t :c c}
       j  (clj->json r)

       storedMaxID (.getItem  js/localStorage "maxID")
       maxID (js/parseInt (if storedMaxID storedMaxID "0"))
       _  (.setItem js/localStorage "maxID" (+ 1 maxID))
       key (str "reading" maxID)

       _ (.setItem js/localStorage key j)
       
       ]
    (println maxID)))

(defn readLastNRecords [n]
  (let
      [
       storedMaxID (.getItem  js/localStorage "maxID")
       startID (- storedMaxID 1)
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
  

(defn ^:export saveData []
  (let [
        reading (.getElementById js/document "page2Reading")
        readingVal (.-value reading)
        readingParseInt (js/parseInt readingVal)
        readingIntVal (if (js/isNaN readingParseInt) 0 readingParseInt)
        comments (.getElementById js/document "page2Comments")
        commentsVal (.-value comments)
        year (.getElementById js/document "page2Year")
        yearVal (.-value year)
        
        getValFromList (fn [n]
                         (let [
                               div (.getElementById js/document n)
                               idx (.-selectedIndex div)
                               val (.-value (aget (.-options div) idx))
                               ] val))


        monthVal (getValFromList "page2MonthList")
        dayVal (getValFromList "page2DayList")
        foodVal (getValFromList "page2FoodList")
        typeVal (getValFromList "page2TypeList")


        ]
    (set! (.-value reading) "100")
    (println foodVal monthVal dayVal)
    (if (> readingIntVal 0)
      (do
        (persistRecord monthVal dayVal yearVal foodVal readingIntVal typeVal commentsVal )
        (println commentsVal)
        (set! (.-value reading) "100")
        (set! (.-value comments) "")      
        true)
      (do
        (dingo)
        (js/alert "Please enter a valid value")
        false))))

(defn ^:export saveDataAndSwitch []
  (if (saveData)
    (gotoPage 2 1)))

(defn ^:export showHistory []
  (let [
        records (readLastNRecords 10)
        countOfRecords (count records)
       ]
       
  (dotimes [i countOfRecords]
    (println (records i)))))

    
(defn ^:export setDummyData []
  (let [
        startDate (js/Date. 2013 0 1 8 0 0 0)
        newDate (fn [d ctr] (js/Date. (+ (* 1000 60 60 4 ctr) (.getTime startDate))))
        mealsCount (count consts/meals)
        typesCount (count consts/types)
        ]
    (.clear js/localStorage)
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
        (persistRecord m d y f v t "hello")
      ))))

(defn dingo []
  (let [
        d (dom/getElementById "page2FoodList")
        e (.createElement js/document "option")
        _ (set! (.-text e) "AAA")
        _ (.add (.-options d) e)
        ] (println "hello")))
