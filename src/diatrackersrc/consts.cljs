(ns diatrackersrc.consts)


(def months
  [
   "Jan" "Feb" "Mar"
   "Apr" "May" "Jun"
   "Jul" "Aug" "Sep"
   "Oct" "Nov" "Dec"
])

(def meals
  [
   "Before Breakfast"
   "After Breakfast"
   "Before Lunch"
   "After Lunch"
   "After Snacks"
   "Before Snacks"
   "Before Dinner"
   "After Dinner"
   ])

(def types 
  [
   "Sugar"
   "BP"
   "Temperature"
   "Weight"
   "Other"
   ])



(def dataRecord {:max "recordMaxID" :prefix "record"})
(def recordType {:max "recordTypeMaxID" :prefix "type"})
(def timeOfDay {:max "timeOfDayMaxID" :prefix "time"})

