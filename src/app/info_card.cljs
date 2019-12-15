(ns app.info-card
  (:require [reagent.core :as r]))

(defn is-image [source]
  (or (clojure.string/ends-with? source "jpg")
      (clojure.string/ends-with? source "png")))

(defn img-url [item]
  (if (is-image (get-in item ["url"]))
    (get-in item ["url"])
    "/images/placeholder.png"))

(defn info-card [item handle-item-click]
  [:div.w-100.w-33-m.w-20-l.card
   {:on-click (fn [e] (.preventDefault e) (handle-item-click item))
    :style {:background-image (str "url(" (img-url item) ")")}}])
