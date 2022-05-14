package UMLeditor;

import java.awt.Graphics;

public abstract class BasicObject extends Shape {
	private int offset = 6;

	protected BasicObject(Port start, int width, int height) {
		super(start, width, height);
		for(int i = 0; i < ports.length; i++) {
			ports[i].setPos(ports[i].x-offset/2, ports[i].y-offset/2);
		}
		ports[0].setPos(ports[0].x+width/2, ports[0].y);
		ports[1].setPos(ports[1].x, ports[1].y+height/2);
		ports[2].setPos(ports[2].x, ports[2].y-height/2);
		ports[3].setPos(ports[3].x-width/2, ports[3].y);
	}
	
	public abstract void paint(Graphics g);
	
}
