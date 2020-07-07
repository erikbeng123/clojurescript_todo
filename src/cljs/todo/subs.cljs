(ns todo.subs
  (:require
   [re-frame.core :as re-frame]))

(re-frame/reg-sub
 :todo-data-cur
 (fn [db]
   (:todo-data-cur db)))

(re-frame/reg-sub
 :add-form-step
 (fn [db]
   (:add-form-step db)))

(re-frame/reg-sub
 :add-form-data
 (fn [db]
   (:add-form-data db)))

(re-frame/reg-sub
 :selected-date
 (fn [db]
   (:selected-date db)))

(re-frame/reg-sub
 :cur-date
 (fn [db]
   (:cur-date db)))

(re-frame/reg-sub
 :todo-data-cur
 (fn [db]
   (let [todo-data (:todo-data db)
         cur-data (get todo-data (:selected-date db))]
     (print (:selected-date db))
     cur-data)))