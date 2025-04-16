(ns core.service.external-service
  (:require [ports.out.external-api :as ext]))

(defn fetch-something [client]
  (let [result (ext/fetch-data client "data")]
    ;; 必要なら整形や検証処理を挿入
    result))
