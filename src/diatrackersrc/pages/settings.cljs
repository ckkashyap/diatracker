(ns diatrackersrc.pages.settings
  (:require
   [diatrackersrc.consts :as consts]
   [diatrackersrc.dom :as dom]
   [clojure.string :as string]   
   [diatrackersrc.storage :as store]
  ))



  

(defn backButton [to] (str
"	<div class=\"horizontalList\">"
"	  <button class=\"bigButton\" onclick=\"location.href='#" to "'\">"
"            Back"
"	  </button>"
"	</div>"))


(defn editPage [type] (str
"      <div id=\"readingType\" class=\"fullPageVisible\">"
"	<div class=\"horizontalList\">"
"<div class=\"reading\">"
(case type
"type" "Category"
"time" "Time of Day")  
"</div>"
"	</div>"
"	<div class=\"horizontalList\">"
"	  <select id=\"settingsList\" class=\"reading\">"
(string/join (map #(str "<option value=\"" (:id %) "\">" (:v %) "</option>") (store/fetchAll type {:v true})))
"	  </select>"
"	</div>"
"	<div class=\"horizontalList\">"
"	  <button class=\"bigButton\" onclick=\"diatrackersrc.pages.settings.removeItem('" type "')\">"
"            Delete existing"
"	  </button>"
"	</div>"
"	<div class=\"horizontalList\">"
"	  <input id=\"newItem\" class=\"reading\" placeholder=\"Enter new type\"/>"
"	</div>"
"	<div class=\"horizontalList\">"
"	  <button class=\"bigButton\" onclick=\"diatrackersrc.pages.settings.addType('" type "')\">"
"            Add"
"	  </button>"
"	</div>"
(backButton "settings")
"      </div>"))


(defn html [type] (case type

"settings" (str 
"        <div id=\"settings\" class=\"fullPageVisible\">"
(backButton "main")
"	<div class=\"horizontalList\">"
"	  <button class=\"bigButton\" onclick=\"location.href='#settingsType'\">"
"            Reading type"
"	  </button>"
"	</div>"
"	<div class=\"horizontalList\">"
"	  <button class=\"bigButton\" onclick=\"location.href='#settingsTime'\">"
"            Time of day"
"	  </button>"
"	</div>"
"      </div>")

"settingsType" (do  (editPage "type"))
"type" (do  (editPage "type"))

"settingsTime" (editPage "time")
"time" (editPage "time")

))



(defn initPage [page]
  (let [
        _ (set! (.-innerHTML (dom/getElementById "fullscreen")) (html page))
        ]
  (println "init settings page " page)))

(defn ^:export addType [t]
  (let [
        value (dom/getElementValById "newItem")
        ]
    (if (not (= value ""))
      (do
        (store/insert t {:v value})
        (initPage t)))))


(defn ^:export removeItem [type]
  (let [
        id (dom/getValFromList "settingsList")
        ]
  (store/delete type id)
  (initPage type)))
