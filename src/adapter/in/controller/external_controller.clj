(ns adapter.in.controller.external-controller
  (:require [core.service.external-service :as svc]
            [ring.util.response :as response]))

(defn fetch-data-handler [client]
  (fn [_req]
    (let [data (svc/fetch-something client)]
      (response/response data))))
