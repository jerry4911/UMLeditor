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
	Line tempLine = null;
	Shape tempObj = null;
	SelectArea tempArea = null;
	private EventListener listener = null;
	private Mode currentMode = null;
	
	private Canvas() {
		setBackground(Color.WHITE);
	}
	public static Canvas getInstance() {
		if (instance == null) {
			instance = new Canvas();
		}
		return instance;
	}
	
	public void reset() {
		// instance = new Canvas();
	}
	
	public void addShape(Shape shape) {
		shapes.add(shape);
	}
	
	public void addLine(Line line) {
		lines.add(line);
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

		for (int i=0; i<shapes.size(); i++) {
			Shape shape = shapes.get(i);
			shape.paint(g);
			if (shape.selected) {
				shape.paintPort(g);
			}
			if (shape.name!="") {
				g.drawString(shape.name, shape.start.x-shape.name.length()*3+shape.width/2, shape.start.y+shape.height/2);
			}
		}
		
		if(tempArea!=null)
			tempArea.paint(g); // Temp selected Area
		
		if(tempLine!=null)
			tempLine.paint(g); // Temp Line
		
		for (int i=0; i<lines.size(); i++) {
			Line line = lines.get(i);
			line.paint(g);
			if (line.selected) {
				line.paintPort(g);
			}
		}
		
	}
	
	public void setCurrentMode(Mode mode) {
		currentMode = mode;
		
		removeMouseListener((MouseListener) listener);
		removeMouseMotionListener((MouseMotionListener) listener);
		listener = currentMode;
		addMouseListener((MouseListener) listener);
		addMouseMotionListener((MouseMotionListener) listener);
	}

	public void resetPort() {
		for (int i=0; i<shapes.size(); i++) {
			shapes.get(i).setUnselected();
		}
	}
	
	public Shape findShape(int p_x, int p_y) {
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
//			System.out.println(shapes.get(i));
			Shape shape = shapes.get(i);
			int x = shape.start.x;
			int y = shape.start.y;
			if (p_x>x && p_x<x+shape.width &&
					p_y>y && p_y<y+shape.height) {
				selectedShape = shapes.get(i);
			}
		}
		return selectedShape;
	}
	
	public Line findLine(int p_x, int p_y) {
		Line selectedLine = null;
		for (int i=0; i<lines.size(); i++) {
			Line line = lines.get(i);
			int x1 = line.ports[0].x;
			int y1 = line.ports[0].y;
			int x2 = line.ports[1].x;
			int y2 = line.ports[1].y;
			
			if (x1==x2) {
				if (p_x==x1 ) {
					selectedLine = lines.get(i);
				}
			}
			else{
				if ((p_y-y1)==(y1-y2)*(p_x-x1)/(x1-x2)) {
					if((p_x>=x1 && p_x<=x2) || (p_x>=x2 && p_x<=x1))
						selectedLine = lines.get(i);
				}
			}
		}
		return selectedLine;
	}


	public void changeObjName(String text) {
		if (tempObj!=null&& tempObj.getClass()!=Group.class) {
			tempObj.name = text;
		}	
	}

}
