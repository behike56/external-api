(ns app.core
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [ragtime.repl :as ragtime]
            [ragtime.jdbc :as rag-jdbc]
            [app.adapter.db :as db]
            [app.adapter.ext-api :as ext])
  (:gen-class))

  ;; DBマイグレーションのサンプル設定
(defn migrate []
  (let [ds (db/create-datasource)
        config {:datastore  (rag-jdbc/sql-database ds)
                :migrations (rag-jdbc/load-resources "migrations")}]
    (ragtime/migrate config)))

(defn -main [& args]
    ;; 1. データベースマイグレーション
  (migrate)

    ;; 2. DBリポジトリ(ポート実装)を用意
  (let [db-repo (db/new-db-user-repository)
        handler (ext/handler db-repo)]
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
        handler (ext/handler db-repo)]
    (println "Starting server on port 3000 (dev mode)...")
    (run-jetty handler {:port 3000 :join? false})))