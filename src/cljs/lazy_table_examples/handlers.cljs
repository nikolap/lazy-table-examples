(ns lazy-table-examples.handlers
    (:require [re-frame.core :as re-frame]
              [lazy-table-examples.db :as db]))

(re-frame/register-handler
 :initialize-db
 (fn  [_ _]
   db/default-db))

(re-frame/register-handler
  :set-search-value
  (fn [db [_ value]]
    (assoc db :search-value value)))
