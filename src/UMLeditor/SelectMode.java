package UMLeditor;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class SelectMode extends Mode {
	private Point start_P, end_P = null;
	private Point g_start_P = null;
	private int offset_x, offset_y;
	private BasicObject selectedObj = null;
	
	public void mousePressed(MouseEvent e) {
		selectedObj = (BasicObject)canvas.findObj(e.getPoint().x, e.getPoint().y);
		start_P = new Point(e.getPoint().x, e.getPoint().y);
		canvas.resetPort();
		if( selectedObj!=null ) {
			canvas.tempObj = selectedObj;
			canvas.area = null;
			selectedObj.selected = true;
		}
		else {
			canvas.tempObj = null;
			g_start_P = new Point(start_P.x, start_P.y);
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
			selectedObj.x1+=offset_x;
			selectedObj.y1+=offset_y;
			selectedObj.x2+=offset_x;
			selectedObj.y2+=offset_y;
			selectedObj.addOffset(offset_x, offset_y);
			for(int i=0; i<selectedObj.ports.length; i++) {
				canvas.addPort(selectedObj.ports[i]);
			}
			for(int i=0; i<canvas.lines.size(); i++) {
				Line line = canvas.lines.get(i);
				if (line.startObj==selectedObj) {
					line.x1+=offset_x;
					line.y1+=offset_y;
				}
				if (line.endObj==selectedObj) {
					line.x2+=offset_x;
					line.y2+=offset_y;
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
				if ( shape.x1-shape.width/2>g_start_P.x && shape.x2-shape.width/2<end_P.x && 
						shape.y1-shape.height/2>g_start_P.y && shape.y2-shape.height/2<end_P.y ) {
					shape.selected = true;
				}
				else {
					shape.selected = false;
				}
			}
		}
	}
}
