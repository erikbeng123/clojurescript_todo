(ns todo.components.bottom)

(defn bar [show-button]
  [:nav
   (if (true? show-button) [:button "wow"] nil)
   [:ul {:class "menu-list"}
    [:li
     [:a "Data"]]
    [:li
     [:a "Settings"]]]])