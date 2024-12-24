plugins {
  id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "twave-multi-module"

include("infra")
include("infra:feign")
include("infra:security")
include("product")
include("order")
