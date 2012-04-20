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
package editor;

import util.Vector2D;
import engine.GlobalInfo;
import game.Core;
import game.Session;

/**
 * @author henning
 *
 */
public class EditorSession implements Session {

	int index = 0;
	Brush brush; 
	double moved = Double.MAX_VALUE;
	Vector2D lastPaint = GlobalInfo.nullVector.copy();
	
	@Override
	public void initialize() {
		brush = (Brush) new CircleBrush();
		Core.useSelectionRect = false;
	}

	/* (non-Javadoc)
	 * @see game.Session#step(double)
	 */
	@Override
	public void step(double dt) {
		moved = Core.input.getCursorPos().distance(lastPaint);
		
		if(Core.input.mouseButtonHold[0] && moved > 0.1) {
			brush.paint(Core.field, Core.input.getCursorPos());
			lastPaint = Core.input.getCursorPos();
		}
		
	}
	
	public Brush getBrush() {
		return brush;
	}

	/* (non-Javadoc)
	 * @see game.Session#processKey(int)
	 */
	@Override
	public void processKey(int key) {
		switch(key) {
		case 'b':
			if(index == 0) {
				brush = new SmoothenBrush();
				index = 1;
			}
			else if(index == 1) {
				brush = new CircleBrush();
				index = 0;
			}
		break;
		case 'i':
			if(brush.filltype == 1)
				brush.filltype = 0;
			else if(brush.filltype == 0)
				brush.filltype = 1;
		break;
		case '+':
			brush.size += 0.05;
		break;
		case '-':
			brush.size -= 0.05;
			if(brush.size < 0.2) brush.size = 0.2;
		break;
		}
		
		
	}

	/* (non-Javadoc)
	 * @see game.Session#processInputState(double)
	 */
	@Override
	public void processInputState(double dt) {
		// TODO Auto-generated method stub
		
	}

}
