(ns solution-01.core
  (:gen-class))

;; I basically want a function that takes a givent integer
;; and sums it with each integer in a collection and returns
;; that value in the collection that sums to 2020 with the input

(defn matcher-finder
 [target value numbers]
;; (println " Target:" target "Value:" value "Numbers:" numbers)
 (loop [remaining-numbers numbers]
  (if (empty? remaining-numbers)
;;   (do (println "Returning nil") nil)
   nil
   (let [[first & remaining] remaining-numbers]
     (if (= target (+ value first))
     first
     (recur remaining))))))

(def filename "resources/input.txt")
(def raw_data (slurp filename))
(defn split [raw_data] (clojure.string/split raw_data #"\n"))
(defn intify [list](map #(Integer. %) list))
(def data (intify (split raw_data)))
(def dummy_data '(5 1010 5 1010 1 1009))

(defn find_and_mult_partner
    ""
    [target numbers]
;    (println "Target:" target "Numbers:" numbers)
    (loop [remaining-numbers numbers]
     (let [[first & remaining] remaining-numbers]
      (let [match (matcher-finder target first remaining)]
;       (println "Got" match "remaining" remaining)
       (if (or (nil? remaining) (and (some? match) (= target (+ first match))))
        (list first match)
        (recur remaining))))))

(defn find_and_mult_triple
 ""
 [target numbers]
 (loop [remaining-numbers numbers]
  (let [[first & remaining] remaining-numbers]
   (let [others (find_and_mult_partner (- target first) remaining)]
;    (println "Got" others)
    (if (and (every? some? others) (= target (reduce + first others)))
     (concat [first] others)
     (recur remaining))))))
    ;;(println others)))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println (reduce * (find_and_mult_partner 2020 dummy_data)))
  (println (reduce * (find_and_mult_partner 2020 data)))
  (println (reduce * (find_and_mult_triple 2020 dummy_data)))
  (println (reduce * (find_and_mult_triple 2020 data))))
