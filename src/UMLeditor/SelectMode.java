package UMLeditor;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

public class SelectMode extends Mode {
	private Port start_P, end_P = null;
	private int offset_x, offset_y;
	private Shape selectedObj = null;
	
	public void mousePressed(MouseEvent e) {
		selectedObj = canvas.findShape(e.getPoint().x, e.getPoint().y);
		Line selectedLine = canvas.findLine(e.getPoint().x, e.getPoint().y);
//		System.out.print("Choose¡G");
//		System.out.println(selectedObj);
		start_P = new Port(e.getPoint().x, e.getPoint().y);
		canvas.resetPort();
		
		if( selectedObj!=null ) {
			canvas.tempObj = selectedObj;
			canvas.tempArea = null;
			selectedObj.setSelected();
		}
		else if (selectedLine!=null) {
			canvas.tempLine = selectedLine;
			selectedLine.setSelected();
		}
		else {
			canvas.tempLine = null;
			for (int i=0; i<canvas.lines.size(); i++) {
				canvas.lines.get(i).setUnselected();
			}	
			canvas.tempObj = null;
			for (int i=0; i<canvas.shapes.size(); i++) {
				canvas.shapes.get(i).setUnselected();
			}		
		}
		canvas.repaint();
	}

	public void mouseDragged(MouseEvent e) {
		end_P = new Port(e.getPoint().x, e.getPoint().y);

		offset_x = end_P.x-start_P.x;
		offset_y = end_P.y-start_P.y;

		if (selectedObj != null) {
			selectedObj.addXYOffset(offset_x, offset_y);
			for(int i=0; i<canvas.lines.size(); i++) {
				Line line = canvas.lines.get(i);
				if (line.startObj.selected) {
					line.ports[0].setPos(line.ports[0].x+offset_x, line.ports[0].y+offset_y);
				}
				if (line.endObj.selected) {
					line.ports[1].setPos(line.ports[1].x+offset_x, line.ports[1].y+offset_y);
				}
			}	
			start_P.setPos(e.getPoint().x, e.getPoint().y);
		}
		else {
			int x = start_P.x;
			int y = start_P.y;
			if (x>end_P.x) {
				x = end_P.x;
			}
			if (y>end_P.y) {
				y = end_P.y;
			}		
			canvas.tempArea = new SelectArea(new Port(x, y), Math.abs(end_P.x-start_P.x), Math.abs(end_P.y-start_P.y));
		}
		canvas.repaint();
	}

	public void mouseReleased(MouseEvent e) {
		end_P = new Port(e.getPoint().x, e.getPoint().y);
		canvas.tempArea = null;
		canvas.repaint();
		offset_x = end_P.x-start_P.x;
		offset_y = end_P.y-start_P.y;
		if (offset_x<0) {
			// swap end_P.x¡Bstart_P.x
			end_P.setPos(start_P.x, start_P.y);
			start_P.setPos(start_P.x+offset_x, start_P.y);
		}
		if (offset_y<0) {
			// swap end_P.y¡Bstart_P.y
			end_P.setPos(end_P.x, start_P.y);
			start_P.setPos(start_P.x, start_P.y+offset_y);
		}
		
		if (selectedObj == null) {
			for(int i=0; i<canvas.shapes.size(); i++) {
				Shape shape = canvas.shapes.get(i);
				int x = shape.start.x;
				int y = shape.start.y;
				if ( x>=start_P.x && x+shape.width<=end_P.x && 
						y>=start_P.y && y+shape.height<=end_P.y ) {
					shape.setSelected();
				}
				else {
					shape.setUnselected();
				}
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
