(ns core.schema.user-schema
  (:require [malli.core :as m]))

(def UserInputSchema
  [:map
   [:name [:string {:min 2}]]])

(def UserOutputSchema
  [:map
   [:id int?]
   [:name string?]])