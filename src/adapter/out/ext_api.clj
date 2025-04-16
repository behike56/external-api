(ns adapter.out.ext-api
  (:require [clj-http.client :as client]
            [ports.out.external-api :refer [ExternalApiClient]]))

(defrecord RealExternalApiClient []
  ExternalApiClient
  (fetch-data [_ endpoint]
    (let [url (str "https://api.example.com/" endpoint)]
      (-> (client/get url {:as :json})
          :body))))
