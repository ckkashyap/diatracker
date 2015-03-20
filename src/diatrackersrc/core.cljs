(ns diatrackersrc.core)

(enable-console-print!)

(def months
  [
   "Jan" "Feb" "Mar"
   "Apr" "May" "Jun"
   "Jul" "Aug" "Sep"
   "Oct" "Nov" "Dec"
])

(def meals
  [
   "Before Breakfast"
   "Breakfast"
   "Before Lunch"
   "Lunch"
   "Snacks"
   "Before Dinner"
   "Dinner"
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

(defn clj->json
  [i]
  (.stringify js/JSON (clj->js i)))

(defn persistRecord [m d y f v c]
  (let
      [ 
       r  { :m m :d d :y y :f f :c c}
       j  (clj->json r)

       storedMaxID (.-maxID js/localStorage)
       maxID (js/parseInt (if storedMaxID storedMaxID "0"))
       _ (set! (.-maxID js/localStorage) (+ 1 maxID))
       key (str "reading" maxID)

       _ (.setItem js/localStorage key j)
       
       ]
    (println maxID)))
      
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
                               val (.-value (aget (.-selectedOptions div) 0))
                               ] val))

        monthVal (getValFromList "page2MonthList")
        dayVal (getValFromList "page2DayList")
        foodVal (getValFromList "page2FoodList")


        ]
    (set! (.-value reading) "100")
    (println foodVal monthVal dayVal)
    (.focus reading)
    (.select reading)
    (if (> readingIntVal 0)
      (do
        (persistRecord monthVal dayVal yearVal foodVal readingIntVal commentsVal )
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
    



(defn ^:export setDummyData []
  (let [
        startDate (js/Date. 2013 0 1 8 0 0 0)
        newDate (fn [d ctr] (js/Date. (+ (* 1000 60 60 4 ctr) (.getTime startDate))))
        ]
    (.clear js/localStorage)
    (dotimes [i 2000]
      (let [
            dt (newDate startDate i)
            m (nth months (js/parseInt (.getMonth dt)))
            d (.getDate dt)
            y (+ 1900 (.getYear dt))
            rand (js/Math.random)
            f (nth meals (mod i 7))
            v (js/Math.round (+ 70 (* rand 100)))
            ]
        (persistRecord m d y f v "hello")
      ))))
