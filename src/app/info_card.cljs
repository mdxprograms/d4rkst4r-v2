(ns app.info-card
  (:require [reagent.core :as r]))

(defn is-image [source]
  (or (clojure.string/ends-with? source "jpg")
      (clojure.string/ends-with? source "png")))

(def bg-colors ["lightblue" "tomato" "lightgreen" "violet" "lightcyan" "skyblue" "#ccc" "#777" "gold"])

(defn info-card [item handle-item-click]
  [:div.w-100.w-50-m.w-25-l.pa3.card
   {:on-click (fn [e] (.preventDefault e) (handle-item-click item))
    :style {:background (rand-nth bg-colors)}}
   [:img.db.w-100.br2.br--top.shadow-1
    {:alt (get-in item ["title"])
     :src (if (is-image (get-in item ["url"])) (get-in item ["url"]) "/images/placeholder.png")}]
   [:div.pa2.ph3-ns.pb3-ns
    [:div.dt.w-100.mt1
     [:div.dtc [:h4.f4.mv0 (get-in item ["title"])]]]]])
