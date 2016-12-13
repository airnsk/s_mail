(ns vko.table
    (:require
              [cljsjs.material-ui]
              [cljs-react-material-ui.core :refer [get-mui-theme color]]
              [cljs-react-material-ui.reagent :as ui]
              [cljs-react-material-ui.icons :as ic]
              [reagent.core :as reagent :refer [atom]]
              [reagent.session :as session]
              [cljs.core.async :refer (chan put! <!)]
              [vko.util :as util]
              ))


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

(def vtable
[:div {:style {:padding "10px" } }
[ui/table {:style {  } :selectable false }
  [ui/table-header {:displaySelectAll false  :adjustForCheckbox false }
    [ui/table-row
      [ui/table-header-column "IDD"] [ui/table-header-column "Name"] [ui/table-header-column "Status"]
    ]]
    [ui/table-body {:displayRowCheckbox false}
    [ui/table-row
    [ui/table-row-column "1"] [ui/table-row-column "1"] [ui/table-row-column "1"]

    ]
    [ui/table-row
    [ui/table-row-column "1"] [ui/table-row-column "1"] [ui/table-row-column "1"]

    ]
    [ui/table-row
    [ui/table-row-column [:a {:href "/" } "33dsfsdhfgsjfgjsghdjhsjdsgh"] ] [ui/table-row-column "1"] [ui/table-row-column "1"]

    ]

    [ui/table-row
    [ui/table-row-column "1"] [ui/table-row-column "1"] [ui/table-row-column "1"]

    ]
    [ui/table-row
    [ui/table-row-column "1"] [ui/table-row-column "1"] [ui/table-row-column "1"]

    ]
    [ui/table-row
    [ui/table-row-column "1"] [ui/table-row-column "1"] [ui/table-row-column "1"]

    ]
    [ui/table-row
    [ui/table-row-column "1"] [ui/table-row-column "1"] [ui/table-row-column "1"]

    ]





    ]

    ]
  ]
    )
