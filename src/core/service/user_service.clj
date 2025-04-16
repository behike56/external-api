(ns core.service.user-service
  (:require [ports.out.user :as user-port]))

(defn get-user [repo user-id]
  (if (re-matches #"\d+" user-id)
    (user-port/find-user-by-id repo (Integer/parseInt user-id))
    {:status 400 :error "Invalid user id"}))

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
  (if (m/validate schema/UserInputSchema user-data)
    (user-port/create-user repo user-data)
    {:status 400 :error "Invalid user data"}))
