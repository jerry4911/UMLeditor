package UMLeditor;

import javax.swing.JToolBar;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ToolBar extends JToolBar{
	private int toolNumber = 6;
	private Canvas canvas;
	private ToolBtn[] btns;
	private ImageIcon[] unselected, selected;

	public ToolBar() {
		canvas = Canvas.getInstance();   // Canvas is singleton 
		setLayout(new GridLayout(toolNumber, 1, 0, 0));
		btns = new ToolBtn[toolNumber];
		selected = new ImageIcon[toolNumber];
		unselected = new ImageIcon[toolNumber];
		
		// this.setBackground(new Color(255, 255, 255));
		
		unselected[0] = new ImageIcon("img/select.png");
		selected[0] = new ImageIcon("img/un_select.png");
		btns[0] = new ToolBtn("select", unselected[0], new SelectMode(), 0);
		add(btns[0]);
		
		unselected[1] = new ImageIcon("img/association.png");
		selected[1] = new ImageIcon("img/un_association.png");
		btns[1] = new ToolBtn("association", unselected[1], new CreateLineMode("association"), 1);
		add(btns[1]);
		
		unselected[2] = new ImageIcon("img/generalization.png");
		selected[2] = new ImageIcon("img/un_generalization.png");
		btns[2] = new ToolBtn("generalization", unselected[2], new CreateLineMode("generalization"), 2);
		add(btns[2]);
		
		unselected[3] = new ImageIcon("img/composition.png");
		selected[3] = new ImageIcon("img/un_composition.png");
		btns[3] = new ToolBtn("composition", unselected[3], new CreateLineMode("composition"), 3);
		add(btns[3]);
		
		unselected[4] = new ImageIcon("img/class.png");
		selected[4] = new ImageIcon("img/un_class.png");
		btns[4] = new ToolBtn("class", unselected[4], new CreateObjectMode("class"), 4);
		add(btns[4]);
		
		unselected[5] = new ImageIcon("img/use_case.png");
		selected[5] = new ImageIcon("img/un_use_case.png");
		btns[5] = new ToolBtn("usecase", unselected[5], new CreateObjectMode("usecase"), 5);
		add(btns[5]);

	}
	public class ToolBtn extends JButton {
		private Mode mode;
		private int index;
		public ToolBtn(String text, ImageIcon icon, Mode mode, int index) {
			this.mode = mode;
			this.index = index;
			setToolTipText(text);
			setIcon(icon);
			setBackground(Color.WHITE);
			addActionListener(new toolBtnListener());
			setFocusable(false);
			setBorderPainted(false);
			setRolloverEnabled(true);
		}
		
		public class toolBtnListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				for(int i=0; i<btns.length; i++) {
					btns[i].setBackground(Color.WHITE);
					btns[i].setIcon(unselected[i]);
				}
				setBackground(Color.BLACK);
				setIcon(selected[index]);
				canvas.currentMode = mode;
				canvas.setCurrentMode();
				canvas.resetPort();
				canvas.repaint();
			}
		}
	}
}
