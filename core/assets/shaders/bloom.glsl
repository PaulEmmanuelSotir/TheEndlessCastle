varying vec2 vTexCoord0;
varying vec4 vColor;
varying vec4 vCoord;

uniform mat4 u_projTrans;
uniform sampler2D u_texture;
uniform float u_ratio;

float Threshold = 0.0+0.25*1.0;
float Intensity = 2.0-0.45*2.0;
float BlurSize = (6.0-0.40*6.0);

//float Threshold = 0.0+0.35*1.0;
//float Intensity = 2.0-0.45*2.0;
//float BlurSize = (6.0-0.60*6.0);

vec4 BlurColor (in vec2 Coord, in sampler2D Tex, in float MipBias)
{
	vec2 TexelSize = MipBias/vec2(1920, 1920*u_ratio);
    
    vec4  Color = texture2D(Tex, Coord, MipBias);
	float a = Color.a;
	
	Color += texture2D(Tex, Coord + vec2(TexelSize.x,0.0), MipBias);
	Color += texture2D(Tex, Coord + vec2(-TexelSize.x,0.0), MipBias);
	Color += texture2D(Tex, Coord + vec2(0.0,TexelSize.y), MipBias);
	Color += texture2D(Tex, Coord + vec2(0.0,-TexelSize.y), MipBias);
	Color += texture2D(Tex, Coord + vec2(TexelSize.x,TexelSize.y), MipBias);
	Color += texture2D(Tex, Coord + vec2(-TexelSize.x,TexelSize.y), MipBias);
	Color += texture2D(Tex, Coord + vec2(TexelSize.x,-TexelSize.y), MipBias);
	Color += texture2D(Tex, Coord + vec2(-TexelSize.x,-TexelSize.y), MipBias);
	
	return vec4(Color.xyz, a)/9.0;
}


void main()
{    
    vec4 Color = texture2D(u_texture, vTexCoord0);
    vec4 Highlight = clamp(BlurColor(vTexCoord0, u_texture, BlurSize)-Threshold,0.0,1.0)*1.0/(1.0-Threshold);
    gl_FragColor = 1.0-(1.0-Color)*(1.0-Highlight*Intensity);
}