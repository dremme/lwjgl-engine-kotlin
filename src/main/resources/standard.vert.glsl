#version 410 core

layout(location = 0) in vec3 vertex_position;

out vec2 uv_coord;

uniform mat4 world_matrix;

void main(void) {
    gl_Position = world_matrix * vec4(vertex_position, 1.0);
    uv_coord    = vertex_position.xy + 1.0 / 2.0; // FIXME: dirty hack
}
