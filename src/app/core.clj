(ns app.core
  " アプリケーション起動責務（Jetty起動＋DI）"
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [ragtime.repl :as ragtime]
            [ragtime.jdbc :as rag-jdbc]
            [adapter.out.db :as db]
            [adapter.in.handler :as handler])
  (:gen-class))

  ;; DBマイグレーションのサンプル設定
(defn migrate []
  (let [ds (db/create-rag-datesource)
        config {:datastore  (rag-jdbc/sql-database ds)
                :migrations (rag-jdbc/load-resources "migrations")}]
    (ragtime/migrate config)))

(defn -main [& args]
    ;; 1. データベースマイグレーション
  (migrate)

    ;; 2. DBリポジトリ(ポート実装)を用意
  (let [db-repo (db/new-db-user-repository)
        handler (handler/handler db-repo)]
      ;; 3. Ringサーバ起動
    (run-jetty handler {:port 3000
                        :join? false})
    (println "Server started on port 3000.")))

;; ★ 開発用にサーバインスタンスを返す関数
(defn run
  "開発時のREPLなどでサーバを起動し、Jettyインスタンスを返す。"
  []
  (migrate)
  (let [db-repo (db/new-db-user-repository)
        ext-client (->RealExternalApiClient) 
        handler (handler/handler db-repo)]
    (println "Starting server on port 3000 (dev mode)...")
    (run-jetty handler {:port 3000 :join? false})))
