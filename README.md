
# Content editable component

[Reagent](https://reagent-project.github.io/) content editable component.

## Usage

```clojure
(ns demo
  (:require
    [reagent.core :as r]
    [content-editable.core :refer [content-editable]]))

(defn example []
  (let [html (r/atom "Hello <strong>World</strong>!")]
    (fn []
      [:div
       [:h1 "Content editable"]
       [content-editable {:html @html :on-change #(reset! html %)}]
       [:pre @html]])))
```

## Demo

    lein figwheel

## Run tests

    npm install
    lein doo firefox test once