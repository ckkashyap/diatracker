(ns diatrackersrc.storage
  (:require
   [diatrackersrc.consts :as cnst]))

(defn getItem [k]
  (.getItem  js/localStorage k))

(defn setItem [k v]
  (.setItem js/localStorage k v))

(defn clear []
  (.clear js/localStorage))


(defn clj->json
  [i]
  (.stringify js/JSON (clj->js i)))

(defn getMaxID [table]
  (getItem (str table "MaxID")))
  

(defn delete [table recordID]
  (setItem (str table :delted recordID) true))

(defn insert [table record]
  (let [
        maxIDKey (str table "MaxID")
        storedMaxID (getItem maxIDKey)
        maxID (js/parseInt (if storedMaxID storedMaxID "0"))
        _  (setItem maxIDKey (+ 1 maxID))
        ]
    (doseq [[k v] record]
      (let [col (str table k maxID)]
        (setItem col v)))))


(defn fetchRecord [table recordID placeHolder]
  (reduce-kv (fn [m k _] 
               (let [
                     col (str table k recordID)
                     v (getItem col)
                     ] (println k "->" v col  m) (assoc m k v)))  {} placeHolder ))
        

(defn persistRecord [m d y f v t c];month date year food value type  comment
  (let
      [ 
       r  { :m m :d d :y y :f f :v v :t t :c c}
       ]
    (insert "table" r)))


(defn readLastNRecords [table n r]
  (let
      [
       maxID (getMaxID table)
       _ (println "Max ID " maxID)
       startID (- maxID 1)
       ]
  (loop [i n id startID v []]
    (if (or (= i 0) (< id 0))
      (do
        (println v)
        v)
        (recur (dec i) (dec id) (conj v (fetchRecord table id r)))))))
  

;(defn fetchAll [table r]
;  (let
;      [
;       maxID (getMaxID table)
;       _ (println "Max ID " maxID)
;       startID (- maxID 1)
;       ]
;  (loop [id startID v []]
;    (if (or (= i 0) (< id 0))
;      (do
;        (println v)
;        v)
;        (recur (dec i) (dec id) (conj v (fetchRecord table id r)))))))
  
