package UMLeditor;

import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class Canvas extends JPanel { //畫布 -> 繪圖的區塊
	private static Canvas instance = null; //For singleton
	List<Shape> shapes = new ArrayList<Shape>();
	List<Line> lines = new ArrayList<Line>();
	private List<Port> ports = new ArrayList<Port>();
	Line tempLine = null;
	BasicObject tempObj = null;
	SelectArea area = null;
	private EventListener listener = null;
	protected Mode currentMode = null;
	
	/* Singleton design pattern */
	private Canvas() {
		// Exists only to defeat instantiation.
		setBackground(Color.WHITE);
	}
	
	public void reset() {
		instance = null;
	}
	public void addShape(Shape shape) {
		shapes.add(shape);
	}
	
	public void addLine(Line line) {
		lines.add(line);
	}

	public static Canvas getInstance() {
		if (instance == null) {
			instance = new Canvas();
		}
		return instance;
	}
	
	public void paint(Graphics g) {
		/* set canvas area */
		Dimension dim = getSize();
		g.setColor(Color.white);
		g.fillRect(0, 0, dim.width, dim.height);
		
		/* set painting color */
		g.setColor(new Color(35, 37, 37));
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(1));
		
		for (int i=0; i<shapes.size(); i++) {
			Shape shape = shapes.get(i);
			shape.paint(g);
			if (shape.name!="") {
				g.drawString(shape.name, shape.x1-shape.name.length()*3, shape.y1);
			}
			if (shape.selected) {
				for(int j=0; j<shape.ports.length; j++) {
					shape.ports[j].paint(g);
				}
			}
		}
		
		if(area!=null)
			area.paint(g);
		
		if(tempLine!=null)
			tempLine.paint(g);
		
		if(!ports.isEmpty()) {
			for (int i=0; i<ports.size(); i++)
				ports.get(i).paint(g);
		}
		for (int i=0; i<lines.size(); i++) {
			lines.get(i).paint(g);
			Port p1 = new Port(lines.get(i).x1,lines.get(i).y1);
			Port p2 = new Port(lines.get(i).x2,lines.get(i).y2);
			p1.paint(g);
			p2.paint(g);
		}
		
	}
	
	public void setCurrentMode() {
		removeMouseListener((MouseListener) listener);
		removeMouseMotionListener((MouseMotionListener) listener);
		listener = currentMode;
		addMouseListener((MouseListener) listener);
		addMouseMotionListener((MouseMotionListener) listener);
	}

	public void resetPort() {
		// TODO Auto-generated method stub
		ports = new ArrayList<Port>();
		for (int i=0; i<shapes.size(); i++) {
			Shape shape = shapes.get(i);
			shape.selected = false;
		}
	}
	
	public Shape findObj(int x, int y) {
		for (int i=0; i<shapes.size(); i++) {
			if (x>shapes.get(i).x1-shapes.get(i).width/2 && x<shapes.get(i).x2-shapes.get(i).width/2 &&
					y>shapes.get(i).y1-shapes.get(i).height/2 && y<shapes.get(i).y2-shapes.get(i).height/2) {
				return shapes.get(i);
			}
		}
		return null;
	}

	public void addPort(Port port) {
		ports.add(port);
	}

	public void changeObjName(String text) {
		if (tempObj!=null) {
			tempObj.name = text;
		}	
	}

}
