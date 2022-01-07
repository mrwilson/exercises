load("//tools:junit5.bzl", "java_junit5_test")

java_library(
    name = "trivia",
    srcs = ["Game.java", "GameRunner.java", "Player.java"]
)

java_junit5_test(
    name = "tests",
    srcs = glob([
        "*Test.java",
    ]),
    test_package = 'uk.co.probablyfine.exercises.trivia',
    deps = [
        "@maven//:org_hamcrest_hamcrest",
        "//src/test/java/uk/co/probablyfine/exercises/trivia:trivia",
    ],
    data = [
        "//src/test/resources:trivia-fixed-output"
    ]
)