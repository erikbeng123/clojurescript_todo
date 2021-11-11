(ns todo.pages.todos-page
  (:require [todo.components.add-form :refer [Add-form]]
            [todo.components.calendar :refer [Calendar]]))


(defn Drawer [todos display-mode]
  [:div.drawer
   [:div.drawer-head
    [:span.handle]]
   [:ul.todo-list
    (if
     (empty? todos)
      [:div.no-todos "No todos for today"]
      (for [{:keys [id title type complete?]} todos]
        [:div.todo {:key id :class (when complete? "complete")}
         [:button.done-btn
          {:on-click
           #(re-frame.core/dispatch
             [:toggle-todo
              {:id id
               :status (not complete?)}])}]
         [:div.todo-txt
          [:span title]
          [:small type]]]))]])

(defn Todos [current-date todo-data add-form-step]
  [:div.todos
   (if
    (> add-form-step 0)
     (Add-form
      @(re-frame.core/subscribe [:add-form-data])))
   (Calendar
    (.getDate current-date)
    (.getMonth current-date)
    (.getFullYear current-date)
    "full-calendar")
   (Drawer todo-data "full-calendar")
   [:button.add-btn
    {:on-click #(re-frame.core/dispatch [:set-add-form-step {:step 1}])}
    [:span "+"]]])