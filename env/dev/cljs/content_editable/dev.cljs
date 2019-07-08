(ns ^:figwheel-no-load content-editable.dev
  (:require
    [content-editable.demo :as demo]
    [devtools.core :as devtools]))

(extend-protocol IPrintWithWriter
  js/Symbol
  (-pr-writer [sym writer _]
    (-write writer (str "\"" (.toString sym) "\""))))

(enable-console-print!)

(devtools/install!)

(demo/init!)
