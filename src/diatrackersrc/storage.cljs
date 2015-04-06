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


(defn readLastNRecords [n]
  (let
      [
       storedMaxID (.getItem  js/localStorage "maxID")
       startID (- storedMaxID 1)
       _ (println startID)
       ]
  (loop [i n id startID v []]
    (if (or (= i 0) (< id 0))
      (do
        (println v)
        v)
      (let [
            key (str "reading" id)
            val (js->clj (js/JSON.parse (.getItem js/localStorage key)))
            ]
        
        (recur (dec i) (dec id) (conj v val)))))))
  
