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
package engine;

import java.util.Arrays;

public class MapCodec {
	
	static byte[] magic = {(byte)0xde, (byte)0xfe, (byte)0xa7, (byte)0xed, (byte)0xbe, (byte)0xef};

	public static byte[] encode(Field f) {
		int tilesX = f.getTilesX();
		int tilesY = f.getTilesY();
		
		byte[] mapname  = new byte[32];
		byte[] tmp = f.getName().getBytes();
		System.arraycopy(tmp, 0, mapname, 0, tmp.length);
		
		byte[] dimensions = new byte[4];
		dimensions[0] =(byte)((tilesX << 16) >> 24);
		dimensions[1] =(byte)((tilesX << 24) >> 24);
		dimensions[2] =(byte)((tilesY << 16) >> 24);
		dimensions[3] =(byte)((tilesY << 24) >> 24);
		
		byte[] map = new byte[tilesX*tilesY];
		Tile[][] tiles = f.getTiles();
		
		int i = 0;
		for(int y=0; y<tilesY; y++) {
			for(int x=0;x<tilesX; x++) {
				map[i] = (byte)tiles[x][y].getValue();
				i++;
			}
		}
		
		byte[] encodedmap = new byte[map.length/2];
		
		for(int j = 0; j < map.length; j+=2) {
			encodedmap[j/2] = (byte) (map[j] << 4);
			encodedmap[j/2] = (byte) (encodedmap[j/2] | map[j+1]);
		}
		
		byte[] result = new byte[magic.length+mapname.length+dimensions.length+encodedmap.length];
		System.arraycopy(magic, 0, result, 0, 6);
		System.arraycopy(mapname, 0, result, 6, 32);
		System.arraycopy(dimensions, 0, result, 38, 4);
		System.arraycopy(encodedmap, 0, result, 42, encodedmap.length);
		
		return result;
	}
	
	public static Field decode(byte[] data) {
		
		byte[] data_magic = new byte[6];
		System.arraycopy(data, 0, data_magic, 0, 6);
		if(!Arrays.equals(data_magic, magic)) {
			System.out.println("Invalid map file, aborting.");
			return null;
		}
		
		byte[] tmpname = Arrays.copyOfRange(data, 6, 38);
		String name = new String(tmpname);
		
		byte[] tmpx = Arrays.copyOfRange(data, 38, 40);
		byte[] tmpy = Arrays.copyOfRange(data, 40, 42);
		int width = (int)tmpx[0] << 8 | (int)tmpx[1];
		int height = (int)tmpy[0] << 8 | (int)tmpy[1];
		
		Field f = new Field(width, height, name);
		
		int header = 42;
		int i = 0;
		for(int y = 0; y < height; y++) {
			for(int x = 0; x< width; x+=2) {
				byte b = data[i+header];
				f.tileAt(x, y).setValue(b>>4);
				f.tileAt(x+1, y).setValue(b & 0x0f);
				i++;
			}
		}
		
		return f;
	}
}
