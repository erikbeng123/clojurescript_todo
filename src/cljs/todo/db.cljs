(ns todo.db)

(def store
  (let [cur-date (js/Date.)]
    {:cur-date cur-date
     :selected-date cur-date
     :todo-data-cur []
     :todo-data {}
     :add-form-step 0
     :add-form-data {:title "" :type "" :frequency ""}}))
