(ns solution-01.core
  (:gen-class))

;; I basically want a function that takes a givent integer
;; and sums it with each integer in a collection and returns
;; that value in the collection that sums to 2020 with the input

(defn matcher-finder
 [value numbers]
 (loop [remaining-numbers numbers]
  (if (empty? remaining-numbers)
   nil
   (let [[first & remaining] remaining-numbers]
    (if (= 2020 (+ value first))
     first
     (recur remaining))))))

(def filename "resources/input.txt")
(def raw_data (slurp filename))
(defn split [raw_data] (clojure.string/split raw_data #"\n"))
(defn intify [list](map #(Integer. %) list))
(def data (intify (split raw_data)))
(def dummy_data '(5 1010 5 1010))

(defn find_and_mult_partner
    ""
    [numbers]
    (loop [remaining-numbers numbers]
     (let [[first & remaining] remaining-numbers]
      (let [match (matcher-finder first remaining)]
       (if (and (some? match) (= 2020 (+ first match)))
        (println (* first match))
        (recur remaining))))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
;;  (println dummy_data)
  (find_and_mult_partner dummy_data)
;;  (println data)
  (find_and_mult_partner data))
