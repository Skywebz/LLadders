## Luppii's Ladders

Official sites:
* Repository: https://github.com/CodeAG/LLadders
* Webpage: http://ladders.luppii.se
* Minecraftforum.net thread: http://www.minecraftforum.net/topic/2568061-172164-luppiis-ladders/

Issues and feature requests, register them at https://github.com/CodeAG/LLadders/issues.

### Setup dev environment for Luppii's Ladders
1. Ensure that you have [Java JDK 7](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html) and [Git](http://git-scm.com/). If you use Windows you can replace Git with [Github for Windows](https://windows.github.com/) for easy (point-and-click) cloning and commiting.
2. Clone the LLadders repository.
3. Navigate to root directory (LLadders) for the repository using the terminal/command prompt and run the following commands:
 * `gradlew setupDecompWorkspace` to setup a complete development environment.
 * `gradlew --refresh-dependencies eclipse` for a pre-built Eclipse java project. If you use `IntelliJ IDEA` you can replace `eclipse` with `idea`.
 * On Windows: use `gradlew.bat` instead of `gradlew`.
4. Building a ready to use package:
 * `gradlew build` to build the .jar-file.
 * The compiled and obfuscated file will be located in 'LLadders/build/libs/'.
