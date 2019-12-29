(ns app.core
  (:require [reagent.core :as r]
            [ajax.core :refer [GET]]
            [app.info-card :refer [info-card]]
            [app.helpers :as helpers]))

(def state (r/atom {:results []
                    :item []
                    :show-explanation false}))

(defn handle-item-click [item]
  (swap! state assoc :show-explanation true)
  (swap! state assoc :item item))

(defn handle-overlay-click []
  (swap! state assoc :show-explanation false)
  (swap! state assoc :item []))

(defn results-with-image [item]
  (helpers/is-image (get-in item ["url"])))

(defn get-results []
  (GET "apods.json" :handler (fn [data] (swap! state update-in [:results] #(filter results-with-image data)))))

(defn overlay []
  [:div#overlay
   {:on-click (fn [e] (handle-overlay-click))
    :style {:background-image (str "url(" (get-in (:item @state) ["hdurl"]) ")")}}
   [:h4.f4.white (get-in (:item @state) ["title"])]
   [:div#text (get-in (:item @state) ["explanation"])]])

(defn cards []
  [:div.article.bg-black
   [:div.cf.flex.flex-wrap
    (for [item (:results @state)]
         ^{:key (get-in item ["title"])}
         [info-card item handle-item-click])]])

(defn app []
  [:div.app
   (when (:show-explanation @state) [overlay])
   [cards]])

(defn ^:dev/after-load start []
  (get-results)
  (r/render [app] (.getElementById js/document "app")))

(defn ^:export main []
  (start))
