package UMLeditor;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;

public class Group extends Shape {
	List<Shape> shapes = new ArrayList<Shape>();
	public final int portNum = 4; //四個連接點
	
	public Group() {
		initializePorts();
	}
	
	private void initializePorts() {
        ports = new Port[portNum];
        for (int i = 0; i < ports.length; i++)
        {
            ports[i] = new Port();
        }
    }
	
	protected void createPorts() {
		int[] xpoint = {x1, x2, x1, x2};
		int[] ypoint = {y1, y1, y2, y2};
		
		for(int i = 0; i < ports.length; i++) {
			Port port = new Port(xpoint[i], ypoint[i]);
			ports[i] = port;
		}
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
		    ((Graphics2D) g).draw(new RoundRectangle2D.Double(x1, y1, width, height, 10, 10));
		    ((Graphics2D) g).setStroke(line_type);
		}
	}

	@Override
	public void paintPort(Graphics g) {
		for(int i = 0; i < this.ports.length; i++) {
			this.ports[i].paint(g);
		}
		for(int i=0; i<shapes.size(); i++) {
			Shape shape = shapes.get(i);
			shape.paintPort(g);
		}
	}
	
	public void addShape(Shape s) {
		shapes.add(s);
	}
	
	@Override
	public void addXYOffset(int offset_x, int offset_y) {
		this.x1 += offset_x;
		this.x2 += offset_x;
		this.y1 += offset_y;
		this.y2 += offset_y;
		for(int i=0; i<shapes.size(); i++) {
			Shape shape = shapes.get(i);
			shape.addXYOffset(offset_x, offset_y);
		}
		this.createPorts();
	}

}
