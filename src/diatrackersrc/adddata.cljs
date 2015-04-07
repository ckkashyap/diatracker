(ns diatrackersrc.adddata
  (:require
   [diatrackersrc.consts :as consts]
   [diatrackersrc.dom :as dom]
   [diatrackersrc.storage :as store]
  ))


(defn ^:export saveData []
  (let [
        reading (.getElementById js/document "adddataReading")
        readingVal (.-value reading)
        readingParseInt (js/parseInt readingVal)
        readingIntVal (if (js/isNaN readingParseInt) 0 readingParseInt)
        comments (.getElementById js/document "adddataComments")
        commentsVal (.-value comments)
        year (.getElementById js/document "adddataYear")
        yearVal (.-value year)

        getValFromList (fn [n]
                         (let [
                               div (.getElementById js/document n)
                               idx (.-selectedIndex div)
                               val (.-value (aget (.-options div) idx))
                               ] val))


        monthVal (getValFromList "adddataMonthList")
        dayVal (getValFromList "adddataDayList")
        foodVal (getValFromList "adddataFoodList")
        typeVal (getValFromList "adddataTypeList")


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
        (dom/getListItems "adddataFoodList")
        (dom/addListItem "adddataFoodList" "hello world")
        (dom/getListItems "adddataFoodList")
        ;ENDREMOVE
        (js/alert "Please enter a valid value")
        false))))
