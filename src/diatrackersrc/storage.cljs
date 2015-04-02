(ns diatrackersrc.storage)

(defn getItem [k]
  (.getItem  js/localStorage k))

(defn setItem [k v]
  (.setItem js/localStorage k v))

(defn clear []
  (.clear js/localStorage))


(defn clj->json
  [i]
  (.stringify js/JSON (clj->js i)))

(defn persistRecord [m d y f v t c];month date year food value type  comment
  (let
      [ 
       r  { :m m :d d :y y :f f :v v :t t :c c}
       j  (clj->json r)

       storedMaxID (getItem "maxID")
       maxID (js/parseInt (if storedMaxID storedMaxID "0"))
       _  (setItem "maxID" (+ 1 maxID))
       key (str "reading" maxID)

       _ (setItem key j)
       
       ]
    (println maxID)))

