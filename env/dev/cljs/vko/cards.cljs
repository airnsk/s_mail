(ns vko.cards
  (:require [reagent.core :as reagent :refer [atom]]
            [reagent.session :as session]
            [vko.core :as core]
            ;;[vko.appstate :as appstate]
            [cljs.core.async :refer (chan put! <! )])
  (:require-macros
   [devcards.core :as dc
    :refer [defcard defcard-doc defcard-rg deftest]]
    [cljs.core.async.macros :refer (go)]))

    (defonce app-state
      (reagent/atom
       {:message "Hello from App State"
        :items [{:display "Item 1"}
                {:display "Item 2"}
                {:display "Item 3"}
                {:display "Item 4"}
                {:display "Item 5"}]
        :active-item {}
        :buttonclick "2"}))


(def EVENTCHANNEL (chan))

(def EVENTS
  {:update-active-item (fn [{:keys [active-item]}]
                         (swap! app-state assoc-in [:active-item] active-item))


    :button1click (fn [eventclick] (swap! app-state assoc-in [:buttonclick] eventclick))})



(go
  (while true
    (let [[event-name event-data] (<! EVENTCHANNEL)]
      (js/console.log (str event-name " " event-data))
      ((event-name EVENTS) event-data))))

(defn mycomp1 []
    [:div>h1 "This is your first devcard!!" (:buttonclick @app-state)]
  )


(defcard-rg first-card
  [mycomp1])

(defcard-rg home-page-card
  [core/home-page])


(defcard-rg input-form
  [:input {:type "button" :value "button"
            :on-click (fn [event] (put! EVENTCHANNEL [:button1click "321"]))}
             ])

(reagent/render [:div] (.getElementById js/document "app"))

;; remember to run 'lein figwheel devcards' and then browse to
;; http://localhost:3449/cards

(defn items-list [EVENTCHANNEL items active-item]
  [:div {:class "items-list"}
   (for [item items]
     ^{:key (:display item)}
     [:div {:class (if (= active-item item) "item active" "item")}
      [:p
       {:on-click (fn [event] (put! EVENTCHANNEL [:update-active-item {:active-item item}]))}
       (:display item)]])])
