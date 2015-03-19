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


