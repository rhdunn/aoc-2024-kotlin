// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0

plugins {
    kotlin("jvm") version Version.Plugin.KotlinJvm
    application
}

group = ProjectMetadata.GitHub.GroupId
version = ProjectMetadata.Build.Version

repositories {
    mavenCentral()
}

application {
    mainClass.set("ApplicationKt")
}
