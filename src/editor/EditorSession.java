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

import interaction.KeyBindings;

import java.util.ArrayList;

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
	ArrayList<Brush> brushes = new ArrayList<Brush>(); 
	double moved = Double.MAX_VALUE;
	Vector2D lastPaint = GlobalInfo.nullVector.copy();
	
	@Override
	public void initialize() {
		brushes.add(new CircleBrush());
		brushes.add(new SquareBrush());
		brushes.add(new SmoothenBrush());
		
		Core.useSelectionRect = false;
	}

	/* (non-Javadoc)
	 * @see game.Session#step(double)
	 */
	@Override
	public void step(double dt) {
		moved = Core.input.getCursorPos().distance(lastPaint);
		
		if(Core.input.mouseButtonHold[0] && moved > 0.1) {
			brushes.get(index).paint(Core.field, Core.input.getCursorPos());
			lastPaint = Core.input.getCursorPos();
		}
		
	}
	
	public Brush getBrush() {
		return brushes.get(index);
	}

	/* (non-Javadoc)
	 * @see game.Session#processKey(int)
	 */
	@Override
	public void processKey(int key) {
		switch(key) {
		case 'b':
			nextBrush();
		break;
		case 'i':
			if(getBrush().filltype == 1)
				getBrush().filltype = 0;
			else if(getBrush().filltype == 0)
				getBrush().filltype = 1;
		break;
		case '+':
			getBrush().size += 0.1;
		break;
		case '-':
			getBrush().size -= 0.1;
			if(getBrush().size < 0.4) getBrush().size = 0.2;
		break;
		case KeyBindings.ZOOM_IN:
			if(Core.input.isPressed(17))
				nextBrush();
			break;
		case KeyBindings.ZOOM_OUT:
			if(Core.input.isPressed(17))
				previousBrush();
			break;
		}
	}
	
	private void nextBrush() {
		index++;
		index = index % brushes.size();
	}
	
	private void previousBrush() {
		index--;
		if(index < 0) index = brushes.size()-1;
	}

	/* (non-Javadoc)
	 * @see game.Session#processInputState(double)
	 */
	@Override
	public void processInputState(double dt) {
		// TODO Auto-generated method stub
		
	}

}
