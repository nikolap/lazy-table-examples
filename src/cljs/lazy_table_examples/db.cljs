(ns lazy-table-examples.db)

(def default-db
  {:table-data (repeatedly 100
                           (fn []
                             (repeatedly 100
                                         (fn [] (rand-int 100)))))})
