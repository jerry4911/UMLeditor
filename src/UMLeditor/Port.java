package UMLeditor;

import java.awt.Graphics;

public class Port { //ÂIÂI
	int x;
	int y;
	
	public Port() {
        x = 0;
        y = 0;
    }
	
	public Port( int x, int y) {
        this.x = x;
        this.y = y;
    }
	
	public void paint(Graphics g) { 
		g.fillRect(x, y, 6, 6);
	}
	
	public void setPos(int x, int y) {
		this.x= x;
		this.y = y;
	}
	
}
