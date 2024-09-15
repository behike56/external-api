(ns be-external-api-clj.core
  (:require
   [ring.adapter.jetty9 :as jetty]))

(defn ring-handler
  [_req]
  {:status 200
   :body "Hello, Clojure API"})

(defn -main
  [& _args]
  (jetty/run-jetty ring-handler {:port 8000}))

(comment
  (-main) ;; (1)
  )

;; (ns be-external-api-clj.core
;;   (:require [be-external-api-clj.handler :as handler]
;;             [ring.adapter.jetty9 :as jetty]
;;             [com.stuartsierra.component :as component]
;;             [aero.core :as aero]))

;; (defn load-config []
;;   (aero/read-config "resources/config.edn"))

;; (defrecord ServerComponent [config]
;;   component/Lifecycle
;;   (start [this]
;;     (let [port (:port config)
;;           server (jetty/run-jetty handler/app {:port port :join? false})]
;;       (assoc this :server server)))
;;   (stop [this]
;;     (when-let [server (:server this)]
;;       (.stop server))
;;     (assoc this :server nil)))

;; (defn -main []
;;   (let [config (load-config)
;;         system (component/system-map :server (map->ServerComponent {:config config}))]
;;     (component/start system)))
