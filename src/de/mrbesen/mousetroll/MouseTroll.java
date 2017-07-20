package de.mrbesen.mousetroll;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;

public class MouseTroll {
	
	private static int rad = 100;//radius

	public static void main(String[] args) {
		try {
			Robot bot = new Robot();

			// get the screen higth / width (used later)
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			int width = (int) screenSize.getWidth();
			int height = (int) screenSize.getHeight();
//			System.out.println("MAX: " + width + " " + height);
					
			long last = 1; // last time the mouse was in the top left screen corner (used later)

			int step = 0;
			double stepsize = Math.PI / 100;
			
			int oldrot[] = {rad,0};
			
			
			boolean run = true;
			while( run ) {
				Point p = MouseInfo.getPointerInfo().getLocation();//start Point
				
				int rot[] = {
						(int) (Math.cos(stepsize * step) * rad),
						(int) (Math.sin(stepsize * step) * rad)
				};
			
				bot.mouseMove((int) ((rot[0] - oldrot[0]) + p.x),(int) ((rot[1] - oldrot[1]) + p.y));
				
				oldrot = rot.clone();
				
//				System.out.println(step + "   " + stepsize* step + "    "+ rot[0] + " " + rot[1]);
				
				step++;
				step %= 201;
				
				if(distance(p.x,0,p.y,0) < 10) {//top left
					last = System.currentTimeMillis();
				} else if(distance(p.x,width,p.y,height) < 10 & (System.currentTimeMillis() - last) < 5000) {//bottom right
					run = false;
				}
				
				try {
					Thread.sleep(5);
				} catch( InterruptedException e) {}
			}
		} catch(AWTException e) {
			e.printStackTrace();
		}
	}
	
	private static int distance(int xa, int xb, int ya, int yb) {
		return (int) (Math.sqrt(((xa-xb)*(xa-xb)) + ((ya-yb)*(ya-yb))));
	}
}