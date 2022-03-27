package UMLeditor;

import java.awt.event.MouseEvent;

public class CreateObjectMode extends Mode {
	private String objType = null;
	
	public CreateObjectMode() {
		
	}
	public CreateObjectMode(String objType) {
		this.objType = objType;
	}
	public void mousePressed(MouseEvent e) {
		BasicObject basicObj = null;
		if (objType.equals("usecase")) {
			basicObj = new UsecaseObject(e.getPoint().x, e.getPoint().y);
			basicObj.depth = canvas.minDepth;
		}
		else if (objType.equals("class")) {
			basicObj = new ClassObject(e.getPoint().x, e.getPoint().y);
			basicObj.depth = canvas.minDepth;
		}
		canvas.addShape(basicObj);
		canvas.repaint();
	}
}