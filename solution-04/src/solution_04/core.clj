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


(defn check-fields-present
  [values]
  (let [keys (map #(first (clojure.string/split % #"\:")) values)
        num-keys (count keys)]
    (if (= 8 num-keys)
      [1 num-keys keys]
      (if (and (= 7 num-keys) (not (some #{"cid"} keys)))
        [1 num-keys keys]
        [0 num-keys keys]))))

(defn valid-year
  [from to value]
  (let [val (Integer/parseInt value)]
    (and (>= val from) (<= val to))))

(defn valid-hgt
  [value]
  (if (clojure.string/includes? value "cm")
    (let [substring (subs value 0 3)]
      (if (every? #(Character/isDigit %) substring)
        (let [cm-val (Integer/parseInt substring)]
          (and (>= cm-val 150) (<= cm-val 193)))
        false))
    (let [substring (subs value 0 2)]
      (if (every? #(Character/isDigit %) substring)
        (let [in-val (Integer/parseInt substring)]
          (and (>= in-val 59) (<= in-val 76)))
        false))))

(defn valid-ecl
  [value]
  (case value
    "amb" true
    "blu" true
    "brn" true
    "gry" true
    "grn" true
    "hzl" true
    "oth" true
    false))

(defn valid-hcl
  [value]
  (let [[hash & digits] value]
    (if (not= hash \#)
      false
      (loop [[char & rest] digits]
        (if (nil? char)
          true
          (if (or (Character/isDigit char)
                  (= \a char)
                  (= \b char)
                  (= \c char)
                  (= \d char)
                  (= \e char)
                  (= \f char))
            (recur rest)
            false))))))

(defn check-fields-valid
  [values]
  (let [keys (map #(clojure.string/split % #"\:") values)]
    (loop [[pair & rest] keys]
      (let [[key value] pair
            valid-byr (partial valid-year 1920 2002)
            valid-iyr (partial valid-year 2010 2020)
            valid-eyr (partial valid-year 2020 2030)
            valid-pid #(= 9 (count %))
            valid (case key
                    "byr" (valid-byr value)
                    "iyr" (valid-iyr value)
                    "eyr" (valid-eyr value)
                    "hgt" (valid-hgt value)
                    "hcl" (valid-hcl value)
                    "ecl" (valid-ecl value)
                    "pid" (valid-pid value)
                    "cid" true)]
        (if (false? valid)
          [0 keys]
          (if (nil? rest)
            [1 keys]
            (recur rest)))))))

(defn check-passport
  [passport]
  (let [[_ & values] (clojure.string/split passport #" ")
        present-fields (check-fields-present values)
        valid-fields (check-fields-valid values)]
    (if (and
         (= 1 (get present-fields 0))
         (= 1 (get valid-fields 0)))
      valid-fields
      [0 passport])))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [rows (split-into-rows (slurp filename))
        passports (combine-entries rows)
        first-half #(check-fields-present (drop 1 (clojure.string/split % #" ")))]
    (println (reduce + (map #(get % 0)
                            (map first-half passports))))
    (println (reduce + (map #(get % 0)
                          (map check-passport passports))))
    ))
