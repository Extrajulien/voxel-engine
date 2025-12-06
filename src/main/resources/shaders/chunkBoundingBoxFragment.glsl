#version 330 core

uniform vec3 line_color;

out vec4 FragColor;

void main() {
    FragColor = vec4(line_color.x/255, line_color.y/255, line_color.z/255 , 1.0f);
}

