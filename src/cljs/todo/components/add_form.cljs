(ns todo.components.add-form)

(defn Add-form [add-form-data]
  (let [{:keys [title type frequency]} add-form-data]
    [:div.add-form
     [:div.form-wrapper
      [:div.form-header
       [:h3 "Create a todo"]]
      [:button.close-add-form-btn
       {:on-click #(re-frame.core/dispatch [:set-add-form-step {:step 0}])} "+"]
      [:ul.form-items
       [:li.form-item
        [:label "Name"]
        [:input {:placeholder "Todo name"
                 :value title
                 :on-change
                 (fn [e]
                   (let [new-form-data (assoc add-form-data :title (.-value (.-target e)))]
                     (re-frame.core/dispatch [:update-add-form {:form-data new-form-data}])))}]]
       [:li.form-item
        [:label "Type"]
        [:select.form-select {:value type
                              :on-change (fn [e] (let [new-form-data (assoc add-form-data :type (.-value (.-target e)))]
                                                   (re-frame.core/dispatch [:update-add-form {:form-data new-form-data}])))}
         [:option {:value "" :disabled true} "Select"]
         [:option {:value "exercise"} "Exercise"]
         [:option {:value "skill"} "Skill"]
         [:option {:value "work"} "Work"]
         [:option {:value "organization"} "Organization"]]]
       [:li.form-item
        [:label "When"]
        [:select.form-select {:value frequency
                              :on-change (fn [e] (let [new-form-data (assoc add-form-data :frequency (.-value (.-target e)))]
                                                   (re-frame.core/dispatch [:update-add-form {:form-data new-form-data}])))}
         [:option {:value "" :disabled true} "Select"]
         [:option {:value "once"}  "One time"]
         [:option {:value "every-day"}  "Every Day"]
         [:option {:value "every-week"}  "Every Week"]
         [:option {:value "every-month"}  "Every Month"]
         [:option {:value "custom"}  "Custom"]]]
       [:button.add-todo-btn {:on-click (fn []
                                          (re-frame.core/dispatch [:update-todo-list {:new-todo add-form-data}])
                                          (re-frame.core/dispatch [:set-add-form-step {:step 0}]))
                              :disabled
                              (or
                               (clojure.string/blank? title)
                               (clojure.string/blank? type)
                               (clojure.string/blank? frequency))} "Add"]]]]))