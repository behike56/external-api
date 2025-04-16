(ns core.schema.user-schema
  (:require [malli.core :as m]))

(def UserInputSchema
  [:map
   [:name [:and string? [:min 2]]]])
