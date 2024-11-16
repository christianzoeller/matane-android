# Matane

![Screenshot of the app, showing a screen displaying information about kanji](/.gitlab/media/preview.png)

Matane is a native Android app for learning Japanese. It is built with a focus on supporting
not only smartphones, but also tablets and foldables as first-class citizens. It is currently
at a very early stage and does not offer a lot of functionality yet.

Please consider the [issues](https://gitlab.com/christianzoeller/matane-android/-/issues) and
[milestones](https://gitlab.com/christianzoeller/matane-android/-/milestones) to find out more
about the project status.

## Building

This project is configured to automatically create signed release builds. The files containing
the secrets necessary for signing the builds are added to the repository as
[secure files](https://docs.gitlab.com/ee/ci/secure_files/). Therefore, simply checking out the
repository and attempting to build it will result in a failed Gradle sync.

Moreover, the app makes use of [Firebase](https://firebase.google.com/). In order to connect a
Firebase project to an app, a *google-services.json* file is needed in the root directory of
the app's ```app``` module. This file is also included as a secure file and attempting to build
the project without it will fail as well.

Due to this, in case you wish to build the app yourself, please contact me to request Developer role
for this repository, allowing you to download secure files.

## Open Source Libraries

This project makes use of [AboutLibraries](https://github.com/mikepenz/AboutLibraries) to display a
list of used open source dependencies and their licenses within the app.

In order to keep this information up-to-date, the following gradle task has to be run after
changing a dependency:

```
app:exportLibraryDefinitions
```

## Acknowledgements

Matane is built on top of several open source projects aimed at making information on the Japanese
language freely available. Without their work, the project would not be possible.

### KANJIDIC

Kanji data is imported from the [KANJIDIC project](http://www.edrdg.org/wiki/index.php/KANJIDIC_Project).
The KANJDIC project began in 1991 by Jim Breen and is now maintained by the Electronic Dictionary Research
and Development Group ([EDRDG](http://www.edrdg.org/wiki/index.php/Main_Page)).

### RADKFILE / KRADFILE

Data on radicals is imported from the [RADKFILE / KRADFILE project](https://www.edrdg.org/krad/kradinf.html).
This project was also started by Jim Breen and is now maintained by the EDRDG.
