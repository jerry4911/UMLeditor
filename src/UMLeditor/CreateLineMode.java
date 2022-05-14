package UMLeditor;

import java.awt.Point;
import java.awt.event.MouseEvent;

public class CreateLineMode extends Mode {
	private String lineType = "";
	private Port start_P, end_P = null;
	private BasicObject startObj, endObj = null;

	public CreateLineMode(String lineType) {
		this.lineType = lineType;
	}
	
	public void mousePressed(MouseEvent e) {
		start_P = new Port(e.getPoint().x, e.getPoint().y);
		Port nearest_p = null;
		startObj = null;
		
		for(int i=0; i<canvas.shapes.size(); i++) {
			if (canvas.shapes.get(i).getClass()==Group.class) { //如果是 Group就不能畫線
				continue;
			}
			startObj = (BasicObject) canvas.shapes.get(i);
			// 找出是點擊在哪一個BasicObject裡面
			if ( start_P.x>=startObj.start.x && start_P.x<=startObj.start.x+startObj.width && 
					start_P.y>=startObj.start.y && start_P.y<=startObj.start.y+startObj.height ) {
				// 找到最近的連接點
				nearest_p = new Port(startObj.ports[0].x, startObj.ports[0].y);
				double distance = Math.pow(startObj.ports[0].x-start_P.x,2)+Math.pow(startObj.ports[0].y-start_P.y,2);
				for (int j=1; j<startObj.portNum; j++) {
					double dis_temp = Math.pow(startObj.ports[j].x-start_P.x,2)+Math.pow(startObj.ports[j].y-start_P.y,2);
					if (distance>dis_temp) {
						nearest_p.setPos(startObj.ports[j].x, startObj.ports[j].y);
						distance = dis_temp;
					}
				}
				start_P = nearest_p; //找到某個BasicObject的最近點
				break;
			}		
		}
		if (nearest_p ==null)
			start_P = null;
	}

	public void mouseDragged(MouseEvent e) {
		end_P =  new Port(e.getPoint().x, e.getPoint().y);
		if (start_P != null) {
			Line line = null;
			if (lineType.equals("generalization")) {
				line = new GeneralizationLine(start_P, end_P);
			}
			else if (lineType.equals("association")) {
				line = new AssociationLine(start_P, end_P);
			}
			else if (lineType.equals("composition")) {
				line = new CompositionLine(start_P, end_P);
			}
			canvas.tempLine = line; //畫暫時的線
			canvas.repaint();
		}
	}

	public void mouseReleased(MouseEvent e) {
		end_P = null;
		if (start_P != null) {
			end_P = new Port(e.getPoint().x, e.getPoint().y);
			// 也是找最近的點
			Port nearest_p = null;
			endObj = null;
			for(int i=0; i<canvas.shapes.size(); i++) {
				if (canvas.shapes.get(i).getClass()==Group.class) {
					continue;
				}
				endObj = (BasicObject) canvas.shapes.get(i);
				if (endObj == startObj)
					continue;
				if ( end_P.x>=endObj.start.x && end_P.x<=endObj.start.x+endObj.width && 
						end_P.y>=endObj.start.y && end_P.y<=endObj.start.y+endObj.height ) {
					// 找到最近的連接點
					nearest_p = new Port(endObj.ports[0].x, endObj.ports[0].y);
					double distance = Math.pow(endObj.ports[0].x-end_P.x,2)+Math.pow(endObj.ports[0].y-end_P.y,2);
					for (int j=1; j<endObj.portNum; j++) {
						double dis_temp = Math.pow(endObj.ports[j].x-end_P.x,2)+Math.pow(endObj.ports[j].y-end_P.y,2);
						if (distance>dis_temp) {
							nearest_p.setPos(endObj.ports[j].x, endObj.ports[j].y);
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
			if (end_P != null) {
				if (lineType.equals("generalization")) {
					line = new GeneralizationLine(start_P, end_P);
				}
				else if (lineType.equals("association")) {
					line = new AssociationLine(start_P, end_P);
				}
				else if (lineType.equals("composition")) {
					line = new CompositionLine(start_P, end_P);
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
