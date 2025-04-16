(ns core.domain.domain)

(defn uppercase-name
  "ユーザ名を大文字にして返すサンプル関数。"
  [user]
  (update user :name clojure.string/upper-case))
