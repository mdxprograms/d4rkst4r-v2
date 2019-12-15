(ns app.core
  (:require [reagent.core :as r]
            [ajax.core :refer [GET]]
            [app.info-card :refer [info-card]]))

(defonce results (r/atom nil))
(def explanation (r/atom nil))
(def hdurl (r/atom ""))
(def title (r/atom ""))
(def show-explanation (r/atom false))

(defn handle-item-click [item]
  (reset! show-explanation true)
  (reset! explanation (get-in item ["explanation"]))
  (reset! title (get-in item ["title"]))
  (reset! hdurl (get-in item ["hdurl"])))

(defn handle-overlay-click []
  (reset! show-explanation false)
  (reset! explanation "")
  (reset! title "")
  (reset! hdurl ""))

(defn get-results []
  (GET "apods.json" :handler (fn [r] (reset! results r))))

(defn overlay []
  [:div#overlay
   {:on-click (fn [e] (handle-overlay-click))
    :style {:background-image (str "url(" @hdurl ")")}}
   [:h4.f4.white @title]
   [:div#text @explanation]])

(defn cards []
  [:div.article.bg-black
   [:div.cf.flex.flex-wrap
    (for [item @results]
      ^{:key (get-in item ["title"])}
      [info-card item handle-item-click])]])

  (defn app []
    [:div.app
     (when @show-explanation [overlay])
     [cards]])


(defn ^:dev/after-load start []
  (get-results)
  (r/render [app] (.getElementById js/document "app")))

(defn ^:export main []
  (start))
