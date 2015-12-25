# boot-stripper

[Boot](https://github.com/boot-clj/boot) tasks to strip things

[![Clojars Project](http://clojars.org/tolitius/boot-stripper/latest-version.svg)](http://clojars.org/tolitius/boot-stripper)

## Usage

Say we have a list of dependencies:

```clojure
(set-env!
  ;; ...
  :dependencies '[[org.clojure/clojure        "1.7.0"        :scope "provided"]
                  [org.clojure/clojurescript  "1.7.189"      :scope "provided"  :classifier "aot"]
                  [org.clojure/tools.reader   "0.9.2"                           :classifier "aot"]
                  [org.clojure/data.json      "0.2.6"                           :classifier "aot"]
                  [datascript                 "0.13.3"]])
```

We would like to run some of the ClojureScript tests with the above dependencies, but _without AOT classifiers_.

Boot stripper can strip those easily:

```clojure
(set-env!
  ;; ...
  :dependencies '[;; ...
                  [tolitius/boot-stripper      "0.1.0-SNAPSHOT"  :scope "test"]])

(require ;; ...
         '[tolitius.boot-stripper :refer [strip-deps-attr]])

(deftask test []
  ;; ...

  (comp 
    (strip-deps-attr :attr :classifier :value "aot")
    (test-cljs)))
```

note `(strip-deps-attr :attr :classifier :value "aot")`.

#### Help

```clojure
$ boot strip-deps-attr -h
strips out an attribute (optionally identified by a value) from all the dependencies

Options:
  -h, --help         Print this help info.
  -a, --attr ATTR    Set the name of the attribute (i.e. "classifier") to strip out to ATTR.
  -v, --value VALUE  Set the optional value of the attribute to strip out to VALUE.
```

## License

Copyright © 2015 toliitus

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
