varying vec2 vTexCoord0;
varying vec4 vColor;
varying vec4 vCoord;

uniform mat4 u_projTrans;
uniform sampler2D u_texture;
uniform float u_globalTime;
uniform float u_ratio;

void main()
{
	vec2 p = vTexCoord0 - vec2(0.5, 0.75);
	p.y *= u_ratio;
	float f = smoothstep(-0.02, 0.02, sin(atan(p.y,p.x)*25.0 + u_globalTime*2.));
	gl_FragColor = vec4(mix( vec3(0.3906f, 0.582031f, 0.925781f), vec3(1., 1., 1.), f)*(1 + 0.5*length(p)*length(p)), 1.);
}