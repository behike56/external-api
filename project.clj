(defproject be-external-api-clj "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.12.0"]
                 [org.clojure/core.memoize "1.1.266"]
                 [info.sunng/ring-jetty9-adapter "0.33.3" :exclusions [org.slf4j/slf4j-api]]
                 [org.clojure/tools.logging "1.3.0"]
                 [spootnik/unilog "0.7.32"]
                 [ring-logger/ring-logger "1.1.1"]
                 [ring/ring-defaults "0.5.0"]
                 [ring/ring-devel "1.12.2"]
                 [metosin/reitit "0.7.2"]
                 [metosin/ring-http-response "0.9.4"]
                 [metosin/muuntaja "0.6.10"]
                 [com.stuartsierra/component "1.1.0"]
                 [aero "1.1.6"]
                 [camel-snake-kebab "0.4.3"]]
  :plugins [[dev.weavejester/lein-cljfmt "0.12.0"]]
  :main ^:skip-aot be-external-api-clj.core
  :target-path "target/%s"
  :profiles {:dev {:source-paths ["dev"]}
             :test {:dependencies [[lambdaisland/kaocha "1.91.1392"]]
                    :plugins [[lambdaisland/kaocha "1.91.1392"]]
                    :test-paths ["test"]
                    :main-opts ["-m" "kaocha.runner"]}
             :build {:dependencies [[io.github.clojure/tools.build "0.10.5"]]}
             :uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
