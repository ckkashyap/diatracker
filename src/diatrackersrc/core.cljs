(ns diatrackersrc.core
  (:require
   [diatrackersrc.consts :as consts]
   [diatrackersrc.dom :as dom]
   [diatrackersrc.storage :as store]
   [diatrackersrc.page2 :as page2]
   [diatrackersrc.navigate :as navigate]
   [diatrackersrc.newreadingtype :as newReadingType]
  ))

(enable-console-print!)

(defn ^:export saveDataAndSwitch []
  (if (page2/saveData)
    (navigate/gotoPage "page2" "page1")))

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
        (store/persistRecord m d y f v t "hello")
      ))))



(navigate/gotoPage "page1" "page1")
