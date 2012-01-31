/**
 * Copyright (c) 2011-2012 Henning Funke.
 * 
 * This file is part of Battlepath.
 *
 * Battlepath is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * Battlepath is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */ 
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
 
 
