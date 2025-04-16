(ns ports.out.external-api)

(defprotocol ExternalApiClient
  (fetch-data [this endpoint]))
