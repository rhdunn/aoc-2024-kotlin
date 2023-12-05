// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0

import io.github.rhdunn.gradle.maven.BuildType

/**
 * Information about the project.
 */
@Suppress("ConstPropertyName")
object ProjectMetadata {
    /**
     * Version information about the current build of the project.
     */
    object Build {
        /**
         * The semantic version of the current version.
         */
        const val VersionTag = "1.0.0" // TODO: Modify this property.

        /**
         * The build type of this project.
         */
        val Type = BuildType.Snapshot // TODO: Modify this property.

        /**
         * The artifact version ID.
         */
        val Version = "$VersionTag${Type.suffix}"
    }

    /**
     * Information about the GitHub account that owns the project.
     */
    object GitHub {
        /**
         * The ID of the GitHub account (organization or developer) to which this project belongs.
         */
        const val AccountId = "rhdunn" // TODO: Modify this property.

        /**
         * The Maven group ID.
         */
        const val GroupId = "io.github.$AccountId"
    }
}
