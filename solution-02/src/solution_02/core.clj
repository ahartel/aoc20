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

(defn letter-to-char
  [letter]
  (char (first (.getBytes letter))))

(defn count-letter
  [letter string]
  (reduce + (map #(if (= (letter-to-char letter) %) 1 0) string)))

(defn check-password
  "Check if :letter appears between :min and :max times in :data"
  [row]
  (let [num-found (count-letter (:letter row) (:data row))]
;;    (println row num-found)
    (if (and (<= num-found (:max row)) (>= num-found (:min row)))
      1
      0)
    ))

(defn check-password-2
  "Check if :letter is on exactly one position of :min or :max in :data"
  [row]
  (let [min-found (= (letter-to-char (:letter row)) (nth (:data row) (- (:min row) 1)))]
    (let [max-found (= (letter-to-char (:letter row)) (nth (:data row) (- (:max row) 1)))]
      (if (or (and min-found (not max-found)) (and (not min-found) max-found))
        1
        0))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [mapped-data (mapify (parse (slurp filename)))]
    (let [num-valid-passwords (reduce + (map check-password mapped-data))]
      (println "There are" num-valid-passwords "correct passwords"))
    (let [num-valid-passwords (reduce + (map check-password-2 mapped-data))]
      (println "There are" num-valid-passwords "correct passwords"))))
