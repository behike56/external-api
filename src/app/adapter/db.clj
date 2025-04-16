(ns app.adapter.db
  (:require [next.jdbc :as jdbc]
            [app.ports.port :refer [UserRepository]]))

(defn create-datasource
  "next.jdbc用のDataSourceを作成するサンプル。実際には接続情報を設定ファイルなどから取得します。"
  []
  (jdbc/get-datasource {:dbtype   "h2"
                        :dbname   "test"
                        :user     "sa"
                        :password ""}))

;; UserRepository プロトコル実装
(defrecord DbUserRepository [datasource]
  UserRepository
  (find-user-by-id [this user-id]
    (let [sql "SELECT id, name FROM users WHERE id = ?"
          result (jdbc/execute-one! datasource [sql user-id])]
      ;; 該当がなければ nil になる
      result)))

(defn new-db-user-repository
  "UserRepository 実装インスタンスを生成するファクトリ関数。"
  []
  (->DbUserRepository (create-datasource)))