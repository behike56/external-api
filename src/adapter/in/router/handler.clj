(ns adapter.in.router.handler
  (:require
   [reitit.ring :as ring]
   [reitit.swagger :as swagger]
   [reitit.swagger-ui :as swagger-ui]
   [reitit.coercion.malli]
   [reitit.ring.coercion :as rrc]
   [reitit.ring.middleware.muuntaja :as muuntaja]
   [muuntaja.core :as m]
   [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
   [ring.middleware.params :refer [wrap-params]]
   [ring.middleware.keyword-params :refer [wrap-keyword-params]]
   [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
   [ring.logger :refer [wrap-with-logger]]
   [adapter.in.controller.user-controller :as user-controller]
   [core.schema.user-schema :as user-schema]))

(defn app-routes [db-repo]
  (ring/ring-handler
   (ring/router
    [["/swagger.json"
      {:get {:no-doc true
             :swagger {:info {:title "external-api"}
                       :securityDefinitions
                       {:Bearer
                        {:type "apiKey"
                         :in "header"
                         :name "Authorization"}}
                       :basePath "/"}
             :handler (swagger/create-swagger-handler)}}]

     ["/api/user"
      {:post {:summary "Create a new user"
              :parameters {:body user-schema/UserInputSchema}
              :responses {200 {:body user-schema/UserOutputSchema}}
              :handler (user-controller/create-user-handler db-repo)}}]

     ["/api/user/:id"
      {:get {:summary "Get user by ID"
             :parameters {:path [:map [:id int?]]}
             :responses {200 {:body user-schema/UserOutputSchema}}
             :handler (user-controller/get-user-handler db-repo)}}]]
    {:data {:coercion reitit.coercion.malli/coercion
            :muuntaja m/instance
            :middleware [muuntaja/format-middleware
                         rrc/coerce-request-middleware
                         rrc/coerce-response-middleware
                         rrc/coerce-exceptions-middleware]}})
   (ring/routes
    (swagger-ui/create-swagger-ui-handler {:path "/api/swagger"})
    (ring/create-default-handler))))

(defn handler [db-repo]
  (-> (app-routes db-repo)
      (wrap-json-response)
      (wrap-json-body {:keywords? true})
      (wrap-keyword-params)
      (wrap-params)
      (wrap-with-logger)
      (wrap-defaults site-defaults)))
