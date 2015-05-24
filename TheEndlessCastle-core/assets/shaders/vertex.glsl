//vertex attributes
attribute vec4 a_position;
attribute vec4 a_color; 
attribute vec2 a_texCoord0;

//projection matrix
uniform mat4 u_projTrans;

//attributes sent to frag shader
varying vec2 vTexCoord0;
varying vec4 vCoord;
varying vec4 vColor;

void main() {	
	vColor = a_color;
	vCoord = a_position;
	vTexCoord0 = a_texCoord0;
	gl_Position = u_projTrans * a_position;
}