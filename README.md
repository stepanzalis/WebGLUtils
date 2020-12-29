## WebGL with KotlinJS

- This project is proof of concept for using WebGl with KotlinJS instead of Java OpenGL (with OGL utils) at UHK FIM.
  - Helper methods and utils are highly inspired by OGLUtils, but implemented the Kotlin way. 
- Transform package converted to Kotlin is used for working with matrices and camera. 

### Pros
- working almost in every browser
- easy to setup UI (controls, switchers, etc) due to HTML / CSS
- programming in Kotlin is
   - fun 
   - safe because of type non-nullable and type safe

### Cons
- KotlinJS not currently working with OpenGL2, which is show stopper for now
- debugging is done in browser's console, which is not always intuitive

