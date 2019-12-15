(ns app.helpers)

(defn is-image [source]
  (or (clojure.string/ends-with? source "jpg")
      (clojure.string/ends-with? source "png")))

(defn img-url [url]
  (if (is-image url)
    url
    "/images/placeholder.png"))
