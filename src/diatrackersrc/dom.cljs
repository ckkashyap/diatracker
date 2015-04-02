(ns diatrackersrc.dom)

(defn getElementById [id]
   (.getElementById js/document id))
             


(defn addListItem [selectID, itemText]
  (let [
        sl (getElementById selectID)
        el (.createElement js/document "option")
        ] 
    (set! (.-text el) itemText)
    (.add (.-options sl) el)))



(defn getListItems [selectID]
  (let [
        sl (getElementById selectID)
        l (.-length sl)
        ] 
    (loop [i 0 o []]
      (if (= i l)
        o
        (let [
              el (.-value (aget (.-options sl) i))
              ]
          (recur (inc i) (conj o el)))))))
