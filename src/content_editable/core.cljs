(ns content-editable.core
  (:require
   [reagent.core :as r]))

(defn text-node? [node]
  (= (.-nodeType node) js/Node.TEXT_NODE))

(defn last-node [node-list]
  (let [last-index (-> node-list .-length dec)]
    (aget node-list last-index)))

(defn last-text-node [node]
  (if (text-node? node)
    node
    (loop [children (.-childNodes node)
           index (dec (.-length children))]
      (when (>= index 0)
        (if-let [text-node (last-text-node (aget children index))]
          text-node
          (recur children (dec index)))))))

(defn replace-caret [el]
  (let [target (last-text-node el)
        target-focused? (= (.-activeElement js/document) el)]
    (if (and
         (not= target nil)
         (not= (.-nodeValue target) nil)
         target-focused?)
      (let [range (.createRange js/document)
            sel (.getSelection js/window)]
        (.setStart range target (.. target -nodeValue -length))
        (.collapse range true)
        (.removeAllRanges sel)
        (.addRange sel range)
        (if (= (type el) js/HTMLElement)
          (.focus el))))))

(defn content-editable [{:keys [on-change]}]
  (let [!el (atom nil)
        emit-change (fn [event]
                      (when-let [el @!el]
                        (let [html (.-innerHTML el)]
                          (if on-change
                            (on-change html)))))]
    (r/create-class
     {:display-name "content-editable"

      :component-did-update
      (fn [this]
        (when-let [el @!el]
          (let [html (:html (r/props this))]
            (if (not= html (.-innerHTML el))
              (-> el .-innerHTML (set! html)))
            (replace-caret el))))

      :should-component-update
      (fn [this [_ {old-html :html}] [_ {new-html :html}]]
        (or (not @!el)
            (not= new-html (.-innerHTML @!el))))

      :reagent-render
      (fn [{:keys [html]}]
        [:div.box
         {:ref (fn [com] (reset! !el com))
          :contentEditable true
          :on-input emit-change
          :on-blur emit-change
          :on-key-down emit-change
          :dangerouslySetInnerHTML {:__html html}}])})))