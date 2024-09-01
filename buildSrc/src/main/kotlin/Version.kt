// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0

/**
 * Versions of the various plugins and libraries used by the project.
 */
@Suppress("ConstPropertyName")
object Version {
    /**
     * The version of the Kotlin compiler and runtime.
     *
     * `SPDX-License-Identifier: Apache-2.0`
     *
     * @see <a href="https://github.com/JetBrains/kotlin">https://github.com/JetBrains/kotlin</a>
     */
    const val Kotlin = "2.0.0"

    /**
     * Versions of the various plugins used by the project.
     */
    object Plugin {
        /**
         * The version of the `kotlin("jvm")` plugin.
         *
         * `SPDX-License-Identifier: Apache-2.0`
         *
         * @see <a href="https://github.com/JetBrains/kotlin/tree/master/libraries/tools/kotlin-gradle-plugin">https://github.com/JetBrains/kotlin/tree/master/libraries/tools/kotlin-gradle-plugin</a>
         */
        const val KotlinJvm = Kotlin
    }

    /**
     * Versions of the various libraries used by the project.
     */
    object Dependency {
        /**
         * The version of the `org.jetbrains.kotlin:kotlin-test-junit` dependency.
         *
         * `SPDX-License-Identifier: Apache-2.0`
         */
        const val KotlinTest = Kotlin
    }
}
