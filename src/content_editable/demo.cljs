(ns content-editable.demo
  (:require
   [reagent.core :as r]
   [content-editable.core :as core]))

(def original-html "Hello <strong>World</strong>!")

(defn home-page []
  (let [html (r/atom original-html)]
    (fn []
      [:div.content-editable
       [:h1 "Content editable"]
       [core/content-editable {:html @html :on-change #(reset! html %)}]
       [:button {:on-click #(reset! html original-html)} "Reset"]
       [:pre @html]])))

(defn mount-root []
  (r/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
