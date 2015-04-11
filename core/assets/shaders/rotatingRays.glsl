varying vec2 vTexCoord0;
varying vec4 vColor;
varying vec4 vCoord;

uniform mat4 u_projTrans;
uniform sampler2D u_texture;
uniform float u_globalTime;

void main()
{
	vec2 p = vTexCoord0 - 0.5;
	float f = smoothstep(-0.1, 0.1, sin(atan(p.y,p.x)*10.0 + u_globalTime));
	gl_FragColor = vec4(mix( vec3(1.0f, 0.5f, 0.125f), vec3(1.,1.,1.0), f)*(1.0 - 0.4*length(p)), 1.);
}
