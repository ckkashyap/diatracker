(ns diatrackersrc.pages.adddata
  (:require
   [diatrackersrc.consts :as consts]
   [diatrackersrc.dom :as dom]
   [clojure.string :as string]
   [diatrackersrc.storage :as store]
  ))


(defn html [timeList typeList] (str
"        <div id=\"main\" class=\"fullPageVisible\">"
"	<div class=\"horizontalList\">"
"	  <div>"
"	    <select id=\"adddataMonthList\" class=\"date\">"
(string/join (map #(str "<option>" % "</option>") consts/months))
"	    </select>"
"	  </div>"
"	  <div>"
"	    <select id=\"adddataDayList\" class=\"date\">"
(string/join (map #(str "<option>" (if (< % 10) (str "0" %)  %) "</option>") (range 1 31)))
"	    </select>"
"	  </div>"
"	  <div>"
"	    <input id=\"adddataYear\" type=\"text\" class=\"date\">"
"	  </div>"
"	</div>"
"	<div class=\"horizontalList\">"
"	  <select id=\"adddataFoodList\" class=\"reading\">"
(string/join (map #(str "<option>" % "</option>") timeList))
"	  </select>"
"	</div>"
"	<div class=\"horizontalList\">"
"	  <select id=\"adddataTypeList\" class=\"reading\">"
(string/join (map #(str "<option>" % "</option>") typeList))
"	  </select>"
"	</div>"
""
"	<div class=\"horizontalList\">"
"	  <input id=\"adddataReading\" type=\"number\" class=\"reading\" value=\"100\" placeholder=\"Enter reading\"/>"
"	</div>"
"	<div class=\"horizontalList\">"
"	  <textarea id=\"adddataComments\" class=\"reading\" placeholder=\"Enter comments here\"></textarea>"
"	</div>"
""
"	<div class=\"horizontalList\">"
"	  <button class=\"bigButton\" onclick=\"diatrackersrc.core.saveDataAndSwitch()\">"
"	    save"
"	  </button>"
"	  <button class=\"bigButton\" onclick=\"diatrackersrc.adddata.saveData()\">"
"	    Add another"
"	  </button>"
"	</div>"
"	</div>"))



(defn initPage []
(do
  (set! (.-href js/location) "#adddata")
  (let [
        _ (set! (.-innerHTML (dom/getElementById "fullscreen")) (html ["A"] ["B"]))
        dt (js/Date.)
        m (.getMonth dt)
        y (+ 1900 (.getYear dt))
        d (- (.getDate dt) 1)
        ml (dom/getElementById "adddataMonthList")
        dl (dom/getElementById "adddataDayList")
        yt (dom/getElementById "adddataYear")
        ]
    (set! (.-selectedIndex ml) m)
    (set! (.-selectedIndex dl) d)
    (set! (.-value yt) y)
    (println d)
    (println m (+ 1900 y) d)
    )
  (println "Initializing page 2")))


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
