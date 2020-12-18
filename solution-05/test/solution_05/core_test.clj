(ns solution-05.core-test
  (:require [clojure.test :refer :all]
            [solution-05.core :refer :all]))

(deftest a-test
  (testing "File is read"
    (is (some? (split-into-rows (slurp filename)))))
  (testing "String is split"
    (is (= (split-rows-cols "BFFFBBFRRR") ["BFFFBBF" "RRR"])))
  (testing "F is zero"
    (is (= (string-to-value \B \F "F") 0))))
