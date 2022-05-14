package UMLeditor;

import java.awt.Color;
import java.awt.Graphics;

public class ClassObject extends BasicObject{
	
	public ClassObject(Port start) {
		super(start, 90, 120); // width:90, height:120
	}
	@Override
	public void paint(Graphics g) {
		int x1 = start.x;
		int y1 = start.y;

		g.setColor(Color.WHITE);
		g.fillRect(x1, y1, width, height);
		g.setColor(Color.BLACK);
		g.drawRect(x1, y1, width, height);
		int gap = height / 3;
		g.drawLine(x1, y1 + gap, x1+width, y1 + gap);
		g.drawLine(x1, y1 + gap * 2, x1+width, y1 + gap * 2);
		if (selected) {
			paintPort(g);
		}
	}
}
