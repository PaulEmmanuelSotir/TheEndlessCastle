varying vec2 vTexCoord0;
varying vec4 vColor;
varying vec4 vCoord;

uniform mat4 u_projTrans;
uniform sampler2D u_texture;
uniform float u_globalTime;
uniform float u_ratio;

#define time u_globalTime*0.1

float hash21(in vec2 n){ return fract(sin(dot(n, vec2(12.9898, 4.1414))) * 43758.5453); }
mat2 makem2(in float theta){ float c = cos(theta); float s = sin(theta); return mat2(c,-s,s,c); }
float noise( in vec2 x ){ return texture2D(u_texture, x*.01).a; }

vec2 gradn(vec2 p)
{
	float ep = .09;
	float gradx = noise(vec2(p.x+ep,p.y))-noise(vec2(p.x-ep,p.y));
	float grady = noise(vec2(p.x,p.y+ep))-noise(vec2(p.x,p.y-ep));
	return vec2(gradx,grady);
}

float flow(in vec2 p)
{
	float z=2.;
	float rz = 0.;
	vec2 bp = p;
	
	for (float i= 1.;i < 7.;i++ )
	{
		//primary flow speed
		p -= time*.6;
		
		//secondary flow speed (speed of the perceived flow)
		bp -= time*1.9;
		
		//displacement field (try changing time multiplier)
		vec2 gr = gradn(i*p*.34+time*1.);
		
		//rotation of the displacement field
		gr*=makem2(time*6.-(0.05*p.x+0.03*p.y)*40.);
		
		//displace the system
		p += gr*.5;
		
		//add noise octave
		rz+= (sin(noise(p)*7.)*0.5+0.5)/z;
		
		//blend factor (blending displaced system with base system)
		//you could call this advection factor (.5 being low, .95 being high)
		p = mix(bp,p,.77);
		
		//intensity scaling
		z *= 1.4;
		//octave scaling
		p *= 2.;
		bp *= 1.9;
	}
	return rz;	
}

void main()
{
	//	TODO: prendre en compte la résolution pour conserver un ratio 1:1 sur la texture
	vec2 p = vec2(vTexCoord0.x, -(vTexCoord0.y + 1.)*u_ratio);
	p *= 5.;
	float rz = flow(p);
	
	gl_FragColor = vec4( pow(vec3(.2,0.07,0.01)/rz, vec3(1.4)) , 1.0);
//	gl_FragColor = vec4( vTexCoord0 , 0.0, 1.0);
//	gl_FragColor = texture2D(u_texture, p);
}