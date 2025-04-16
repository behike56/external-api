(ns adapter.in.controller.user-controller
  (:require [core.service.user-service :as user-service]
            [ring.util.response :as response]))

(defn get-user-handler [repo]
  (fn [req]
    (let [id-str (-> req :path-params :id)
          user (user-service/get-user repo id-str)]
      (if user
        (response/response user)
        (response/not-found {:error "User not found"})))))

(defn create-user-handler [repo]
  (fn [req]
    (let [user-data (:body req)
          result (user-service/create-user repo user-data)]
      (if (:error result)
        (response/status (response/response result) (:status result))
        (response/response result)))))
