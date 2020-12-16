(ns solution-03.core
  (:gen-class))

(defn parse
  "Convert a CSV into rows of columns"
  [string]
  (clojure.string/split string #"\n"))

(def filename "resources/input.txt")

(defn look-for-trees
  [right-step rows]
  (loop [remaining-rows rows
         x 0
         total 0]
    (let [[row & rest] remaining-rows]
;;      (println x total (nth row x) row)
      (if (nil? rest)
        (if (= \# (nth row x)) (+ total 1) total)
        (recur rest (mod (+ x right-step) 31) (if (= \# (nth row x)) (+ total 1) total))))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [rows (parse (slurp filename))]
    (println (look-for-trees 3 rows))
    (reduce *
            [(look-for-trees 3 rows)
             (look-for-trees 1 rows)
             (look-for-trees 5 rows)
             (look-for-trees 7 rows)
             (look-for-trees 1 (take-nth 2 rows))])))
