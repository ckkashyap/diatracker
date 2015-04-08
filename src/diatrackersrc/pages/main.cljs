(ns diatrackersrc.pages.main
  (:require
   [diatrackersrc.consts :as consts]
   [diatrackersrc.dom :as dom]
   [diatrackersrc.storage :as store]
  ))


(defn html [] (str 
"        <div id=\"main\" class=\"fullPageVisible\">"
"	<div class=\"horizontalList\">"
"	  <button onclick=\"diatrackersrc.core.showReports()\">"
"	    Reports"
"	  </button>"
"	  <button onclick=\"diatrackersrc.navigate.showHistory()\">"
"	    History"
"	  </button>"
"	  <button onclick=\"diatrackersrc.core.showGraphs()\">"
"	    Graphs"
"	  </button>"
"	</div>"
""
"	<div class=\"horizontalList\">"
"	  <button class=\"bigButton\" onclick=\"location.href='#adddata'\">"
"	    + Add"
"	  </button>"
"	</div>"
"	<div class=\"horizontalList\">"
"	  <button class=\"bigButton\" onclick=\"location.href='#settings'\">"
"	    Settings"
"	  </button>"
"	</div>"
"	<div class=\"horizontalList\">"
"	  <button class=\"bigButton\" onclick=\"diatrackersrc.core.setDummyData()\">"
"	    DummyData"
"	  </button>"
"	</div>"
"      </div>"))


(defn initPage []
  (let [
        _ (set! (.-innerHTML (dom/getElementById "fullscreen")) (html))
        ]
  (println "init main page")))
