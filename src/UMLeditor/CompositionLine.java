package UMLeditor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;

public class CompositionLine extends Line {
	private int arrowW = 15, arrowH = 10;
	
	public CompositionLine(Port start, Port end) {		
		super(start, end);
	}

	@Override
	public void paint(Graphics g) {
		int x1 = ports[0].x;
		int y1 = ports[0].y;
		int x2 = ports[1].x;
		int y2 = ports[1].y;
		g.setColor(Color.BLACK);
		g.drawLine(x1, y1, x2, y2);
		
		int dx = x2 - x1, dy = y2 - y1;
		double D = Math.sqrt(dx*dx + dy*dy);
		double xm = D - arrowW, xn = xm, ym = arrowH, yn = -arrowH, x, xo, yo;
		double sin = dy/D, cos = dx/D;
		x = xm*cos - ym*sin + x1;
        ym = xm*sin + ym*cos + y1;
        xm = x;
        x = xn*cos - yn*sin + x1;
        yn = xn*sin + yn*cos + y1;
        xn = x;
        xo = xm + xn - x2;
        yo = ym + yn - y2;
        int[] xpoints = {x2, (int) xm, (int) xo, (int) xn};
        int[] ypoints = {y2, (int) ym, (int) yo, (int) yn};
        
        g.setColor(Color.WHITE);
        g.fillPolygon(xpoints, ypoints, 4);
        g.setColor(Color.BLACK);
        g.drawPolygon(xpoints, ypoints, 4);
        if (selected) {
			paintPort(g);
		}
	}
	
}
