package UMLeditor;

import java.awt.Color;
import java.awt.Graphics;

public class UsecaseObject extends BasicObject{
	public UsecaseObject() {
		
	}
	
	public UsecaseObject(int x, int y) {
		this.width = 120;
		this.height = 90;
		this.x1 = x;
		this.y1 = y;
		this.x2 = x + width;
		this.y2 = y + height;
		createPorts();
	}
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillOval(x1, y1, width, height);
		g.setColor(Color.BLACK);
		g.drawOval(x1, y1, width, height);
		if (selected) {
			paintPort(g);
		}
	}

	@Override
	public void paintPort(Graphics g) {
		for(int i = 0; i < this.ports.length; i++) {
			this.ports[i].paint(g);
		}	
	}
}
