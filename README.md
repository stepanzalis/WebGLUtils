## WebGL with KotlinJS

- This project is proof of concept for using WebGL with KotlinJS instead of Java OpenGL (with OGL utils) at UHK FIM.
  - Helper methods and utils are highly inspired by OGLUtils, but implemented the Kotlin way. Not all the methods and use cases are implemented
  because the utils package is quite big. Thanks for that! 
    
- Transform package converted to Kotlin is used for working with matrices and camera.
  - Maybe there can someone put some love to make work with it even better. 

## How to run?
- type `./gradlew run` in project's root or if you are using IntelliJ, put `run.sh`
  file as shell script configuration (Edit configuration → Add new configuration → shell script → `yourPathToProject/WebGL/run.sh`)
  
## How to debug?
- debugging is done via browser's console, once the JS code is built (in [build] folder)
  
## What are pros and cons of using WebGL with KotlinJS?  
### ➕ Pros
- working almost in every modern browser
- easy to distribute page (Github Pages, free hosting) → no need to archive jar
- easy to setup UI (controls, switchers, debug texts, etc.) due to HTML / CSS
- programming in Kotlin is
   - fun and safe because of type non-nullable and type safety

### ➖ Cons
- KotlinJS not currently working with OpenGL2, which is show stopper for now, hopefully can be fixed in future
  - version of GLSL is only 100
- debugging is done in browser's console, which is not always intuitive

