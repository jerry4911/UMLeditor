package UMLeditor;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MenuBar extends JMenuBar{
	private Canvas canvas;
	public MenuBar() {
		canvas = Canvas.getInstance();   // Canvas is singleton 
		JMenu menu;
		JMenuItem menu_item;
		
		Font big = new Font("Verdana", Font.BOLD, 15);
		/* --- File menu --- */
		menu = new JMenu("<html><p style='margin-left:25;margin-right:25'>File");
		menu.setFont(big);
		add(menu);
		
		menu_item = new JMenuItem("New File");
		menu_item.setFont(big);
		menu_item.addActionListener(new newFileListener());
		menu.add(menu_item);

		/* --- Edit menu --- */
		menu = new JMenu("<html><p style='margin-left:25;margin-right:25'>Edit");
		menu.setFont(big);
		add(menu);
		
		menu_item = new JMenuItem("Group");
		menu_item.setFont(big);
		menu_item.addActionListener(new GroupListener());
		menu.add(menu_item);
		
		menu_item = new JMenuItem("Ungroup");
		menu_item.setFont(big);
		menu_item.addActionListener(new UngroupListener());
		menu.add(menu_item);
		
		menu_item = new JMenuItem("Change Object Name");
		menu_item.setFont(big);
		menu_item.addActionListener(new ChangeNameListener());
		menu.add(menu_item);	
		
	}
	
	class GroupListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			group();
		}
	}
	
	class UngroupListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			ungroup();
		}
	}
	
	class ChangeNameListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			changeNameForm();
		}
	}
	
	class newFileListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// TODO：想要新的畫布
		}
	}
	
	public void changeNameForm() {
		JFrame inputTextFrame = new JFrame("Change Object Name");
		inputTextFrame.setSize(400, 100);
		inputTextFrame.getContentPane().setLayout(new GridLayout(0, 1));
		
		JPanel panel = null;
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JTextField Text =  new JTextField("Object Name");
		panel.add(Text);
		inputTextFrame.getContentPane().add(panel);
		
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JButton confirm = new JButton("OK");
		panel.add(confirm);
		
		JButton cancel = new JButton("Cancel");
		panel.add(cancel);

		inputTextFrame.getContentPane().add(panel);
		inputTextFrame.setLocationRelativeTo(null);
		inputTextFrame.setVisible(true);
		
		confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.changeObjName(Text.getText());
				inputTextFrame.dispose();
				canvas.repaint();
			}
		});
		
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inputTextFrame.dispose();
			}
		});
		
	}

	public void ungroup() {
		if (canvas.tempObj!=null && canvas.tempObj.getClass()==Group.class) {
			Group group = (Group)canvas.tempObj;
			canvas.shapes.remove(group);
			for(int i=0; i<group.shapes.size(); i++) {
				canvas.addShape(group.shapes.get(i));
			}
		}	
		canvas.repaint();
	}

	public void group() {
		if (canvas.shapes.size()==0)
			return;
		Group group = new Group();
		Port g_P1 = null;
		Port g_P2 = null;
		for(int i=0; i<canvas.shapes.size(); i++) {
			Shape shape = canvas.shapes.get(i);
			if (shape.selected==true) {
//				if (shape.getClass()==Group.class) {
//					return;
//				}
				if (g_P1==null || g_P2==null) {
					g_P1 = new Port(shape.x1, shape.y1);
					g_P2 = new Port(shape.x2, shape.y2);
				}
				else {
					if (g_P1.x>shape.x1) {
						g_P1.x = shape.x1;
					}
					if (g_P1.y>shape.y1) {
						g_P1.y = shape.y1;
					}
					if (g_P2.x<shape.x2) {
						g_P2.x = shape.x2;
					}
					if (g_P2.y<shape.y2) {
						g_P2.y = shape.y2;
					}
				}
				group.addShape(shape);
				for(int j=0; j<shape.ports.length; j++) {
					canvas.addPort(shape.ports[j]);
				}
			}
		}
		// remove shape
		for(int i=0; i<group.shapes.size(); i++) {
			group.shapes.get(i).selected = false;
			canvas.shapes.remove(group.shapes.get(i));
		}
		group.selected = true;
		group.x1 = g_P1.x;
		group.y1 = g_P1.y;
		group.x2 = g_P2.x;
		group.y2 = g_P2.y;
		group.width = group.x2-group.x1;
		group.height = group.y2-group.y1;
		group.createPorts();
		canvas.addShape(group);
		canvas.repaint();
	}
}
