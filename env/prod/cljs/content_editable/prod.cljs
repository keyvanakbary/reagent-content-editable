(ns content-editable.prod
  (:require
    [content-editable.demo :as demo]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(demo/init!)
