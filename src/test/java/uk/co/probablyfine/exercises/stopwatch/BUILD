load("//tools:junit5.bzl", "java_junit5_test")

java_junit5_test(
    name = "tests",
    srcs = glob([
        "*Test.java",
    ]),
    test_package = 'uk.co.probablyfine.exercises.stopwatch',
    deps = [
        "@maven//:org_hamcrest_hamcrest",
    ]
)