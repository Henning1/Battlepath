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
	
	double oldZoom = 1;
	double targetZoom = 1;
	double zoomSetTime;
	private boolean zoomFinished = true;
	
	Vector2D oldOffset;
	Vector2D targetOffset;
	double offsetSetTime;
	boolean smoothOffsetFinished = true;
	double smoothOffsetDuration;
	public Vector2D offset;
	
	public View(Dimension size, int tileSize, Game g) {
		windowSize = size;
		offset = new Vector2D(0,0);
		this.tileSize = tileSize;
		game = g;
		
	}
	
	public Vector2D viewSize() {
		return new Vector2D(windowSize.width/(tileSize*zoom), windowSize.height/(tileSize*zoom));
	}
	
	public Vector2D fieldSizeInView() {
		Vector2D fieldSize = new Vector2D(game.field.tilesX, game.field.tilesY);
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
		double maxOffsX = -game.field.tilesX+(viewSize().x);
		double maxOffsY = -game.field.tilesY+(viewSize().y);

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
	

	public void zoom(double deltaZoom, boolean smooth) {
		targetZoom = Util.valueInBounds(0.2, targetZoom+deltaZoom, 3);
		if(smooth) {
			if(zoomFinished) zoomSetTime = GlobalInfo.time;
			oldZoom = zoom;
			zoomFinished = false;
		}
		else {
			setZoom(targetZoom);
		}
	}
	
	public void smoothOffset(Vector2D pOffset) {
		targetOffset = getValidOffset(pOffset.negate().add(viewSize().scalar(0.5)));
		oldOffset = offset.copy();
		offsetSetTime = GlobalInfo.time;
		smoothOffsetFinished = false;
		smoothOffsetDuration = oldOffset.distance(targetOffset)/50;
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
	
	public void process(double dt) {
		if(!zoomFinished) {
			setZoom(Transition.t(GlobalInfo.time-zoomSetTime, oldZoom, targetZoom, 0.5, Transition.type.EASEINOUT));
			zoomFinished = (zoom == targetZoom);
		}
		
		if(!smoothOffsetFinished) {
			offset.x = Transition.t(GlobalInfo.time-offsetSetTime, oldOffset.x, targetOffset.x, 
					smoothOffsetDuration, Transition.type.EASEINOUT);
			offset.y = Transition.t(GlobalInfo.time-offsetSetTime, oldOffset.y, targetOffset.y, 
					smoothOffsetDuration, Transition.type.EASEINOUT);
			
			smoothOffsetFinished = (offset.x == targetOffset.x && offset.y == targetOffset.y);
		}
		
		if(autonomous && smoothOffsetFinished) {
			center(followedEntity.pos);
		} 
		else if(smoothOffsetFinished){
			setOffset(offset.subtract(velocity.scalar(dt)));
		}
	}

}
