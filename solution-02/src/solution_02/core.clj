(ns solution-02.core
  (:gen-class))

(def filename "resources/input.txt")
(def test-string "7-8 r: rrrrrrrrvl\n4-7 f: ffwnzdf")
(def data-keys [:min :max :letter :data])

(defn str->int
  [str]
  (Integer. str))

(def conversions {:min str->int
                  :max str->int
                  :letter identity
                  :data identity})

(defn convert
  [vamp-key value]
  ((get conversions vamp-key) value))

(defn parse-line
  [line]
  (let [[rule data](clojure.string/split line #": ")]
    (let [[nums letter](clojure.string/split rule #" ")]
      (concat (clojure.string/split nums #"-") [letter data]))))

(defn parse
  "Convert a CSV into rows of columns"
  [string]
  (map parse-line
       (clojure.string/split string #"\n")))

(defn mapify
  "Return a seq of maps like {:name \"Edward Cullen\" :glitter-index 10}"
  [rows]
  (map (fn [unmapped-row]
         (reduce (fn [row-map [vamp-key value]]
                   (assoc row-map vamp-key (convert vamp-key value)))
                 {}
                 (map vector data-keys unmapped-row)))
       rows))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (mapify (parse (slurp filename))))
