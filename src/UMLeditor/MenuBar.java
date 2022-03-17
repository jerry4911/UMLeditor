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
		menu.add(menu_item);
		
		menu_item = new JMenuItem("Ungroup");
		menu_item.setFont(big);
		menu.add(menu_item);
		
		menu_item = new JMenuItem("Change Object Name");
		menu_item.setFont(big);
		menu_item.addActionListener(new ChangeNameListener());
		menu.add(menu_item);	
		
	}
	
	class ChangeNameListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			changeNameForm();
		}
	}
	
	class newFileListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
//			canvas.reset();
//			canvas.repaint();
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
}
