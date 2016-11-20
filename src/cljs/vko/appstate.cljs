(ns vko.appstate
    (:require [reagent.core :as reagent :refer [atom]]
              [reagent.session :as session]
              [secretary.core :as secretary :include-macros true]
              [accountant.core :as accountant]
              [cljs.core.async :refer (chan put! <!)]))

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
