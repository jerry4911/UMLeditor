package UMLeditor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;

public class GeneralizationLine extends Line {
	private int arrowW = 20, arrowH = 10;
	public GeneralizationLine(int x1, int y1, int x2, int y2) {		
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawLine(x1, y1, x2, y2);
		
		int dx = x2 - x1, dy = y2 - y1;
		double D = Math.sqrt(dx*dx + dy*dy);
		double xm = D - arrowW, xn = xm, ym = arrowH, yn = -arrowH, x;
		double sin = dy/D, cos = dx/D;
		x = xm*cos - ym*sin + x1;
        ym = xm*sin + ym*cos + y1;
        xm = x;
        x = xn*cos - yn*sin + x1;
        yn = xn*sin + yn*cos + y1;
        xn = x;
        int[] xpoints = {x2, (int) xm, (int) xn};
        int[] ypoints = {y2, (int) ym, (int) yn};
        
        g.setColor(Color.WHITE);
        g.fillPolygon(xpoints, ypoints, 3);
        g.setColor(Color.BLACK);
        g.drawPolygon(xpoints, ypoints, 3);
	}

	@Override
	public void paintPort(Graphics g) {
		// TODO Auto-generated method stub
		
	}
	
}
