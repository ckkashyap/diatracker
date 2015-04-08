(ns diatrackersrc.pages.settings
  (:require
   [diatrackersrc.consts :as consts]
   [diatrackersrc.dom :as dom]
   [diatrackersrc.storage :as store]
  ))


(defn html [] (str 
"        <div id=\"settings\" class=\"fullPageVisible\">"
"	<div class=\"horizontalList\">"
"	  <button class=\"bigButton\" onclick=\"location.href='#main'\">"
"            Back"
"	  </button>"
"	</div>"
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
"      </div>"))



(defn initPage [page]
  (let [
        _ (set! (.-innerHTML (dom/getElementById "fullscreen")) (html))
        ]
  (println "init settings page " page)))
