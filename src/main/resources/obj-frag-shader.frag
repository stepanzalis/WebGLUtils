precision highp float;

varying vec3 vertPos;
varying vec3 normalInterp;

void main(void) {
    vec3 normal = normalize(normalInterp);
    gl_FragColor = vec4(0.4, 0.3, 0.1, 1.0);
}
