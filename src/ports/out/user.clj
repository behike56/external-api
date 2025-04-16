(ns ports.out.user)

(defprotocol UserRepository
  (find-user-by-id [this id])
  (create-user [this user-data]))
