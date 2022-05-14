package UMLeditor;

import java.awt.Color;
import java.awt.Graphics;

public class UsecaseObject extends BasicObject{
	public UsecaseObject(Port start) {
		super(start, 120, 90); // width:120, height:90
	}
	
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillOval(start.x, start.y, width, height);
		g.setColor(Color.BLACK);
		g.drawOval(start.x, start.y, width, height);
		if (selected) {
			paintPort(g);
		}
	}

}
