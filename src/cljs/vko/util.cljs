(ns vko.util)

(defn start-upgrading []
  (js/setInterval #(.upgradeDom js/componentHandler) 100))
