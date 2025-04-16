(ns app.adapter.ext-api
  (:require [ring.util.response :as response]
            [reitit.ring :as ring]
            [app.domain.domain :as domain]
            [app.ports.port :refer [find-user-by-id]]))

(defn handler [db-repo]
  (ring/ring-handler
   (ring/router
    ["/"
     ["/user/:id"
      {:get (fn [req]
              (let [user-id (-> req :path-params :id)
                    user (find-user-by-id db-repo (parse-long user-id))
                    user (when user (domain/uppercase-name user))]
                (if user
                  (response/response user)
                  (response/not-found {:error "User not found"}))))}]])
   (ring/create-default-handler)))