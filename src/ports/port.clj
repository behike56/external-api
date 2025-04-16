(ns ports.port)

(defprotocol UserRepository
  "ユーザ情報を取得/操作するためのポートの例"
  (find-user-by-id [this user-id] "ユーザIDでユーザ情報を取得する"))