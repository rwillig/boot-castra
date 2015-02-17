(set-env!
 :source-paths #{"src" "test"}
 :dependencies '[[org.clojure/clojure     "1.6.0"     :scope "provided"]
                 [adzerk/bootlaces        "0.1.9"     :scope "test"]
                 [adzerk/boot-test        "1.0.3"     :scope "test"]
                 [tailrecursion/castra    "3.0.0"     :scope "test"]
                 [tailrecursion/boot-ring "0.1.0"]])

(require
 '[adzerk.bootlaces :refer :all] ;; tasks: build-jar push-snapshot push-release
 '[adzerk.boot-test :refer :all])

(def +version+ "0.1.0-SNAPSHOT")

(bootlaces! +version+)

(task-options!
 pom {:project     'rwillig/boot-castra
      :version     +version+
      :description "Boot task to handle castra"
      :url         "https://github.com/rwillig/boot-castra"
      :scm         {:url "https://github.com/rwillig/boot-castra"}
      :license     {"Eclipse Public License" "http://www.eclipse.org/legal/epl-v10.html"}})
