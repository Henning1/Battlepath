 
 uniform vec2 pos[10];
 uniform float radius[10];
 uniform int number;
 
 void main(void) {
 	
 	if(number==0) {
 		gl_FragColor = gl_Color;
 	} else {
 	
 	
	 	float bestDistance = 1000000000.0;
	 	vec2 swCenter;
	 	float swRadius;
	 	
	 	for(int i=0;i<number;i++) {
	 		vec2 distVector = pos[i] - gl_FragCoord.xy;
	 		float squaredDistance = dot(distVector,distVector);
	 		if(squaredDistance < bestDistance) {
	 			bestDistance = squaredDistance;
	 			swCenter = pos[i];
	 			swRadius = radius[i];
	 		}
	 	}
	 		
	 	float distance = sqrt(bestDistance);
	 		
	 	if(distance <= swRadius) {
	 		float c = (gl_Color.x + gl_Color.y + gl_Color.z)/3.0;
	 		gl_FragColor = vec4(c,c,c,gl_Color.a);
	 	} else {
	 		gl_FragColor = gl_Color;
	 	}
	}


 }
 
 
