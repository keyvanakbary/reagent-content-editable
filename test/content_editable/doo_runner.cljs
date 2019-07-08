(ns content-editable.doo-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [content-editable.core-test]))

(doo-tests 'content-editable.core-test)