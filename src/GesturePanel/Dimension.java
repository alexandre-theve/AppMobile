package GesturePanel;

import java.io.Serializable;

public class Dimension implements Serializable {
	public int left;
	public int right;
	public int top;
	public int bottom;
	
	public Dimension(int left, int top, int right, int bottom) {
		this.left = left;
		this.right = right;
		this.top = top;
		this.bottom = bottom;
	}
	
	public int getWidth() {
		return right - left;
	}
	
	public int getHeight() {
		return bottom - top;
	}
}
