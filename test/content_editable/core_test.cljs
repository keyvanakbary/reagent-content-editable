(ns content-editable.core-test
  (:require
   [cljs.test :refer-macros [is are deftest testing use-fixtures]]
   [reagent.core :as reagent :refer [atom]]
   [content-editable.core :as core]
   [hipo.core :as h]))

(deftest detects-text-node
  (testing "text node"
    (is (core/text-node? (h/create "texdt"))))
  (testing "non text node"
    (is (not (core/text-node? (h/create [:div "text"]))))))

(defn text [node]
  (.-nodeValue node))

(defn child-nodes [el]
  (.-childNodes el))

(deftest selects-last-node
  (testing "simple element"
    (is (= (-> (child-nodes
                (h/create [:div "last"]))
               (core/last-node)
               (text))
           "last")))
  (testing "nested element"
    (is (= (-> (child-nodes
                (h/create [:div
                           [:span "first"]
                           "last"]))
               (core/last-node)
               (text))
           "last"))))

(deftest finds-last-text-node
  (testing "simple element"
    (is (= (-> (h/create [:div "last"])
               (core/last-text-node)
               (text))
           "last")))
  (testing "nested element"
    (is (= (-> (h/create [:div
                          [:span "first"]
                          [:span "last"]])
               (core/last-text-node)
               (text))
           "last")))

  (testing "empty element"
    (is (= (-> (h/create [:div])
               (core/last-text-node))
           nil)))
  (testing "empty nested element"
    (is (= (-> (h/create [:div [:br]])
               (core/last-text-node))
           nil)))
  (testing "double nested element"
    (is (= (-> (h/create [:div
                          "text"
                          [:br]])
               (core/last-text-node)
               (text))
           "text")))
  (testing "complex nesting"
    (is (= (-> (h/create [:div
                          "text"
                          [:br]
                          [:div
                           "some text"
                           [:span
                            "last"
                            [:br]]]])
               (core/last-text-node)
               (text))
           "last"))))

(deftest comparing-dom-nodes
  (testing "simple element"
    (let [e1 (h/create [:div])
          e2 (h/create [:div])]
      (is (= e1 e1))
      (is (not= e1 e2)))))