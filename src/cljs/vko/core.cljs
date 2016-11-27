(ns vko.core
    (:require

              [hiccups.runtime :as h]
              [cljsjs.material-ui]
              [cljs-react-material-ui.core :refer [get-mui-theme color]]
              [cljs-react-material-ui.reagent :as ui]
              [cljs-react-material-ui.icons :as ic]
              [reagent.core :as reagent :refer [atom]]
              [reagent.session :as session]
              [secretary.core :as secretary :include-macros true]
              [accountant.core :as accountant]
              [cljs.core.async :refer (chan put! <!)]
              [vko.util :as util]
              )


              (:require-macros [hiccups.core :refer [html]]
                                 )

              )

;; -------------------------
;; Views

(comment (

  primary1Color cyan500,
  primary2Color cyan700,
  primary3Color grey400,
  accent1Color pinkA200,
  accent2Color grey100,
  accent3Color grey500,
  textColor darkBlack,
  secondaryTextColor fade(darkBlack, 0.54),
  alternateTextColor white,
  canvasColor white,
  borderColor grey300,
  disabledColor fade(darkBlack, 0.3),
  pickerHeaderColor cyan500,
  clockCircleColor fade(darkBlack, 0.07),
  shadowColor fullBlack)

  )
(def labelb (atom "1111111"))
  (defn home-page []
    [:div [:h2 "Welcome to vko111177"]

    [ui/mui-theme-provider
     {:mui-theme (get-mui-theme {:palette {:text-color (color :blue200)
                                            :primary1-color (color :deep-orange-a100)
                                            :secondary1-color (color :blue200) }})}
     [ui/raised-button {:label @labelb :secondary true
                        :on-touch-tap #(reset! labelb "55555555")} ]]

     [:div [:a {:href "/about"} "go to about page"]]
     [:div [:a {:href "/list"} "go to list page"]]])


  (defn mount-root2 [d]
    (reagent/render [home-page] (.getElementById d "app2")))

  (defn init2! [d]
    (accountant/configure-navigation!
      {:nav-handler
       (fn [path]
         (secretary/dispatch! path))
       :path-exists?
       (fn [path]
         (secretary/locate-route path))})
    (accountant/dispatch-current!)
    (mount-root2 d))

  (def userauth (atom true))



  (defonce deb-data (reagent/atom {:w-c true
                             :event-data ""}))



(defn debugger-page [src]
 [:html
  [:head
   [:title "re-frisk debugger"]
   [:meta {:charset "UTF-8"}]
   [:meta
    {:content "width=device-width, initial-scale=1", :name "viewport"}]]
  [:body {:style "margin:0px;padding:0px"}


   [:div#app2 {:style "height:100%;width:100%"}
    [:h2 "re-frisk debugger"]
    [:p "ENJOY!"]]]

  [:script {:type "text/javascript", :src src}]])

(defn opendebug []

  (let [w (js/window.open "" "Debugger" "width=800,height=400,resizable=yes,scrollbars=yes,status=no,directories=no,toolbar=no,menubar=no")
          d (.-document w)]

      (.open d)
      (.write d (html (debugger-page (:p @userauth))))
      (init2! d)
))










(defn template-page []
  [:div

  [ui/mui-theme-provider
   {:mui-theme (get-mui-theme
                 {:palette {:text-color (color :black)
                          :primary1-color (color :pinkA200)
                          :accent1-color (color :green600) }})}
   [:div
    [ui/app-bar {:title "ПОЧТА" :style {:margin-bottom 15}
                  :icon-element-right
                   (reagent/as-element [ui/icon-button
                                    (ic/action-account-balance-wallet)])}


                                    ]
    [ui/paper {:style { :display "inline-block" :float "left" :margin-right 50 }}

      [ui/list
      [ui/list-item {:primaryText "Все" :left-icon (ic/content-inbox)}]
      [ui/list-item {:primaryText "Входящие" :left-icon (ic/content-mail)}]
      [ui/list-item {:primaryText "Исходящие" :left-icon (ic/content-send)}]
      [ui/list-item {:primaryText "Черновики" :left-icon (ic/content-drafts)}]
      [ui/list-item {:primaryText "Корзина" :left-icon (ic/content-delete-sweep)}]
      ]

    ]
    [:div {:style {}}
    [ui/table {:style {  }}
      [ui/table-header
        [ui/table-row
          [ui/table-header-column "ID"] [ui/table-header-column "Name"] [ui/table-header-column "Status"]

        ]

      ]


    ]]




    [:div "Hello"]
    [ui/mui-theme-provider
     {:mui-theme (get-mui-theme {:palette {:text-color (color :blue200)}})}
     [ui/raised-button {:label "Blue button"}]]
    (ic/action-home {:color (color :grey600)})
    [ui/raised-button {:label        "open window"
                        :icon         (ic/social-group)
                        :secondary true
                        :on-touch-tap #(opendebug)}]


    ]]





     ] )

(defn login-page []
 [:div [:h2 "please login"]
  [:div [:a {:href "#" :on-click #(reset! userauth true)} "login"]]])

(defn logout-page []

  (accountant/navigate! "/")
  (reset! userauth false)
 )




(defn about-page []
  [:div [:h2 "About vko1"]

  [ui/mui-theme-provider
   {:mui-theme (get-mui-theme {:palette {:text-color (color :blue200)}})}
   [ui/raised-button {:label "Blue button"}]]


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
