(ns lazy-table-examples.views
  (:require [re-frame.core :as re-frame]
            [lazy-table.core :refer [lazy-table]]
            [reagent.core :as reagent]))

(def scroll-value (reagent/atom "Not scrolled"))


(defn basic-table-body [row-count col-count]
  (let [data (re-frame/subscribe [:table-data])]
    (fn [row-count col-count]
      [:table>tbody
       (doall
         (for [row (take @row-count @data)]
           [:tr
            (for [cell (take @col-count row)]
              [:td cell])]))])))

(defn search-table-body [row-count col-count]
  (let [data (re-frame/subscribe [:table-data-with-search])]
    (fn [row-count col-count]
      [:table>tbody
       (doall
         (for [row (take @row-count @data)]
           [:tr
            (for [cell (take @col-count row)]
              [:td cell])]))])))

(defn basic-list [row-count col-count]
  (let [data (re-frame/subscribe [:table-data])]
    (fn [row-count col-count]
      [:ul
       (doall
         (for [row (take @row-count @data)]
           [:li
            (clojure.string/join ", " (take @col-count row))]))])))

(defn main-panel []
  (let [search-value (re-frame/subscribe [:search-value])]
    (fn []
      [:div
       [:h1 "Basic table"]
       [:pre "[lazy-table\n    {:table-fn       basic-table-body\n     :min-row-height 20\n     :min-col-width  15}]"]
       [lazy-table
        {:table-fn       basic-table-body
         :min-row-height 20
         :min-col-width  15}]

       [:h1 "Basic list"]
       [:pre "[lazy-table\n        {:table-fn basic-list\n         :min-row-height 15\n         :min-col-width 20}]"]
       [lazy-table
        {:table-fn basic-list
         :min-row-height 15
         :min-col-width 20}]

       [:h1 "Basic Table With Search"]
       [:p "Here you can see scroll reset with :component-will-receive-props lifecycle element. If you set :reset-scroll-on-props-change? to false it will not reset. So props will change if search results are empty or outside of current scroll range. You can experiment for various results."]
       [:pre "[lazy-table\n    {:table-fn       search-table-body\n     :min-row-height 20\n     :min-col-width  15}]"]
       [:label "Search"]
       [:input
        {:type      "text"
         :value     @search-value
         :on-change (fn [e]
                      (re-frame/dispatch [:set-search-value (.-value (.-target e))]))}]
       [lazy-table
        {:table-fn       search-table-body
         :min-row-height 20
         :min-col-width  15}]

       [:h1 "Custom Scroll Event Table"]
       [:pre "[lazy-table\n    {:table-fn             basic-table-body\n     :min-row-height       20\n     :min-col-width        15\n     :additional-scroll-fn (fn [] \n                             (reset! scroll-value \n                                     (str \"Scrolled! You get a super special lucky number: \" \n                                          (rand-int 100))))}]"]
       [:p "Scroll value: " @scroll-value]
       [lazy-table
        {:table-fn             basic-table-body
         :min-row-height       20
         :min-col-width        15
         :additional-scroll-fn (fn []
                                 (reset! scroll-value
                                         (str "Scrolled! You get a super special lucky number: "
                                              (rand-int 100))))}]

       [:h1 "Super Complex Table"]
       ;todo
       [:p "Todo"]
       [:p "You can have multiple complex tables (e.g. lets say you want sticky headers) together, for example"]
       [:pre "[lazy-table\n        {:table-fn [(fn [row-count col-count]\n                      [sticky-head row-count col-count])\n                    (fn [row-count col-count]\n                      [table-body row-count col-count])]}]"]])))
