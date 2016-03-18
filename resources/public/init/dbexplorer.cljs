
(defn get-tables []
(remote "!get-list-of-tables"
        {:session-id  (:session-id @webapp.framework.client.system-globals.client-session-atom)}
        (fn [result2]
            (reset! webapp.framework.client.system-globals/app-state (assoc-in @webapp.framework.client.system-globals/app-state [:ui :table-list] result2))))
)
(get-tables)


(comment sql "select * from newtable" [] (fn [rr] (js/alert (pr-str rr))))



(defn-ui-component     main   [app] {}
  (div nil
       (div {:style {:fontWeight "bold"}} "List of tables")
       (map-many
         (fn [table-name]
           (div nil table-name))
         (get-in @webapp.framework.client.system-globals/app-state [:ui :table-list])
         )

       (div {:className "input-group"}
            (input {:id          "tablename"
                    :type        "text"
                    :className   "form-control"
                    :placeholder "New table name" } )

            (span {:className "input-group-btn"}
                  (button {:className "btn btn-default" :type "button"
                           :onClick #(let [table-name    (.. (js/document.getElementById "tablename") -value  )]
                                       (sql  (str "create table " table-name " (did     integer)") {}
                                             (fn [ss]
                                               (do
                                                 (set! (js/document.getElementById "tablename") -value  "")
                                                 (get-tables))))

                                       )
                           } "Add!")
                  )
            )
       )
  )
