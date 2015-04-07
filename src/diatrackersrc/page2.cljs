(ns diatrackersrc.page2
  (:require
   [diatrackersrc.consts :as consts]
   [diatrackersrc.dom :as dom]
   [diatrackersrc.storage :as store]
  ))


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
        (store/persistRecord monthVal dayVal yearVal foodVal readingIntVal typeVal commentsVal )
        (println commentsVal)
        (set! (.-value reading) "100")
        (set! (.-value comments) "")      
        true)
      (do
        ;REMOVE
        (dom/getListItems "page2FoodList")
        (dom/addListItem "page2FoodList" "hello world")
        (dom/getListItems "page2FoodList")
        ;ENDREMOVE
        (js/alert "Please enter a valid value")
        false))))
