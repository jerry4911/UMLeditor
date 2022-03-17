package UMLeditor;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;

public class SelectArea extends Shape {
	public SelectArea() {
		
	}
	public SelectArea(int x, int y, int width, int height) {
		this.width = width;
		this.height = height;
		this.x1 = x;
		this.y1 = y;
		this.x2 = x + width;
		this.y2 = y + height;
	}
	@Override
	public void paint(Graphics g) {
		final float dash[] = {5.0f};
	    final BasicStroke dashed = new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
	                        BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
	    ((Graphics2D) g).setStroke(dashed);
	    ((Graphics2D) g).draw(new RoundRectangle2D.Double(x1, y1, width, height, 10, 10));
	}

	@Override
	public void paintPort(Graphics g) {
		
	}

}
