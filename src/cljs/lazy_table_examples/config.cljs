(ns lazy-table-examples.config)

(def debug?
  ^boolean js/goog.DEBUG)

(when debug?
  (enable-console-print!))
