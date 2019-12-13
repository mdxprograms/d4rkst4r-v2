(ns app.core
  (:require [reagent.core :as r]
            [ajax.core :refer [GET]]
            [app.info-card :refer [info-card]])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(defonce results (r/atom nil))
(def explanation (r/atom nil))
(def hdurl (r/atom ""))
(def show-explanation (r/atom false))
(def filtered-results (r/atom nil))

(defn handle-item-click [item]
  (reset! show-explanation true)
  (reset! explanation (get-in item ["explanation"]))
  (reset! hdurl (get-in item ["hdurl"])))

(defn handle-overlay-click []
  (reset! show-explanation false)
  (reset! explanation "")
  (reset! hdurl ""))

(defn handle-reset-results []
  (reset! filtered-results @results))

(defn get-results []
  (GET "apods.json" :handler (fn [r] (reset! results r) (reset! filtered-results r))))

(defn filter-results
  [e]
  (let [title (-> e .-target .-value)]
    (let [new-results (filter (fn [item] (clojure.string/includes? (clojure.string/lower-case (get-in item ["title"])) (clojure.string/lower-case title))) @results)]
      (reset! filtered-results new-results))))

(defn overlay []
  [:div#overlay
   {:on-click (fn [e] (handle-overlay-click))}
   [:div#text @explanation]
   [:a.hd-link.f6.link.dim.ph3.pv2.mb2.dib.black.bg-light-green {:href @hdurl :target "_blank"} "View in HD"]])

(defn search-filter []
  [:div#search-filter-wrapper
   [:input#search-filter {:type "text" :placeholder "Search..." :on-change filter-results}]
   [:button#reset-search {:on-click handle-reset-results} "Reset"]])

(defn no-results []
  [:h3#no-results.f3 "No results :("])

(defn cards []
  [:div.article
   [search-filter]
   (if (= (count @filtered-results) 0) [no-results])
   [:div.cf.pa2.flex.flex-wrap
    (for [item @filtered-results]
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
