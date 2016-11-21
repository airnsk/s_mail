(ns vko.handler
  (:require [compojure.core :refer [GET defroutes]]
            [compojure.route :refer [not-found resources]]
            [hiccup.page :refer [include-js include-css html5]]
            [vko.middleware :refer [wrap-middleware]]
            [config.core :refer [env]]))

(def mount-target
  [:div#app
      [:h3 "ClojureScript has not been compiled!!!"]
      [:p "please run "
       [:b "lein figwheel"]
       " in order to start the compiler"]])

(defn head []
  [:head
   [:meta {:charset "utf-8"}]
   [:meta {:name "viewport"
           :content "width=device-width, initial-scale=1"}]
   (include-css (if (env :dev) "/css/site.css" "/css/site.min.css"))
   (include-css (if (env :dev) "/materialize/css/materialize.css" "/materialize/css/materialize.min.css"))
   ])

(defn loading-page []
  (html5
    (head)
    [:body {:class "body-container"}
     mount-target
     (include-js "https://code.jquery.com/jquery-2.1.4.min.js")
     (include-js "/materialize/js/materialize.min.js")
     (include-js (if (env :dev) "/js/app.js" "/jsprod/app.js"))



     ]))

(defn cards-page []
  (html5
    (head)
    [:body
     mount-target
     (include-js "/js/app_devcards.js")]))

(defroutes routes
  (GET "/" [] (loading-page))
  (GET "/about" [] (loading-page))
  (GET "/list" [] (loading-page))
  (GET "/cards" [] (cards-page))
  (resources "/")
  (not-found ["Not Found"]  ))

(def app (wrap-middleware #'routes))
