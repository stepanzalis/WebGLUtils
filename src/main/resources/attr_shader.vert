attribute vec2 inPosition;
attribute vec3 inColor;

varying vec3 outColor;
uniform float time;

void main(void) {
    vec2 position = inPosition;
    position.x += 0.1;
    position.y += cos(position.x + time);
    gl_Position = vec4(position, 0.0, 1.0);
    outColor = inColor;
}