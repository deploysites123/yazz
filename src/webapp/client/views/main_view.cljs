(ns webapp.client.main-view
    (:refer-clojure :exclude [val empty remove find next parents])
    (:require
        [cljs.reader :as reader]
        [crate.core :as crate]
    )
    (:require-macros
        [cljs.core.async.macros :refer [go alt!]])

    (:use
        [webapp.framework.client.coreclient :only [ header-text body-text body-html make-sidebar swap-section sql el clear addto remote  add-to]]
        [jayq.core                          :only [$ css  append fade-out fade-in empty]]
        [webapp.framework.client.help       :only [help]]
        [webapp.framework.client.eventbus   :only [do-action esb undefine-action]]
        [webapp.framework.client.interpreter :only [!fn]]
    )
    (:use-macros
        [webapp.framework.client.eventbus :only [define-action]]
        [webapp.framework.client.coreclient :only [on-click on-mouseover]]
        [webapp.framework.client.interpreter :only [! !! !!!]]
     )
)






(defn homepage-html []
  (el :div {} [
        (header-text "Welcome to Clojure on Coils" )
        (body-text "Clojure on Coils is a Clojure based webapp framework for
                   single page database or Neo4j backed webapps")
]))


(defn technologies-html []
    (el :div {} [
        (header-text "Technologies" )
        (body-html "<div>Uses: Clojure, Clojurescript, JQuery, Bootstrap.js, Neo4j</div>")
]))

(defn clojure-html []
    (el :div {} [
        (header-text "Clojure" )
        (body-text "Clojure is used for the server side code, and for client side macros (macros
                   are unique to Lisp)")
]))


(defn why-html []
    (el :div {} [
        (header-text "Why" )
        (body-html "<div>In Denmark there is a CV system called
                   <a href='http://nemcv.com'>NemCV</a>. NemCV is made with Clojure
                   and ClojureScript. Clojure on Coils was extracted from the NemCV system,
                   in exactly the same way that Ruby on Rails was extracted from BaseCamp (made
                   in Denmark too, by
                   <a href='http://david.heinemeierhansson.com/'>David Heinemeier Hansson</a>)</div>")
]))

(defn clojuresript-html []
    (el :div {} [
        (header-text "ClojureScript" )
        (body-html "<div><a href='https://github.com/clojure/clojurescript'>ClojureScript</a> is built on <a href='https://developers.google.com/closure/library/'>Google Closure</a>. Google Closure
                   is used by Google to build to build google.com, gmail.com, google+, and all of the other main google properties.
                   <br><br>Both Closurescript anfd Google closure compile down to native Javascript, so they are both cross platform.</div>")
]))

(defn database-html []
    (el :div {} [
        (header-text "Database" )
        (body-html "<div>
                   Clojure on Coils can connect to any relational database backend using a unique technology where the developer can
                   synchronously program database calls from the web client to the database. Under the covers the code is translated to
                   server side code.
                   </div>")
]))

(defn neo4j-html []
    (el :div {} [
        (header-text "Neo4j" )
        (body-html "<div>
                   Neo4j is a leading graph database. Clojure for Coils is developing first class client side support for Neo4j Cypher
                   queries. Watch this space!
                   </div>")
]))


(define-action
    "refresh homepage"
    (do
      (do-action "clear homepage")
      (do-action "show homepage")
    )
)

(defn sidebar []
  (make-sidebar
       {:text "What" :html (homepage-html)}
       {:text "Why" :html (why-html)}
       {:text "Technologies" :html (technologies-html)}
       {:text "Clojure" :html (clojure-html)}
       {:text "Clojurescript" :html (clojuresript-html)}
       {:text "Database" :html (database-html)}
       {:text "Neo4j" :html (neo4j-html)}
   )
)

(define-action
    "show home page"
    (do
        (-> ($ :#main-section)
            (fade-out 200
                      #(do
                         (-> ($ :#main-section)
                             (empty)
                             (append (homepage-html))
                             (fade-in)

                        )

                      )
             )
        )

      (swap-section
            ($ :#left-navigation)
            (sidebar)
       )
        nil
     )
)



