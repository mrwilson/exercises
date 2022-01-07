load("//tools:junit5.bzl", "java_junit5_test")

java_junit5_test(
    name = "tests",
    srcs = glob([
        "WordWrapperTest.java",
    ]),
    test_package = 'uk.co.probablyfine.exercises.javaclub',
    deps = [
        "@maven//:org_hamcrest_hamcrest",
    ],
)