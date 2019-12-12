(ns app.core
  (:require [reagent.core :as r]
            [ajax.core :refer [GET]]
            [app.info-card :refer [info-card]])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(def explanation (r/atom nil))
(def show-explanation (r/atom false))
(def results (r/atom nil))

(defn handle-item-click [text]
  (reset! show-explanation true)
  (reset! explanation text))

(defn handle-overlay-click []
  (reset! show-explanation false)
  (reset! explanation ""))

(defn overlay []
  [:div#overlay
   {:on-click #(handle-overlay-click)}
   [:div#text @explanation]])

(defn cards []
  (let [get-results (fn [] (GET "apods.json" :handler (fn [r] (reset! results r))))]
    (get-results)
    (fn []
      [:div.article
       [:div.cf.pa2.flex.flex-wrap
        (for [item @results]
          ^{:key (get-in item ["title"])}
          [info-card item handle-item-click])]])))

(defn app []
  [:div.app
   (when @show-explanation [overlay])
   [cards]])

(defn ^:dev/after-load start []
  (r/render [app] (.getElementById js/document "app")))

(defn ^:export main []
  (start))
