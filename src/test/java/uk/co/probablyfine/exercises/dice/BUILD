load("//tools:junit5.bzl", "java_junit5_test")

java_library(
    name = "dice",
    srcs = ["InvalidDiceRollException.java", "RollDice.java"]
)

java_junit5_test(
    name = "tests",
    srcs = glob([
        "*Test.java",
    ]),
    test_package = 'uk.co.probablyfine.exercises.dice',
    deps = [
        "@maven//:org_hamcrest_hamcrest",
        "//src/test/java/uk/co/probablyfine/exercises/dice:dice"
    ],
)