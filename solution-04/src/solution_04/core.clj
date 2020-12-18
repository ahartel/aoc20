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
        (conj entries (str current-entry " " row))
        (if (clojure.string/blank? (str row))
          (recur remaining (conj entries current-entry) "")
          (recur remaining entries (str current-entry " " row))
          )))))

(defn check-passport
  [passport]
  (let [[_ & values] (clojure.string/split passport #" ")
        keys (map #(first (clojure.string/split % #"\:")) values)
        num-keys (count keys)]
    (if (= 8 num-keys)
      [1 num-keys keys]
      (if (and (= 7 num-keys) (not (some #{"cid"} keys)))
        [1 num-keys keys]
        [0 num-keys keys]))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [rows (split-into-rows (slurp filename))]
    (reduce + (map #(get % 0)
                   (map check-passport
                        (combine-entries rows))))))
