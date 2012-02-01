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
package game;

import java.awt.Dimension;
import java.awt.Point;

import engine.GlobalInfo;
import entities.Entity;


import util.Rectangle2D;
import util.Transition;
import util.Util;
import util.Vector2D;

public class View {
	
	public Dimension windowSize;
	Game game;
	int tileSize;
	public double zoom = 1;
	public Vector2D velocity = new Vector2D(0,0);
	boolean autonomous = false;
	Entity followedEntity;
	
	Transition zoomTransition;
	private double zoomSetTime;
	private boolean zoomFinished = true;
	
	Transition offsetTransitionX, offsetTransitionY;
	private double offsetSetTime;
	private boolean smoothOffsetFinished = true;
	
	public Vector2D offset;
	
	public View(Dimension size, int tileSize, Game g) {
		windowSize = size;
		offset = new Vector2D(0,0);
		this.tileSize = tileSize;
		game = g;
		
		zoomTransition = new Transition(1, 1, 1, 0, Transition.type.EASEINOUT);
		offsetTransitionX = new Transition(0, 0, 1, 0, Transition.type.EASEINOUT);
		offsetTransitionY = new Transition(0, 0, 1, 0, Transition.type.EASEINOUT);
	}
	
	public Vector2D viewSize() {
		return new Vector2D(windowSize.width/(tileSize*zoom), windowSize.height/(tileSize*zoom));
	}
	
	public Vector2D fieldSizeInView() {
		Vector2D fieldSize = new Vector2D(game.field.getTilesX(), game.field.getTilesY());
		return fieldSize;
		
	}
	
	public Vector2D viewToWorld(Point viewPos) {
		Vector2D v = new Vector2D(viewPos.x/((double)tileSize*zoom),
				(windowSize.height-viewPos.y)/((double)tileSize*zoom));
		return v.subtract(offset);
	}
	
	public Vector2D viewToWorld(int x, int y) {
		return viewToWorld(new Point(x,y));
	}
	
	public Point worldToViewShader(Vector2D worldPos) {
		Vector2D viewpos = worldPos.add(offset).scalar(tileSize*zoom);
		return new Point((int)viewpos.x,(int)viewpos.y);
	}
	
	
	public Point worldToView(Vector2D worldPos) {
		Vector2D viewpos = worldPos.add(offset).scalar(tileSize*zoom);
		return new Point((int)viewpos.x,windowSize.height-(int)viewpos.y);
	}
	
	private void setOffset(Vector2D pOffset) {
		this.offset = getValidOffset(pOffset);
	}
	
	private Vector2D getValidOffset(Vector2D pOffset) {
		double maxOffsX = -game.field.getTilesX()+(viewSize().x);
		double maxOffsY = -game.field.getTilesY()+(viewSize().y);

		Vector2D newOffset = pOffset.copy();
		if(newOffset.x >= 0)
			newOffset.x = 0;
		if(newOffset.x <= maxOffsX)
			newOffset.x = maxOffsX;
		
		if(newOffset.y >= 0)
			newOffset.y = 0;
		if(newOffset.y <= maxOffsY)
			newOffset.y = maxOffsY;
		
		Vector2D viewsize = viewSize();
		Vector2D fieldsize = fieldSizeInView();
		
		if(viewsize.x > fieldsize.x) {
			newOffset.x = (viewsize.x-fieldsize.x)/2;
		}
		if(viewsize.y > fieldsize.y) {
			newOffset.y = (viewsize.y-fieldsize.y)/2;	
		}
		return newOffset;
	}
	
	public Rectangle2D getScreenRect() {
		return new Rectangle2D(viewToWorld(0,0), viewToWorld(windowSize.width,windowSize.height));
	}
	

	public void zoom(double zoom, boolean smooth) {
		if(!Util.isValueInBounds(0.2, zoom, 3)) return;
		if(smooth) {
			if(zoomFinished) {
				zoomSetTime = GlobalInfo.time; 
				zoomTransition.setType(Transition.type.EASEINOUT);
				zoomTransition.setStartValue(this.zoom);
				zoomTransition.setEndValue(zoom);
				zoomTransition.setDuration(1);
				zoomFinished = false;
			}
			else if(!zoomFinished) {
				/*zoomTransition.setStartValue(this.zoom);
				zoomTransition.setEndValue(zoom);
				zoomTransition.setType(Transition.type.INTERMEDIARY);
				zoomTransition.setStartSpeed(zoomTransition.getSpeed(GlobalInfo.time-zoomSetTime));*/
			}
		}
		else {
			setZoom(zoom);
		}
	}
	
	public void smoothOffset(Vector2D pOffset) {
		Vector2D targetOffset = getValidOffset(pOffset.negate().add(viewSize().scalar(0.5)));
		offsetSetTime = GlobalInfo.time;
		offsetTransitionX.setStartValue(offset.x);
		offsetTransitionX.setEndValue(targetOffset.x);
		offsetTransitionX.setDuration(offset.distance(targetOffset)/50);
		offsetTransitionY.setStartValue(offset.y);
		offsetTransitionY.setEndValue(targetOffset.y);
		offsetTransitionY.setDuration(offset.distance(targetOffset)/50);
		smoothOffsetFinished = false;
	}
	
	private void setZoom(double pZoom) {
		Vector2D center = this.viewToWorld(windowSize.width/2, windowSize.height/2);
		zoom = pZoom;
		center(center);
	}
	
	public void follow(Entity entity) {
		if(entity != followedEntity) {
			autonomous = true;
			followedEntity = entity;
			smoothOffset(entity.pos);
		}
	}
	
	public void unfollow() {
		autonomous = false;
		followedEntity = null;
	}	
	
	public void center(Vector2D point) {
		setOffset(point.negate().add(viewSize().scalar(0.5)));
	}
	
	public double getTargetZoom() {
		return zoomTransition.getEndValue();
	}
	
	public void process(double dt) {
		if(!zoomFinished) {
			setZoom(zoomTransition.get(GlobalInfo.time-zoomSetTime));
			zoomFinished = (zoom == zoomTransition.getEndValue());
		}
		
		if(!smoothOffsetFinished) {
			offset.x = offsetTransitionX.get(GlobalInfo.time-offsetSetTime);
			offset.y = offsetTransitionY.get(GlobalInfo.time-offsetSetTime);
			
			smoothOffsetFinished = (offset.x == offsetTransitionX.getEndValue() && offset.y == offsetTransitionY.getEndValue());
		}
		
		if(autonomous && smoothOffsetFinished) {
			center(followedEntity.pos);
		} 
		else if(smoothOffsetFinished){
			setOffset(offset.subtract(velocity.scalar(dt)));
		}
	}

}
