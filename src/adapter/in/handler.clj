(ns adapter.in.handler
  (:require
   [reitit.ring :as ring]
   [muuntaja.middleware :as muuntaja]
   [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
   [ring.middleware.params :refer [wrap-params]]
   [ring.middleware.keyword-params :refer [wrap-keyword-params]]
   [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
   [ring.logger :refer [wrap-with-logger]]
   [adapter.in.controller.user-controller :as user-controller]
   [adapter.in.controller.external-controller :as external-controller]))

(defn app-routes [db-repo ext-client]
  (ring/ring-handler
   (ring/router
    [["/api/user/:id" {:get (user-controller/get-user-handler db-repo)}]
     ["/api/user"     {:post (user-controller/create-user-handler db-repo)}]
     ["/api/external" {:get (external-controller/fetch-data-handler ext-client)}]])
   (ring/create-default-handler)))

(defn handler [db-repo ext-client]
  (-> (app-routes db-repo ext-client)
      (wrap-json-response)
      (wrap-json-body {:keywords? true})
      (wrap-keyword-params)
      (wrap-params)
      (wrap-with-logger)
      (wrap-defaults site-defaults)))
