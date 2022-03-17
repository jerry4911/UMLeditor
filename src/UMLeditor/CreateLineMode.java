package UMLeditor;

import java.awt.Point;
import java.awt.event.MouseEvent;

public class CreateLineMode extends Mode {
	private String lineType = "";
	private Point start_P, end_P = null;
	private BasicObject startObj, endObj = null;
	public CreateLineMode() {
		
	}
	public CreateLineMode(String lineType) {
		this.lineType = lineType;
	}
	public void mousePressed(MouseEvent e) {
		start_P = new Point(e.getPoint().x, e.getPoint().y);
		Point nearest_p = null;
		startObj = null;
		for(int i=0; i<canvas.shapes.size(); i++) {
			startObj = (BasicObject) canvas.shapes.get(i);
			// 點擊在圖形裡面
			if ( start_P.x>startObj.x1-startObj.width/2 && start_P.x<startObj.x2-startObj.width/2 && 
					start_P.y>startObj.y1-startObj.height/2 && start_P.y<startObj.y2-startObj.height/2 ) {
				// 找到最近的連接點
				nearest_p = new Point(startObj.ports[0].x, startObj.ports[0].y);
				double distance = Math.pow(startObj.ports[0].x-start_P.x,2)+Math.pow(startObj.ports[0].y-start_P.y,2);
				for (int j=1; j<startObj.portNum; j++) {
					double dis_temp = Math.pow(startObj.ports[j].x-start_P.x,2)+Math.pow(startObj.ports[j].y-start_P.y,2);
					if (distance>dis_temp) {
						nearest_p.x =  startObj.ports[j].x;
						nearest_p.y =  startObj.ports[j].y;
						distance = dis_temp;
					}
				}
				start_P = nearest_p;
				break;
			}		
		}
		if (nearest_p ==null)
			start_P = null;
	}

	public void mouseDragged(MouseEvent e) {
		/* show dragged line */
		if (start_P != null) {
			Line line = null;
			if (lineType.equals("generalization")) {
				line = new GeneralizationLine(start_P.x, start_P.y, e.getPoint().x, e.getPoint().y);
			}
			else if (lineType.equals("association")) {
				line = new AssociationLine(start_P.x, start_P.y, e.getPoint().x, e.getPoint().y);
			}
			else if (lineType.equals("composition")) {
				line = new CompositionLine(start_P.x, start_P.y, e.getPoint().x, e.getPoint().y);
			}
			canvas.tempLine = line;
			canvas.repaint();
		}
	}

	public void mouseReleased(MouseEvent e) {
		end_P = null;
		if (start_P != null) {
			end_P = new Point(e.getPoint().x, e.getPoint().y);
			// 也是找最近的點
			Point nearest_p = null;
			endObj = null;
			for(int i=0; i<canvas.shapes.size(); i++) {
				endObj = (BasicObject) canvas.shapes.get(i);
				if ( end_P.x>endObj.x1-endObj.width/2 && end_P.x<endObj.x2-endObj.width/2 && 
						end_P.y>endObj.y1-endObj.height/2 && end_P.y<endObj.y2-endObj.height/2 ) {
					// 找到最近的連接點
					nearest_p = new Point(endObj.ports[0].x, endObj.ports[0].y);
					double distance = Math.pow(endObj.ports[0].x-end_P.x,2)+Math.pow(endObj.ports[0].y-end_P.y,2);
					for (int j=1; j<endObj.portNum; j++) {
						double dis_temp = Math.pow(endObj.ports[j].x-end_P.x,2)+Math.pow(endObj.ports[j].y-end_P.y,2);
						if (distance>dis_temp) {
							nearest_p.x =  endObj.ports[j].x;
							nearest_p.y =  endObj.ports[j].y;
							distance = dis_temp;
						}
					}
					end_P = nearest_p;
					break;
				}
				
			}
			if (nearest_p ==null)
				end_P = null;
			
			Line line = null;
			if (end_P != null && (end_P.x!=start_P.x || end_P.y!=start_P.y)) {
				if (lineType.equals("generalization")) {
					line = new GeneralizationLine(start_P.x, start_P.y, end_P.x, end_P.y);
				}
				else if (lineType.equals("association")) {
					line = new AssociationLine(start_P.x, start_P.y, end_P.x, end_P.y);
				}
				else if (lineType.equals("composition")) {
					line = new CompositionLine(start_P.x, start_P.y, end_P.x, end_P.y);
				}
			}
			if (line != null) {
				line.startObj = startObj;
				line.endObj = endObj;
				canvas.addLine(line);
			}
			// reset
			canvas.tempLine = null;
			canvas.repaint();
			start_P = null;
			end_P = null;
		}
	}
}
