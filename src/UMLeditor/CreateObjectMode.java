package UMLeditor;

import java.awt.event.MouseEvent;

public class CreateObjectMode extends Mode {
	private String objType = null;
	
	public CreateObjectMode(String objType) {
		this.objType = objType;
	}
	public void mousePressed(MouseEvent e) {
		BasicObject basicObj = null;
		Port start_P = new Port(e.getPoint().x, e.getPoint().y);
		if (objType.equals("usecase")) {
			basicObj = new UsecaseObject(start_P);
			basicObj.depth = canvas.minDepth;
		}
		else if (objType.equals("class")) {
			basicObj = new ClassObject(start_P);
			basicObj.depth = canvas.minDepth;
		}
		System.out.println(basicObj.depth);
		canvas.minDepth++;
		canvas.addShape(basicObj);
		canvas.repaint();
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
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
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}