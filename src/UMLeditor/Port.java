package UMLeditor;

import java.awt.Graphics;

public class Port { //ÂIÂI
	int x;
	int y;
	private boolean visible, movable;
	
	public Port() {
        x = 0;
        y = 0;
        visible = false;
        movable = false;
    }
	
	public Port( int x, int y) {
        this.x = x;
        this.y = y;
        visible = false;
        movable = false;
    }
	
	public void paint(Graphics g) { 
		g.fillRect(x, y, 6, 6);
	}
	
}
