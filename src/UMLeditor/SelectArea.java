package UMLeditor;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;

public class SelectArea extends Shape {

	public SelectArea(Port start, int width, int height) {
		super(start, width,  height);
	}
	
	@Override
	public void paint(Graphics g) {
		final float dash[] = {5.0f};
	    final BasicStroke dashed = new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
	                        BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
	    ((Graphics2D) g).setStroke(dashed);
	    ((Graphics2D) g).draw(new RoundRectangle2D.Double(start.x, start.y, width, height, 10, 10));
	}

}
