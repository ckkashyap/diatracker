(ns diatrackersrc.dom)

(defn getElementById [id]
   (.getElementById js/document id))
             
