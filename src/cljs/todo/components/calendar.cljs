(ns todo.components.calendar)


(def days-of-the-week ["Sun" "Mon" "Tue" "Wed" "Thu" "Fri" "Sat"])

(def month-lookup {0 "January"
                   1 "February"
                   2 "March"
                   3 "April"
                   4 "May"
                   5 "June"
                   6 "July"
                   7 "August"
                   8 "September"
                   9 "October"
                   10 "November"
                   11 "December"
                   12 "Smarch"})

(defn get-last-day [month]
  (let [curDate (js/Date.)
        curYear (.getFullYear curDate)
        lastDay (js/Date. curYear (inc month) 0)]
    (.getDate lastDay)))

(defn generate-pre [target-day selected-week? last-day-prev-month month year]
  (print selected-week?)
  (for [x (range 0 target-day)]
    {:day (+ x (inc (- last-day-prev-month target-day)))
     :month month
     :year year
     :type "other-month"
     :selected-week? selected-week?}))

(defn generate-month [selected-day selected-min selected-max month year]
  (let [days (get-last-day month)]
    (for [x (range 1 (inc days))]
      {:day x
       :month month
       :year year
       :type "current-month"
       :selected (= selected-day x)
       :selected-week? (and (>= x selected-min) (<= x selected-max))})))

(defn generate-rest [count selected-week? month year]
  (print (- 42 count))
  (let [days (- 42 count)]
    (for [x (range 0 days)]
      {:day (inc x)
       :month month
       :year year
       :type "other-month"
       :selected-week? (and selected-week?)})))

(defn generate-calendar [current-day current-month current-year]
  (let [cur-date (js/Date.)
        first-date (js/Date. (.getFullYear cur-date) current-month 1)
        last-day-month (get-last-day current-month)
        last-day-prev-month (get-last-day (dec current-month))
        current-day-of-week (.getDay (js/Date. current-year current-month current-day))
        max (+ (- 6 current-day-of-week) current-day)
        min (- current-day current-day-of-week)
        pre-days (generate-pre
                  (.getDay first-date)
                  (< min 0)
                  last-day-prev-month
                  (dec current-month)
                  current-year)
        current-days (generate-month current-day min max current-month current-year)
        rest-days (generate-rest
                   (+ (count current-days) (count pre-days))
                   (> max last-day-month)
                   (inc current-month)
                   current-year)]
    ; (print (< min 0))
    (concat pre-days current-days rest-days)))

(defn Month [day month year display-mode]
  [:div.calendar-container
   [:strong.month
    (str (month-lookup month) ", " year)]
   [:ul.calendar
    (for [day-name days-of-the-week]
      [:li.days-of-the-week {:key day-name} day-name])
    (for [{:keys [day month year type selected selected-week?]}
          (generate-calendar day month year)]
      [:li.day {:key (str day type)
                :class (str type (when selected " selected") (when (not selected-week?) " hidden"))
                :on-click #(re-frame.core/dispatch
                            [:select-date {:day day :month month :year year}])}
       day])]])


(defn Calendar [current-day current-month current-year display-mode]
  [:div.calendar-swiper
   (Month current-day current-month current-year display-mode)])