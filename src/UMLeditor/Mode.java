package UMLeditor;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public abstract class Mode implements MouseListener, MouseMotionListener{
	protected String modeDescription;
	protected Canvas canvas = Canvas.getInstance();
	
	public Mode() {
        modeDescription = "";
    }
	
	public Mode(String description) {
        modeDescription = description;
    }
}
