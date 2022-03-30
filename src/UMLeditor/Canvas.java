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
import java.util.Collections;
import java.util.Comparator;
import java.util.EventListener;
import java.util.List;

public class Canvas extends JPanel { //畫布 -> 繪圖的區塊
	int minDepth = 0;
	int maxDepth = 0;
	private static Canvas instance = null; //For singleton
	List<Shape> shapes = new ArrayList<Shape>();
	List<Line> lines = new ArrayList<Line>();
	private List<Port> ports = new ArrayList<Port>();
	Line tempLine = null;
	Shape tempObj = null;
	SelectArea area = null;
	List<Shape> tempGroup = new ArrayList<Shape>();
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
		
		Collections.sort(shapes, new Comparator<Shape>(){
		     public int compare(Shape s1, Shape s2){
		         if(s1.depth == s2.depth)
		             return 0;
		         return s1.depth < s2.depth ? -1 : 1;
		     }
		});
//		int min = 99;
//		int max = 0;
		for (int i=0; i<shapes.size(); i++) {
			Shape shape = shapes.get(i);
//			if (shape.depth<min)
//				min = shape.depth;
//			if (shape.depth>max)
//				max = shape.depth;
			shape.paint(g);
			if (shape.selected) {
//				if (shape.getClass()==Group.class) {
//					Group group = (Group)shape;
//					for(int j=0; j<group.shapes.size(); j++) {
//						Shape s = group.shapes.get(j);
//						if (s.ports==null)
//							continue;
//						for(int k=0; k<s.ports.length; k++) {
//							s.ports[k].paint(g);
//						}
//					}
//				}
//				else {
//					if (shape.ports==null)
//						continue;
//					for(int j=0; j<shape.ports.length; j++) {
//						shape.ports[j].paint(g);
//					}
//				}	
				shape.paintPort(g);
			}
			if (shape.name!="") {
				g.drawString(shape.name, shape.x1-shape.name.length()*3+shape.width/2, shape.y1+shape.height/2);
			}
		}
//		minDepth = min;
//		maxDepth = max;
		
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
	
	public Shape findShape(int x, int y) {
		Collections.sort(shapes, new Comparator<Shape>(){
		     public int compare(Shape s1, Shape s2){
		    	 if(s1.depth < s2.depth)
		    		 return -1;
		    	 else if(s1.depth > s2.depth)
		    		 return 1;
		    	 else return 0;
		     }
		});
		Shape selectedShape = null;
		for (int i=0; i<shapes.size(); i++) {
			System.out.println(shapes.get(i));
			if (x>shapes.get(i).x1 && x<shapes.get(i).x2 &&
					y>shapes.get(i).y1 && y<shapes.get(i).y2) {
				selectedShape = shapes.get(i);
			}
		}
		return selectedShape;
	}

	public void addPort(Port port) {
		ports.add(port);
	}

	public void changeObjName(String text) {
		if (tempObj!=null&& tempObj.getClass()!=Group.class) {
			tempObj.name = text;
		}	
	}
//	public void sortShapes(List<Shape> shapes) {
//		for(int i=0; i<shapes.size(); i++) {
//			
//		}
//	}

}
