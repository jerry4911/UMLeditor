package UMLeditor;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

public class SelectMode extends Mode {
	private Point start_P, end_P = null;
	private Point g_start_P = null;
	private int offset_x, offset_y;
	private Shape selectedObj = null;
	
	public void mousePressed(MouseEvent e) {
		selectedObj = canvas.findShape(e.getPoint().x, e.getPoint().y);
		System.out.print("Choose¡G");
		System.out.println(selectedObj);
		start_P = new Point(e.getPoint().x, e.getPoint().y);
		canvas.resetPort();
		if( selectedObj!=null ) {
			if (selectedObj.getClass()==Group.class) {
				Group group = (Group)selectedObj;
				group.selected = true;
			}
			canvas.tempObj = selectedObj;
			canvas.area = null;
			selectedObj.selected = true;
		}
		else {
			canvas.tempObj = null;
			g_start_P = new Point(start_P.x, start_P.y);
			for (int i=0; i<canvas.shapes.size(); i++) {
				canvas.shapes.get(i).selected = false;
			}			
		}
		canvas.repaint();
	}

	public void mouseDragged(MouseEvent e) {
		end_P = new Point(e.getPoint().x, e.getPoint().y);
		offset_x = end_P.x-start_P.x;
		offset_y = end_P.y-start_P.y;
		start_P.x = e.getPoint().x;
		start_P.y = e.getPoint().y;
		canvas.resetPort();
		if (selectedObj != null) {
			List<Shape> inside_shapes = new ArrayList<Shape>();
			if (selectedObj.getClass()==Group.class) {
				Group group = (Group)selectedObj;
				inside_shapes = group.shapes;
				group.x1+=offset_x;
				group.y1+=offset_y;
				group.x2+=offset_x;
				group.y2+=offset_y;
				group.createPorts();
				group.selected = true;
			}
			else {
				inside_shapes.add(selectedObj);
			}
//			System.out.println(inside_shapes.size());
			for(int i=0; i<inside_shapes.size(); i++) {
				Shape insideObj = inside_shapes.get(i);
//				insideObj.x1+=offset_x;
//				insideObj.y1+=offset_y;
//				insideObj.x2+=offset_x;
//				insideObj.y2+=offset_y;
				insideObj.addXYOffset(offset_x, offset_y);
//				insideObj.addPortOffset(offset_x, offset_y);
				
				for(int j=0; j<insideObj.ports.length; j++) {
					canvas.addPort(insideObj.ports[j]);
				}
				for(int j=0; j<canvas.lines.size(); j++) {
					Line line = canvas.lines.get(j);
					if (line.startObj==insideObj) {
						line.x1+=offset_x;
						line.y1+=offset_y;
					}
					if (line.endObj==insideObj) {
						line.x2+=offset_x;
						line.y2+=offset_y;
					}
				}
			}		
		}
		else {
			if (canvas.area==null)
				canvas.area = new SelectArea(g_start_P.x, g_start_P.y, end_P.x-g_start_P.x, end_P.y-g_start_P.y);
			else {
				canvas.area.x2+=offset_x;
				canvas.area.y2+=offset_y;
				canvas.area.width+=offset_x;
				canvas.area.height+=offset_y;
			}
		}
		canvas.repaint();
	}

	public void mouseReleased(MouseEvent e) {
		end_P = new Point(e.getPoint().x, e.getPoint().y);
		canvas.area = null;
		canvas.repaint();
		if (selectedObj == null) {
			for(int i=0; i<canvas.shapes.size(); i++) {
				Shape shape = canvas.shapes.get(i);
				if ( shape.x1>g_start_P.x && shape.x2<end_P.x && 
						shape.y1>g_start_P.y && shape.y2<end_P.y ) {
					shape.selected = true;
				}
				else {
					shape.selected = false;
				}
			}
		}
		else {
//			if (selectedObj.getClass()==Group.class) {
//				Group group = (Group)selectedObj;
//				group.selected = false;
//			}
		}
	}
}
