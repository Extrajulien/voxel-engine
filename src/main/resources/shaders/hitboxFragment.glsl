#version 330 core

uniform vec3 hitboxColor;

out vec4 FragColor;

void main() {
    FragColor = vec4(hitboxColor/255, 1.0f);
}
