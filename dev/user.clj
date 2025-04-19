(ns user
  "REPL 起動時に自動で読み込まれる開発用セットアップ"
  (:require [dev :as dev]))

;; REPL起動と同時にサーバ起動したければ以下を有効に
(dev/start)

(println "開発用環境が起動しました。dev/start, dev/stop, dev/restart が使えます。")
