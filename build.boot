(def +version+ "0.1.0-SNAPSHOT")

(set-env!
  :source-paths #{"src"}
  :dependencies '[[boot/core              "2.5.1"           :scope "test"]
                  [adzerk/bootlaces       "0.1.13"          :scope "test"]])

(require '[tolitius.boot-stripper :refer [strip-deps-attr]]
         '[adzerk.bootlaces :refer :all]
         '[boot.util])

(deftask strip-test []
  (strip-deps-attr :attr :scope :value "test")
  (when-not (= '[[boot/core "2.5.1"] 
                 [adzerk/bootlaces "0.1.13"]] 
               (get-env :dependencies))
    (boot.util/fail "(!) strip-deps-attr did not strip attributes correctly\n\n")
    (boot.util/exit-error)))

(bootlaces! +version+)

(task-options!
  pom {:project     'tolitius/boot-stripper
       :version     +version+
       :description "stripping things from filesets, tasks and env"
       :url         "https://github.com/tolitius/boot-stripper"
       :scm         {:url "https://github.com/tolitius/mount"}
       :license     {"Eclipse Public License"
                     "http://www.eclipse.org/legal/epl-v10.html"}})

