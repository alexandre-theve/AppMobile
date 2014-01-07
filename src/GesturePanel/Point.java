package GesturePanel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Point extends android.graphics.Point implements Serializable {

	public Point() {
	        super();
	}

	 
	public Point(int _x, int _y) {
		super(_x, _y);
	}

	public Point(Point src) {
        super(src);
    }

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private void writeObject(ObjectOutputStream out) throws IOException {
        // appel des m�canismes de s�rialisation par d�faut
        out.defaultWriteObject();
        
        // on s�rialise les attributs normalement non s�rialis�s
        out.writeInt(x); 
        out.writeInt(y); 
	}
	
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
	        // appel des m�canismes de d�s�rialisation par d�faut
	        in.defaultReadObject();
	
	        // on d�s�rialise les attributs normalement non d�s�rialis�s
	        x = in.readInt(); 
	        y = in.readInt(); 
	}
	
	@Override
	public boolean equals(Object point) {
	        
	        if (point instanceof Point) {
	                if ( this.x == ((Point) point).x && this.y == ((Point) point).y ) {
	                        return true;
	                }
	        }
	        
	        return false;           
	}
	
	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
}
