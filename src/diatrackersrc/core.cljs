(ns diatrackersrc.core)

(enable-console-print!)

(def months
  [
   "Jan" "Feb" "Mar"
   "Apr" "May" "Jun"
   "Jul" "Aug" "Sep"
   "Oct" "Nov" "Dec"
])

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
              ml (.getElementById js/document "page2MonthList")
              dl (.getElementById js/document "page2DayList")
              yt (.getElementById js/document "page2Year")
              ]
          (set! (.-selectedIndex ml) m)
          (set! (.-selectedIndex dl) d)
          (set! (.-value yt) y)
          (println d)
          (println m (+ 1900 y) d)

          )
        (println "Initializing page 2"))
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
      
(defn ^:export saveData []
  (let [
        reading (.getElementById js/document "page2Reading")
        readingVal (.-value reading)
        readingParseInt (js/parseInt readingVal)
        readingIntVal (if (js/isNaN readingParseInt) 0 readingParseInt)
        
        comments (.getElementById js/document "page2Comments")
        commentsVal (.-value comments)
        ]
    (set! (.-value reading) "100")
    (.focus reading)
    (.select reading)
    (if (> readingIntVal 0)
      (do
        (println commentsVal)
        (set! (.-value reading) "100")
        (set! (.-value comments) "")      
        true)
      (do
        (js/alert "Please enter a valid value")
        false))))

(defn ^:export saveDataAndSwitch []
  (if (saveData)
    (gotoPage 2 1)))
    
