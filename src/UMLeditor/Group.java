package UMLeditor;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;

public class Group extends Shape {
	List<Shape> shapes;
	
	public Group(Port start, List<Shape> shapes, int width, int height) {
		super(start, width, height);
		this.shapes = shapes;
	}
	
	@Override
	public void paint(Graphics g) {
		for(int i=0; i<shapes.size(); i++) {
			shapes.get(i).paint(g);
		}
		if(selected) {
			final float dash[] = {5.0f};
		    final BasicStroke dashed = new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
		                        BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
		    Stroke line_type = ((Graphics2D) g).getStroke();
		    ((Graphics2D) g).setStroke(dashed);
		    ((Graphics2D) g).draw(new RoundRectangle2D.Double(start.x, start.y, width, height, 10, 10));
		    ((Graphics2D) g).setStroke(line_type);
		    paintPort(g);
		}
	}

	@Override
	public void addXYOffset(int offset_x, int offset_y) {
		for(int i=0; i<ports.length; i++) {
			int x = ports[i].x;
			int y = ports[i].y;
			ports[i].setPos(x+offset_x, y+offset_y);
		}
		for(int i=0; i<shapes.size(); i++) {
			Shape shape = shapes.get(i);
			shape.addXYOffset(offset_x, offset_y);
		}
		start.setPos(start.x+offset_x, start.y+offset_y);
	}
	
	@Override
	public void setSelected() {
		selected = true;
		for(int i=0; i<shapes.size(); i++) {
			shapes.get(i).setSelected();
		}
	}
	
	@Override
	public void setUnselected() {
		for(int i=0; i<shapes.size(); i++) {
			shapes.get(i).setUnselected();
		}
	}
}
