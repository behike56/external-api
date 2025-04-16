(ns core.service.user-service
  (:require [ports.out.user :as user-port]))

(defn get-user [repo user-id]
  (some-> user-id
          (Integer/parseInt)
          (user-port/find-user-by-id repo)))

(defn- validate-user [user-data]
  (let [name (:name user-data)]
    (cond
      (nil? name)
      {:status 400 :error "name is required"}

      (not (string? name))
      {:status 400 :error "name must be a string"}

      (< (count name) 2)
      {:status 400 :error "name too short"}

      :else
      nil))) ;; バリデーションエラーなし

(defn create-user [repo user-data]
  (if-let [err (validate-user user-data)]
    err
    (user-port/create-user repo user-data)))
