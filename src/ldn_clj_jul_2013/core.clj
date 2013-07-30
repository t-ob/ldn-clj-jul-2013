(ns ldn-clj-jul-2013.core
  (:use
   [twitter.oauth]
   [twitter.callbacks]
   [twitter.callbacks.handlers]
   [twitter.api.restful])
  (:import
   (twitter.callbacks.protocols SyncSingleCallback)))

(def my-creds (make-oauth-creds "m9xyayF1FMevW1tPg0PQBg"
                                "9WBpU5Yl6FL1yEHo5eHeFvwwm437ixWcsG3UCEyKWI"
                                "209616289-DwpaNyVZLrOCtAoGINqDnRQXXrDh5BC3eVvksJOL"
                                "ZgXJWDVOGhkJng54eIOZKcbDkPfQUxdz3gmjYWsPM"))

;; Searching tweets

(defn get-tweets [keyword]
  (map :text
       (:statuses (:body (search-tweets :oauth-creds my-creds :params {:q keyword})))))

(defn make-tweet [keyword]
  (clojure.string/join " "
                       (take 10
                             (shuffle (reduce into
                                              (map (partial re-seq #"@?\w+")
                                                   (get-tweets keyword)))))))

(def terms ["clojure" "london" "cats" "apple" "thumbs" "modulus" "minuend"])

#_(loop []
  (println (make-tweet (rand-nth terms)))
  (Thread/sleep 1000)
  (recur))
