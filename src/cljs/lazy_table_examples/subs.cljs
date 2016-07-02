(ns lazy-table-examples.subs
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :as re-frame]))

(re-frame/register-sub
  :table-data
  (fn [db]
    (reaction (:table-data @db))))

(re-frame/register-sub
  :search-value
  (fn [db]
    (reaction (:search-value @db))))

(re-frame/register-sub
  :table-data-with-search
  (fn [db _]
    (let [search-value (re-frame/subscribe [:search-value])]
      (reaction (let [data (:table-data @db)
                      search-val (js/parseInt @search-value)]
                  (if (js/isNaN search-val)
                    data
                    (map
                      (fn [row]
                        (filter #(= search-val %) row))
                      (filter #(contains? (set %) search-val) data))))))))
