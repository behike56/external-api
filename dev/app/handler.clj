(ns be-external-api-clj.handler
  (:require [reitit.ring :as ring]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            
            [ring.logger :as logger]
            [muuntaja.core :as m]
            [muuntaja.middleware :as middleware]))
  
;; Helloハンドラ
(defn hello-handler [_]
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body {:message "Hello, World!"}})

;; アプリケーションのルーティングとミドルウェア設定
;; (def app
;;   (middleware/wrap-format
;;     (ring/ring-handler
;;       (ring/router
;;         [["/api/hello" {:get hello-handler}]])
;;       (wrap-defaults logger/wrap-with-logger site-defaults)
;;       {:muuntaja m/instance})))
  
;; アプリケーションのルーティングとミドルウェア設定
(def app
  (-> (ring/ring-handler
        (ring/router
          [["/api/hello" {:get hello-handler}]]))
      (wrap-defaults site-defaults)
      (middleware/wrap-format m/instance) ;; wrap-formatをアプリケーション全体に適用
      (logger/wrap-with-logger))) ;; ロガーを適用