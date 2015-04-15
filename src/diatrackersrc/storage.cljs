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
  

(defn deletedEntryName [table recordID]
  (str table :deleted recordID))

(defn delete [table recordID]
  (setItem (deletedEntryName table recordID) true))

(defn isRecordDeleted [table id]
  (not (getItem (deletedEntryName table id))))

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
  (println (str "fetchRecord called with " table ", " recordID ", "placeHolder))
  (if (isRecordDeleted table recordID)
    (reduce-kv (fn [m k _] 
                 (let [
                       col (str table k recordID)
                       v (getItem col)
                       ] (assoc m k v)))  {:id recordID} placeHolder )))
  

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
  

(defn fetchAll [table r]
  (let
      [
       maxID (getMaxID table)
       startID (- maxID 1)
       ]
  (loop [id startID v []]
    (if (< id 0)
      v
      (let [
            record (fetchRecord table id r)
            newVector (if record (conj v record) v)
            ]
        (recur (dec id) newVector))))))  


