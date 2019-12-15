(ns app.info-card
  (:require [reagent.core :as r]
            [app.helpers :as helpers]))

(defn info-card [item handle-item-click]
  [:div.w-100.w-33-m.w-20-l.card
   {:on-click (fn [e] (.preventDefault e) (handle-item-click item))
    :style {:background-image (str "url(" (helpers/img-url (get-in item ["url"])) ")")}}])

