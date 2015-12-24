(ns tolitius.boot-stripper
  {:boot/export-tasks true}
  (:require [boot.core :as core :refer [deftask set-env!]]
            [boot.util]))

(defn- dep-to-attrs [dep]
  (into {} (map vec (partition 2 dep))))

(defn- attrs-to-dep [attrs]
  (->> attrs
       (apply vector)
       (apply concat)
       vec))

(defn- strip-out [attr-name value dep]
  (let [attrs (dep-to-attrs dep)
        v (attrs attr-name)
        strip? (if value (= v value) v)]
    (attrs-to-dep (if strip?
                    (dissoc attrs attr-name)
                    attrs))))

(deftask strip-deps-attr
  "strips out an attribute (optionally identified by a value) from all the dependencies"
  [a attr ATTR kw "name of the attribute (i.e. \"classifier\") to strip out"
   v value VALUE edn "optional value of the attribute to strip out"]

  (when-not attr
    (boot.util/fail "The -a/--attr option is required!\n") (*usage*))

  (set-env! :dependencies #(mapv (partial strip-out attr value) %))
  identity)

