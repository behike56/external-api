(ns core.service.user-service-test
  (:require [clojure.test :refer :all]
            [core.service.user-service :as service]
            [ports.out.user :as port]))

;; ダミーの UserRepository 実装（mock）
(def mock-repo
  (reify port/UserRepository
    (create-user [_ user] {:mock-inserted user})
    (find-user-by-id [_ id] {:id id :name "Mock"})))

(deftest create-user-test
  (testing "正常系: ユーザーが作成できる"
    (let [res (service/create-user mock-repo {:name "Alice"})]
      (is (= res {:mock-inserted {:name "Alice"}}))))

  (testing "バリデーション失敗: name が空"
    (let [res (service/create-user mock-repo {})]
      (is (= (:status res) 400))
      (is (= (:error res) "name is required"))))

  (testing "バリデーション失敗: name が短すぎる"
    (let [res (service/create-user mock-repo {:name "A"})]
      (is (= (:status res) 400))
      (is (= (:error res) "name too short")))))
