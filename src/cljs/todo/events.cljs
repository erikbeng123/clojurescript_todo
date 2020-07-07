(ns todo.events
  (:require
   [re-frame.core :as re-frame]
   [todo.db :as db]))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/store))

(defn select-date [{:keys [db]} [_ {:keys [day month year]}]]
  (let [new-date (js/Date. year month day)]
    {:db (assoc db :selected-date new-date)}))

(re-frame.core/reg-event-fx
 :select-date
 select-date)

(defn update-todo [{:keys [db]} [_ {:keys [id status]}]]
  (let [cur-data (:todo-data db)
        selected-date (:selected-date db)
        cur-todos-by-date (get cur-data (:selected-date db))
        updated-todos
        (map
         (fn [x] (if (= (:id x) id)
                   (assoc-in x [:complete?] status) x))
         cur-todos-by-date)]
    {:db
     (assoc
      db
      :todo-data
      (assoc
       cur-data
       selected-date
       updated-todos))}))

(re-frame.core/reg-event-fx :toggle-todo update-todo)

(defn update-add-form-step [{:keys [db]} [_ {:keys [step]}]]
  (let [tmp
        (if (= step 0)
          (assoc db :add-form-data {:title "" :type "" :frequency ""})
          db)]
    {:db
     (assoc tmp :add-form-step step)}))

(re-frame.core/reg-event-fx :set-add-form-step update-add-form-step)

(defn update-add-form [{:keys [db]} [_ {:keys [form-data]}]]
  {:db (assoc db :add-form-data form-data)})

(re-frame.core/reg-event-fx :update-add-form update-add-form)

(defn update-todo-list [{:keys [db]} [_ {:keys [new-todo]}]]
  (let [todo-data (:todo-data db)
        selected-date (:selected-date db)
        todo-list-by-date (get todo-data selected-date)]
    {:db
     (assoc db :todo-data
            (assoc
             (:todo-data db)
             (:selected-date db)
             (conj
              (if (some? todo-list-by-date)
                todo-list-by-date [])
              (assoc new-todo :id (.now js/Date)))))}))

(re-frame.core/reg-event-fx :update-todo-list update-todo-list)