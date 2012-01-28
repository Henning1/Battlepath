package util;

public class Rectangle2D {
	public Vector2D topleft;
	public Vector2D bottomright;
	
	public Rectangle2D(Vector2D topleft, Vector2D bottomright){
		this.topleft = topleft;
		this.bottomright = bottomright;
		
		if(topleft.x > bottomright.x) {
			this.topleft.x = bottomright.x;
			this.bottomright.x = topleft.x;
		}
		
		if(topleft.y < bottomright.y) {
			this.topleft.y = bottomright.y;
			this.bottomright.y = topleft.y;
		}
	}
	
	public double top() {return topleft.y;}
	public double bottom() {return bottomright.y;}
	public double right() {return bottomright.x;}
	public double left() {return topleft.x;}
	
	public boolean inside(Vector2D p) {
		return Util.isValueInBounds(topleft.x, p.x, bottomright.x) &&
				Util.isValueInBounds(bottomright.y, p.y, topleft.y);
	}
}