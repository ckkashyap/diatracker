(ns diatrackersrc.pages.settings
  (:require
   [diatrackersrc.consts :as consts]
   [diatrackersrc.dom :as dom]
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
"	  <select id=\"settingsList\" class=\"reading\">"
"	  </select>"
"	</div>"
"	<div class=\"horizontalList\">"
"	  <button class=\"bigButton\" onclick=\"location.href='#settings'\">"
"            Delete existing"
"	  </button>"
"	</div>"
"	<div class=\"horizontalList\">"
"	  <input id=\"readingTypeNew\" class=\"reading\" placeholder=\"Enter new type\"/>"
"	</div>"
"	<div class=\"horizontalList\">"
"	  <button class=\"bigButton\" onclick=\"diatrackersrc.newreadingtype.addNewType()\">"
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

"settingsType" (editPage "type")

"settingsTime" (editPage "time")

))



(defn initPage [page]
  (let [
        _ (set! (.-innerHTML (dom/getElementById "fullscreen")) (html page))
        ]
  (println "init settings page " page)))
