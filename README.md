# Save Flappy Bird

Save Flappy Bird is a modified version of the famous Flappy Bird game. In this game you don't control the bird directly by tapping the screen. But your task is to move the pipes such that the bird can fly between them. If necessary the bird bounces the bottom pipe to jump and fly further. The goal of the game is to fly as far as possible. In order to make the game funnier and harder, there are other birds in the game. Some of them are larger than your, others are smaller. If you collide with a larger bird it will eat part of your body so you will become smaller and smaller until they eat all your body and you die. Otherwise if you collide a smaller bird then you eat it and you will become bigger. But if you are bigger then it's harder for you to jump so the flying becomes more challenging. For you it's necessary to sometimes eat a smaller bird because as the time flows the game speed increases. Therefore, the game becomes pretty challenging. To slow the game speed down, you can eat a larger bird. That's the reason for the name "Save Flappy Bird" your task is to escape all your enemies and save flappy bird.

## Getting Started

These instructions will guide you through the process of installation and running of the application.
This game has been developed by utilizing [libGDX](https://libgdx.badlogicgames.com/) a game development cross-platform framework. Therefore, the subsequent section explain how to install and run the game on different platforms.

### Prerequisites
1. Installed [Java 8](https://www.oracle.com/technetwork/java/javase/overview/java8-2100321.html)
2. Installed [Gradle](https://gradle.org/)

### Installing & Running
For a more detailed instructions on how to package and run `libGDX` application follow [this tutorial](https://github.com/libgdx/libgdx/wiki/Gradle-on-the-Commandline#packaging-the-project).
#### Desktop
0. Navigate to project root directory
1. Package the application into a .jar file
```
$ ./gradlew desktop:dist
```
2. The packaged application is available in `/desktop/build/libs/`
3. Execute the .jar file in `/desktop/build/libs/`
```
$ java -jar desktop-1.0.jar
```
#### Android
0. Navigate to project root directory
1. Package the application into an .apk file
```
$ ./gradlew android:assembleRelease
```
2. Before you can install or publish the application you have to sign the .apk.
3. The built .apk is available under `android/build/outputs/apk`
#### HTML
0. Navigate to project root directory
1. Compile the application to Javascript, HTML and asset files by executing:
```
$ ./gradlew html:dist
```
2. The result of the compilation process is located in `html/build/dist/`
3. The content of the `html/build/dist/` folder has to be served by a web server (Apache, Nginx, etc.). In this tutorial python example is given to explain how to execute a simple http server.
```
$ python -m SimpleHTTPServer
```
4. Open your browser and go to `localhost:8000` and enjoy the game.

## Built With

* [Gradle](https://gradle.org/) - Build & Dependecny Management
* [Java 8](https://www.oracle.com/technetwork/java/javase/overview/java8-2100321.html) - Programming language
* [libGDX](https://libgdx.badlogicgames.com/) - Cross-platform game development framework

## License
Licensed under the Apache License, Version 2.0 (http://www.apache.org/licenses/LICENSE-2.0).
