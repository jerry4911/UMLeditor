package UMLeditor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;

public class AssociationLine extends Line {
	private int arrowW = 20, arrowH = 10;
	
	public AssociationLine(Port start, Port end) {		
		super(start, end);
	}

	@Override
	public void paint(Graphics g) {
		int x1 = ports[0].x;
		int y1 = ports[0].y;
		int x2 = ports[1].x;
		int y2 = ports[1].y;
		g.setColor(Color.BLACK);
		g.drawLine(x1, y1, x2, y2); //主要的連接線
		
		// 畫箭頭
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
        
        g.drawLine((int)xm, (int)ym, x2, y2);
        g.drawLine((int)xn, (int)yn, x2, y2);
        if (selected) {
			paintPort(g);
		}
	}
	
}
