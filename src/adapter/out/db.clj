(ns adapter.out.db
  (:require [next.jdbc :as jdbc]
            [ports.out.user :refer [UserRepository]]
            [adapter.in.controller.user-controller :as user-controller]))

(defn create-datasource
  "next.jdbc用のDataSourceを作成するサンプル。実際には接続情報を設定ファイルなどから取得します。"
  []
  (jdbc/get-datasource {:dbtype   "h2"
                        :dbname   "test"
                        :user     "sa"
                        :password ""}))

(defn create-rag-datesource
  "rag用のソースを作成する。"
  []
  {:subprotocol "h2"
   :subname     "./test"
   :user        "sa"
   :password    ""})

;; UserRepository プロトコル実装
(defrecord DbUserRepository [datasource]
  UserRepository
  (find-user-by-id [this user-id]
    (let [sql "SELECT id, name FROM users WHERE id = ?"
          result (jdbc/execute-one! datasource [sql user-id])]
      ;; 該当がなければ nil になる
      result))
  (create-user [this user-data]
               (let [sql "INSERT INTO users (name) VALUES (?)"
                     _ (jdbc/execute-one! datasource [sql (:name user-data)])]
                 {:status :ok})))

(defn new-db-user-repository
  "UserRepository 実装インスタンスを生成するファクトリ関数。"
  []
  (->DbUserRepository (create-datasource)))