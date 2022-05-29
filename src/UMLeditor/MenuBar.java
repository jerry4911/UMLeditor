package UMLeditor;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

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
		
		menu_item = new JMenuItem("Delete");
		menu_item.setFont(big);
		menu_item.addActionListener(new DeleteListener());
		menu.add(menu_item);	
		
	}
	
	class GroupListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			group();
		}
		
		private void group() {
			if (canvas.shapes.size()==0)
				return;
			List<Shape> shapes = new ArrayList<Shape>();
			int x1 = Integer.MAX_VALUE;
			int y1 = Integer.MAX_VALUE;
			int x2 = Integer.MIN_VALUE;
			int y2 = Integer.MIN_VALUE;
			for(int i=0; i<canvas.shapes.size(); i++) {
				Shape shape = canvas.shapes.get(i);
				if (shape.selected) {
					shapes.add(shape);
					if (shape.start.x<x1) {
						x1 = shape.start.x;
					}
					if (shape.start.y<y1) {
						y1 = shape.start.y;
					}
					if (shape.start.x+shape.width>x2) {
						x2 = shape.start.x+shape.width;
					}
					if (shape.start.y+shape.height>y2) {
						y2 = shape.start.y+shape.height;
					}
				}
			}
			Group group = new Group(new Port(x1, y1), shapes, x2-x1, y2-y1);
			// remove shape
			for(int i=0; i<group.shapes.size(); i++) {
				group.shapes.get(i).setUnselected();
				canvas.shapes.remove(group.shapes.get(i));
			}
			group.setSelected();
			canvas.tempObj = group;
			canvas.addShape(group);
			canvas.repaint();
		}
	}
	
	class UngroupListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ungroup();
		}
		
		private void ungroup() {
			if (canvas.tempObj!=null && canvas.tempObj.getClass()==Group.class) {
				Group group = (Group)canvas.tempObj;
				canvas.shapes.remove(group);
				for(int i=0; i<group.shapes.size(); i++) {
					canvas.addShape(group.shapes.get(i));
				}
			}	
			canvas.repaint();
		}
	}
	
	class ChangeNameListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			changeNameForm();
		}
		private void changeNameForm() {
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
	}
	
	class newFileListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO：想要新的畫布
			canvas.reset();
		}
	}
	
	class DeleteListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			delete();
		}

		private void delete() {
			if (canvas.tempObj!= null) {
				canvas.shapes.remove(canvas.tempObj);
			}
			if (canvas.tempLine!= null) {
				canvas.lines.remove(canvas.tempLine);
				canvas.tempLine = null;
			}
			canvas.repaint();
		}
	}

	
}
