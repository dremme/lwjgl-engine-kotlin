#version 410 core

in vec2 uv_coord;

out vec4 color;

uniform vec3 tint;
uniform bool has_albedo;
uniform sampler2D albedo_map;

void main(void) {
    vec4 albedo;
    if (has_albedo) albedo = texture(albedo_map, uv_coord);
    else            albedo = vec4(1.0);

    color.rgb = tint * albedo.rgb;
    color.a   = albedo.a;
}
