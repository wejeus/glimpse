Releasing
========

pre: make sure maven settings are correct according to: https://github.com/chrisbanes/gradle-mvn-push

 1. Change the version in `gradle.properties` to a non-SNAPSHOT version.
 2. Update the `CHANGELOG.md` for the impending release.
 3. Update the `README.md` with the new version.
 4. `git commit -am "Prepare for release X.Y.Z."` (where X.Y.Z is the new version)
 5. `./gradlew clean uploadArchives`.
 6. Visit [Sonatype Nexus](https://oss.sonatype.org/) and promote the artifact.
 7. `git tag -a X.Y.X -m "Version X.Y.Z"` (where X.Y.Z is the new version)
 8. Update the `gradle.properties` to the next SNAPSHOT version.
 9. `git commit -am "Prepare next development version."`
 10. `git push && git push --tags`
 11. Update the two sample modules to point to the newly released version.

If step 5 or 6 fails, drop the Sonatype repo, fix the problem, commit, and start again at step 5.