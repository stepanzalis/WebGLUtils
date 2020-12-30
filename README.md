## WebGL with KotlinJS

- This project is proof of concept for using WebGL with KotlinJS instead of Java OpenGL (with OGL utils) at UHK FIM.
  - Helper methods and utils are highly inspired by OGLUtils, but implemented the Kotlin way. 
- Transform package converted to Kotlin is used for working with matrices and camera. 

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

