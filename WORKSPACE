load("//tools:junit5.bzl", "junit_jupiter_java_repositories", "junit_platform_java_repositories")

junit_jupiter_java_repositories( version = "5.8.2", )
junit_platform_java_repositories( version = "1.8.2", )

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

http_archive(
    name = "rules_jvm_external",
    strip_prefix = "rules_jvm_external-2.8",
    sha256 = "79c9850690d7614ecdb72d68394f994fef7534b292c4867ce5e7dec0aa7bdfad",
    url = "https://github.com/bazelbuild/rules_jvm_external/archive/2.8.zip",
)

load("@rules_jvm_external//:defs.bzl", "maven_install")

maven_install(
    artifacts = [ "org.hamcrest:hamcrest:2.2", ],
    repositories = [ "https://repo1.maven.org/maven2", ],
)