(ns lazy-table-examples.core
    (:require [reagent.core :as reagent]
              [re-frame.core :as re-frame]
              [devtools.core :as devtools]
              [lazy-table-examples.handlers]
              [lazy-table-examples.subs]
              [lazy-table-examples.views :as views]
              [lazy-table-examples.config :as config]))


(defn dev-setup []
  (when config/debug?
    (println "dev mode")
    (devtools/install!)))

(defn mount-root []
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (re-frame/dispatch-sync [:initialize-db])
  (dev-setup)
  (mount-root))
