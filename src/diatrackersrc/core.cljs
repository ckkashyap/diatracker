(ns diatrackersrc.core)


(enable-console-print!)

(defn ^:export switchPage []
  (let [
        pageNumberDiv (.getElementById js/document "pageNumber")
        pageNumber (.-innerHTML pageNumberDiv)
        newPageNumber (if (= pageNumber "1") "2" "1")
        oldDiv (.getElementById js/document (str "page" pageNumber))
        newDiv (.getElementById js/document (str "page" newPageNumber))
        _ (set! (.-innerHTML pageNumberDiv) newPageNumber)
        ]
      (set! (.-className oldDiv) "fullPageInvisible")
      (set! (.-className newDiv) "fullPageVisible")
      (println pageNumber)
      (println newPageNumber)
  (println "Hello world123!")))

(defn ^:export saveData []
  (let [
        reading (.getElementById js/document "page2Reading")
        readingVal (.-value reading)
        readingParseInt (js/parseInt readingVal)
        readingIntVal (if (js/isNaN readingParseInt) 0 readingParseInt)
        
        comments (.getElementById js/document "page2Comments")
        commentsVal (.-value comments)
        ]
    (if (= readingIntVal 100)
      (do
        (println (str readingVal "1234"))
        (println commentsVal)
        (println (+ readingIntVal 10))
        (set! (.-value reading) "100")
        (set! (.-value comments) "")      
        (switchPage))
      (do
        (js/alert "Please enter a valid value")
        (.focus reading)
        (set! (.-value reading) "100")))))



