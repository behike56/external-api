(ns dev
  "開発用の名前空間。REPL でサーバを起動・再起動しながら開発する。"
  (:require [clojure.tools.namespace.repl :as tn]
            [app.core :as core]))

;; サーバーのインスタンスを格納するための atom
(defonce server (atom nil))

(defn stop
  "起動中のサーバがあれば停止する。"
  []
  (when @server
    (.stop @server)
    (reset! server nil)))

(defn start
  "開発用サーバを起動し、Jettyインスタンスを atom に保存する。"
  []
  (when @server
    (stop))  ;; 既に起動中なら一旦停止
  (reset! server (core/run)))

(defn restart
  "ソースを再読み込みして、サーバを再起動。"
  []
  (stop)
  ;; tools.namespace.repl/refresh で依存関係を正しくリロード
  (tn/refresh :after 'dev/start))
