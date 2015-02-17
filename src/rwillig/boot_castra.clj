(ns rwillig.boot-castra
  {:boot/export-tasks true}
  (:require
   [boot.pod                        :as pod]
   [boot.util                       :as util]
   [tailrecursion.castra.handler    :as c     :refer [castra]]
   [tailrecursion.boot-ring         :as r]
   [boot.core                       :as core  :refer [deftask]]))


(deftask castra-dev-server
  "Creates a server for development with castra. 
  
  The first argument is
  a quoted namespace or a quoted vector of namespaces with Castra endpoints."
  
  [n namespaces    SYM  sym  "The castra handler(s) to serve."
   d docroot       PATH str  "The directory to serve."
   p port          PORT int  "The port to listen on. (Default: 8000)"
   k session       KEY  str  "session key"
   c cors?              bool "use cors?"]
  (let [port      (or port 8000)
        join?     false
        session   (or session "a 16-byte secret")
        cors?     (or cors? false)]
    (->> [(r/head)
        (r/dev-mode)
        (if cors? (r/cors #".*localhost.*"))
        (r/session-cookie session)
        (r/files docroot)
        (r/reload)
        (if (coll? namespaces) (apply castra namespaces) (castra namespaces))
        (r/jetty :port port :join? join?)]
       (filter identity)
       (apply comp))))

