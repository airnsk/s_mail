(ns vko.core
    (:require
              [cljsjs.material]
              [reagent.core :as reagent :refer [atom]]
              [reagent.session :as session]
              [secretary.core :as secretary :include-macros true]
              [accountant.core :as accountant]
              [cljs.core.async :refer (chan put! <!)]
              [vko.util :as util]
              ))

;; -------------------------
;; Views


(defn side-nav []
  [:div.mdl-layout.mdl-js-layout.mdl-layout--fixed-header
  [:header.mdl-layout__header
  [:div.mdl-layout__header-row
   [:span.mdl-layout-title "title"]
   [:div.mdl-layout-spacer]
   [:nav.mdl-navigation.mdl-layout--large-screen-only
    [:a.mdl-navigation__link {:href "#/"} "Main page"]
    [:a.mdl-navigation__link {:href "#/about"} "About"]
    [:a.mdl-navigation__link {:href "#/settings"} "Settings"]]]]

    [:div.mdl-layout__drawer
    [:span.mdl-layout-title "titel2" ]
    [:nav.mdl-navigation
     [:a.mdl-navigation__link {:href "#/"} "Main page"]
     [:a.mdl-navigation__link {:href "#/about"} "About"]
     [:a.mdl-navigation__link {:href "#/settings"} "Settings"]]


    ]
    ])


    (defn search-bar []
      [:form
       {:action "#"}
       [:div.mdl-textfield.mdl-js-textfield.mdl-textfield--expandable
        [:label.mdl-button.mdl-js-button.mdl-button--icon
         {:for "search"}
         [:i.material-icons
          "search"]]
        [:div.mdl-textfield__expandable-holder
         [:input#search.mdl-textfield__input
          {:type "text"}]
         [:label.mdl-textfield__label
          {:for "sample-expandable"}
          "Expandable Input"]]]])


(def userauth (atom true))

(defn template-page []
  [:div


  [:button.fixed-bottom-fab.mdl-button.mdl-js-button.mdl-button--fab.mdl-js-ripple-effect.mdl-button--colored
      [:i.material-icons "add"]]


(side-nav)
(search-bar)


     ] )

(defn login-page []
 [:div [:h2 "please login"]
  [:div [:a {:href "#" :on-click #(reset! userauth true)} "login"]]])

(defn logout-page []

  (accountant/navigate! "/")
  (reset! userauth false)
 )


(defn home-page []
  [:div [:h2 "Welcome to vko111177"]

[:button {:class "waves-effect waves-light btn red"} "test"]

   [:div [:a {:href "/about"} "go to about page"]]
   [:div [:a {:href "/list"} "go to list page"]]])

(defn about-page []
  [:div [:h2 "About vko1"]


[:button {:class "waves-effect waves-light btn green"} "test"]

   [:div [:a {:href "/"} "go to the home page"]]])

(defn list-mail-page []
 [:div [:h2 "list-mail-page vko1"]
  [:div [:a {:href "/"} "go to the home page"]]])



(defn current-page []
  (if @userauth
  [:div [template-page]
  [(session/get :current-page)]]
  [:div [login-page]]))

;; -------------------------
;; Routes

(secretary/defroute "/" []
  (session/put! :current-page #'home-page))

(secretary/defroute "/about" []
  (session/put! :current-page #'about-page))

(secretary/defroute "/list" []
  (session/put! :current-page #'list-mail-page))

(secretary/defroute "/login" []
  (session/put! :current-page #'login-page))

(secretary/defroute "/logout" []
  (session/put! :current-page #'logout-page))

;; -------------------------
;; Initialize app

(defn mount-root []
  (reagent/render [current-page] (.getElementById js/document "app")))

(defn init! []
  (accountant/configure-navigation!
    {:nav-handler
     (fn [path]
       (secretary/dispatch! path))
     :path-exists?
     (fn [path]
       (secretary/locate-route path))})
  (accountant/dispatch-current!)
  (mount-root))
(util/start-upgrading)
