(ns solution-05.core
  (:gen-class))

(require '[clojure.math.numeric-tower :as math])

(defn split-into-rows
  "Convert a CSV into rows of columns"
  [string]
  (clojure.string/split string #"\n"))

(def filename "resources/input.txt")

(defn split-rows-cols
  [string]
  [(subs string 0 7) (subs string 7 10)])

(defn string-to-value
  [one zero string]
  (loop [remaining-string string
         total 0
         current-power (math/expt 2 (- (count string) 1))]
    (let [[first & rest] remaining-string]
      (if (nil? first)
        total
        (if (= first one)
          (recur rest (+ total current-power) (/ current-power 2))
          (recur rest total (/ current-power 2)))))))

(defn find-my-seat
  [seats]
  (let [sorted-seats (sort seats)
;;        _ (println sorted-seats)
        ]
    (loop [[first-seat second-seat third-seat & rest] sorted-seats]
;;      (println first-seat second-seat third-seat)
      (if (and
           (= (- second-seat first-seat) 1)
           (= (- third-seat second-seat) 1)
           (not (nil? rest)))
        (recur (cons second-seat (cons third-seat rest)))
        second-seat))))

(defn values-of-row
  [row]
  (+ (* 8 (string-to-value \B \F (get row 0))) (string-to-value \R \L (get row 1))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [seat-ids (map #(values-of-row (split-rows-cols %)) (split-into-rows (slurp filename)))]
    (println (apply max seat-ids))
    (println (+ 1 (find-my-seat seat-ids)))))
