package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import GesturePanel.Dimension;
import GesturePanel.Point;


public class Signature extends ArrayList<Point> implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -994278603999388735L;
	private Dimension dimension;
	
	public Signature() {
		super();
		dimension = new Dimension(Integer.MAX_VALUE,Integer.MAX_VALUE,0,0);
	}
	
	public Signature(String signature){
		super();
		dimension = new Dimension(Integer.MAX_VALUE,Integer.MAX_VALUE,0,0);
		
		this.parseStringifiedSignature(signature);
	}
	
	public Signature(ArrayList<Point> points) {
		super();
		dimension = new Dimension(Integer.MAX_VALUE,Integer.MAX_VALUE,0,0);
		for (android.graphics.Point p : points) {
			add(p);
		}
	}

	public boolean add(android.graphics.Point p){
		if(p != null){
			if(p.x < dimension.left)
	    		dimension.left = p.x;
	    	if(p.y < dimension.top)
	    		dimension.top = p.y;
	    	if(p.x > dimension.right)
	    		dimension.right = p.x;
	    	if(p.y > dimension.bottom)
				dimension.bottom = p.y;	
		}
		if(p != null)
			return super.add(new Point(p.x, p.y));
		else
			return super.add(null);
	}
	
	public Dimension getDimension() {
		return dimension;
	}
	
	public void parseStringifiedSignature(String signature){
		if(signature!=null&&!signature.equals(""))
		{
			//Ajoute tous les levés de crayon dans les cases de traits
			ArrayList<String> traits = new ArrayList<String>();
			Collections.addAll(traits, signature.split("-")); 
			
			// pour chaque levé de crayon
			for(String trait : traits){
				ArrayList<String> points = new ArrayList<String>();
				Collections.addAll(points, trait.split(";")); // on ajoute tous les points du tracé dans points
				
				for(String point : points){
					String[] coordones = point.split(",");
					
					this.add(new Point(Integer.valueOf(coordones[0]), Integer.valueOf(coordones[1])));
				}
				this.add(null);
			}
		}
	}
	
	public String getStringifiedSignature(){
		Boolean first = true;
		String signature = "";
		
		Iterator<Point> it = this.iterator();
		while(it.hasNext()){
			Point next = it.next();
			if(next!=null){
				if(!first)
					signature+=";";
				else
					first=false;
				signature += next.x + ","+next.y;
			}
			else{
				if(it.hasNext()){
					signature += "-";
					first = true;
				}
			}
		}
		return signature;
	}
}
