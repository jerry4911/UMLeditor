package UMLeditor;

import java.awt.Color;
import java.awt.Graphics;

public class ClassObject extends BasicObject{
	public ClassObject() {
		
	}
	
	public ClassObject(int x, int y) {
		this.width = 90;
		this.height = 120;
		this.x1 = x;
		this.y1 = y;
		this.x2 = x + width;
		this.y2 = y + height;
		createPorts();
	}
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(x1-width/2, y1-height/2, width, height);
		g.setColor(Color.BLACK);
		g.drawRect(x1-width/2, y1-height/2, width, height);
		int gap = height / 3;
		g.drawLine(x1-width/2, y1-height/2 + gap, x2-width/2, y1-height/2 + gap);
		g.drawLine(x1-width/2, y1-height/2 + gap * 2, x2-width/2, y1-height/2 + gap * 2);
	}

	@Override
	public void paintPort(Graphics g) {
		for(int i = 0; i < this.ports.length; i++) {
			this.ports[i].paint(g);
		}	
	}
}
