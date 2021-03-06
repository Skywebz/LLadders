## Luppii's Ladders

Official sites:
* Repository: https://github.com/CodeAG/LLadders
* Webpage: http://ladders.luppii.se
* Minecraftforum.net thread: http://www.minecraftforum.net/topic/2568061-172164-luppiis-ladders/

Issues and feature requests, register them at https://github.com/CodeAG/LLadders/issues.

### Setup dev environment for Luppii's Ladders
1. Ensure that you have [Java JDK 7](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html) and [Git](http://git-scm.com/). If you use Windows you can replace Git with [Github for Windows](https://windows.github.com/) for easy (point-and-click) cloning and commiting.
2. Clone the LLadders repository.
3. You will need a deobfuscated copy of Ropes+ in order to build (not required for dev environment).
 * You can find the source code for Ropes+ at: https://code.google.com/p/atomicstrykers-minecraft-mods/source/browse
 * Place the deobfuscated Ropes+ jar-file in the 'libs' (create it if necessarily) folder in root directory (LLadders) for the repository.
4. Navigate to root directory (LLadders) for the repository using the terminal/command prompt and run the following commands:
 * `gradlew setupDecompWorkspace` to setup a complete development environment.
 * `gradlew --refresh-dependencies eclipse` for a pre-built Eclipse java project. If you use `IntelliJ IDEA` you can replace `eclipse` with `idea`.
 * On Windows: use `gradlew.bat` instead of `gradlew`.
5. Building a ready to use package:
 * `gradlew build` to build the .jar-file.
 * The compiled and obfuscated file will be located in 'LLadders/build/libs/'.

### Run configurations in Eclipse
* Client:
  * Main class: `net.minecraft.launchwrapper.Launch`
  * Program arguments: `--version 1.7 --tweakClass cpw.mods.fml.common.launcher.FMLTweaker --accessToken dev -username=Player --userProperties {} --assetIndex 1.7.10 --assetsDir ~/.gradle/caches/minecraft/assets`
    * You might need to replace `--assetsDir ~/.gradle/caches/minecraft/assets` with an absolute path to that folder.
  * VM arguments: `-Dfml.ignoreInvalidMinecraftCertificates=true`
* Server:
  * Main class: `cpw.mods.fml.relauncher.ServerLaunchWrapper`
  * Starting the server:
    1. Run server once, it will crash. Edit `eula=false ` inside 'eula.txt' to `eula=true`.
    2. Run server again. Once loaded, stop it and edit `online-mode=true` inside 'server.properties' to `online-mode=false`
