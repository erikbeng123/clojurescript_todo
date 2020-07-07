(ns todo.main
  (:require [todo.db :as db]
            [todo.subs :as subs]
            [todo.components.top-bar :as topbar]
            [todo.components.bottom :refer [bar]]
            [todo.pages.todos-page :refer [Todos]]))

(defn Contents [cur-page]
  (case cur-page
    "todos" (Todos
             @(re-frame.core/subscribe [:selected-date])
             @(re-frame.core/subscribe [:todo-data-cur])
             @(re-frame.core/subscribe [:add-form-step]))
    "data" [:div "data!"]
    "settings" [:div "settings!"]
    [:div "ERROR"]))

(defn app []
  [:div {:class "main"}
   [:div {:class "contents"} [Contents "todos"]]])
