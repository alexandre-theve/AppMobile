package GesturePanel;

import model.Signature;
import android.app.Activity;
import android.content.Context;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGestureListener;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class Panel extends GestureOverlayView implements OnGestureListener, Callback{
	    private SurfaceView panel;
	    private Path path = new Path();
	    private Signature points;
	    private Paint paint = new Paint();
	    private float strokeWidth = 5f;
	    private DisplayMetrics metrics;
	    
        public Panel(Context context, AttributeSet attrs) {      
        	super(context,attrs);
        	Log.d("BIS", "instanciation panel");
            super.setGestureVisible(true);
	        paint.setStyle(Paint.Style.STROKE);
			paint.setColor(Color.BLACK);
			paint.setStrokeWidth(this.strokeWidth);

			if(!this.isInEditMode()){ // permet de visualiser le layout sur l'editeur eclipse sans erreur (car context n'existe pas en mode edition)
				metrics = new DisplayMetrics();
				((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
				
				points = new Signature();	
			}
			super.addOnGestureListener(this);
			//super.setGestureColor(Color.BLACK);
			panel = new SurfaceView(context);
			panel.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
			panel.getHolder().addCallback(this);
			
			super.addView(panel);
        }

        public void drawPath(final Path path) {
        	new Thread(){
        		public void run() {
        			synchronized (panel.getHolder()) {
        				Canvas c = panel.getHolder().lockCanvas();
            			if(c!=null){
            				c.drawColor(Color.WHITE);
                    		c.drawPath(path, paint);
                    		panel.getHolder().unlockCanvasAndPost(c);
                    	}
        			}
        		};
        	}.start();
        }
        
		public void onGesture(GestureOverlayView overlay, MotionEvent event) {        
			int x = (int) event.getX();
            int y = (int) event.getY();
            
            System.out.println("onGesture : " + x + " - " + y);

            points.add(new Point(x,y));
		}

		public void onGestureCancelled(GestureOverlayView overlay,
				MotionEvent event) {
		}

		public void onGestureEnded(GestureOverlayView overlay, MotionEvent event) {
            points.add(null);
            
            path.addPath(overlay.getGesture().getStrokes().get(0).getPath());
			drawPath(path);
		}

		public void onGestureStarted(GestureOverlayView overlay,
				MotionEvent event) {
			int x = (int) event.getX();
            int y = (int) event.getY();
            
            System.out.println("Gesture Started:" + x + " - " + y);
            
            points.add(new Point(x,y));
		}

		public void init(){
			synchronized (panel.getHolder()) {
				Canvas c = panel.getHolder().lockCanvas();
				if(c!=null){
					c.drawColor(Color.WHITE);
					drawPath(path);
					panel.getHolder().unlockCanvasAndPost(c);
				}
			}
		}
		
		public void clear() {
			System.out.println("Clear");
			points.clear();
			path = new Path();
			synchronized (panel.getHolder()) {
				Canvas c = panel.getHolder().lockCanvas();
				if(c!=null){
					c.drawColor(Color.WHITE);
					panel.getHolder().unlockCanvasAndPost(c);
				}
			}
	    }
		
		public Signature getSignature(){
			System.out.println("Signature : " + points);
			return points;
		}
		
		public Canvas getCanvas(){
			synchronized (panel.getHolder()) {
				return panel.getHolder().lockCanvas();
			}
		}

		public void setSignature(Signature signature) {
			if(signature!=null){
				path = new Path();
				Point oldp = null;
				Point oldp2 = null;
				Point oldp3 = null;
				for(Point p : signature){
					if(p!=null && oldp != null && oldp2 != null && oldp3 != null){
						System.out.println("Cubic curve from : " + oldp3 + " to " + p);
						// TODO : produit en croix pour redimensionner la signature : 
						// path.moveTo(oldp3.x * metrics.widthPixels / signature.getDimension().getWidth(), oldp3.y * metrics.heightPixels / signature.getDimension().getHeight());
						path.moveTo(oldp3.x, oldp3.y);
						path.cubicTo(oldp2.x, oldp2.y, oldp.x, oldp.y, p.x, p.y);
						oldp3 = null;
						oldp2 = null;
						oldp = null;
					}
					oldp3 = oldp2;
					oldp2 = oldp;
					oldp = p;
				}
				drawPath(path);
				points = signature;
				//Const.iLog("Signature : " + points);
			}
		}

		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
		}

		public void surfaceCreated(SurfaceHolder holder) {
			this.init();
		}

		public void surfaceDestroyed(SurfaceHolder holder) {
		}
	}