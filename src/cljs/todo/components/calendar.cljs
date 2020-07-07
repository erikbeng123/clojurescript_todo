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

(defn generate-pre [target-day last-day-prev-month month year]
  (for [x (range 0 target-day)]
    {:day (+ x (inc (- last-day-prev-month target-day)))
     :month month
     :year year
     :type "other-month"}))

(defn generate-month [selected-day month year]
  (let [days (get-last-day month)]
    (for [x (range 1 (inc days))]
      {:day x
       :month month
       :year year
       :type "current-month"
       :selected (= selected-day x)})))

(defn generate-rest [count month year]
  (for [x (range 0 (- 42 count))]
    {:day (inc x)
     :month month
     :year year
     :type "other-month"}))

(defn generate-calendar [current-day current-month current-year]
  (let [cur-date (js/Date.)
        first-date (js/Date. (.getFullYear cur-date) current-month 1)
        last-day-prev-month (get-last-day (dec current-month))
        pre-days (generate-pre
                  (.getDay first-date)
                  last-day-prev-month
                  (dec current-month)
                  current-year)
        current-days (generate-month current-day current-month current-year)
        rest-days (generate-rest
                   (+ (count current-days) (count pre-days))
                   (inc current-month)
                   current-year)]
    (concat pre-days current-days rest-days)))

(defn Month [day month year]
  [:div.calendar-container
   [:strong.month
    (str (month-lookup month) ", " year)]
   [:ul.calendar
    (for [day-name days-of-the-week]
      [:li.days-of-the-week {:key day-name} day-name])
    (for [{:keys [day month year type selected]}
          (generate-calendar day month year)]
      [:li.day {:key (str day type)
                :class (str type (when selected " selected"))
                :on-click #(re-frame.core/dispatch
                            [:select-date {:day day :month month :year year}])}
       day])]])


(defn Calendar [current-day current-month current-year]
  [:div.calendar-swiper {:on-scroll #(print "scroll")}
   (Month current-day (dec current-month) current-year)
   (Month current-day current-month current-year)
   (Month current-day (inc current-month) current-year)])