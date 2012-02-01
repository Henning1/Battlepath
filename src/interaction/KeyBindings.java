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
package interaction;

import java.awt.event.KeyEvent;

public class KeyBindings {
	public static int MOVE_LEFT = KeyEvent.VK_D;
	public static int MOVE_RIGHT = KeyEvent.VK_A;
	public static int MOVE_UP = KeyEvent.VK_W;
	public static int MOVE_DOWN = KeyEvent.VK_S;
	public static int SCROLL_RIGHT = KeyEvent.VK_D;
	public static int SCROLL_LEFT = KeyEvent.VK_A;
	public static int SCROLL_UP = KeyEvent.VK_W;
	public static int SCROLL_DOWN = KeyEvent.VK_S;
	public static final char ZOOM_IN = (char)-2;
	public static final char ZOOM_OUT = (char)-1;
}