(ns solution-04.core
  (:gen-class))

(def filename "resources/input.txt")

(defn split-into-rows
  "Convert a CSV into rows of columns"
  [string]
  (clojure.string/split string #"\n"))

(defn combine-entries
  [rows]
  (loop [remaining-rows rows
         entries []
         current-entry ""]
    (let [[row & remaining] remaining-rows]
      (if (nil? remaining)
        (conj entries (str current-entry row))
        (if (clojure.string/blank? (str row))
          (recur remaining (conj entries current-entry) "")
          (recur remaining entries (str current-entry " " row))
          )))))
    

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (combine-entries (split-into-rows (slurp filename))))
