import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class TextEditor implements ActionListener{
	JFrame frame;
	JTextArea area;
	// JTextPane area;
	JMenu fileMenu;
	JMenuItem newPage;
	JFileChooser fsave;
	String mList, path;
	int pageNumber = 0;
	boolean alSaved = false;

	String[] menus = {"File", "Edit", "Format", "View", "Help"};
	char[] mAlias = { 'F', 'E', 'O', 'V', 'H'};
	String[][] menumenu = {
		{"  New     ", "  Open...     ", "  Save   ", "  Save As...     ", "  Page Setup...     ", "  Print...     ", "  Exit     "},
		{"  Undo     ", "  Cut     ", "  Copy     ","  Paste     ","  Delete             Del","  Find...     ","  Find Next           F3", "  Replace...     ", "  Goto...     ","  Select All        ", "  Time/Date           F5"},
		{"  Word Wrap    ", "  Font...     "},
		{"  Status Bar    "},
		{"  View Help     ", "  About Notepad     "}
	};
	String[][] menuAlias = {
		{"N", "O","S", "","","P",""},
		{"Z", "X","C", "X","Del","F","F3","H", "G", "A", "F5"},
		{"", ""}, {""}, {"", ""}
	};
	
	public TextEditor(){
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // To close the console window on exit of the window
		frame.setLocation(new Point(400, 100)); // To set the default location to anywhere on the window
		frame.setSize(new Dimension(500, 300));
		frame.setTitle("Text Frame"); // To set the title of the window;
		
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("textEditor.png"));
		frame.setLayout(new BorderLayout());
		area = new JTextArea(25, 50);
		area.setFont(new Font("Serif", Font.BOLD, 14));
		frame.add(new JScrollPane(area));
		
		JMenuBar menuContainer = new JMenuBar(); // the bar where the menu can be placed
		
		for (int ii= 0; ii < menus.length; ii++) {
			
			fileMenu = new JMenu(menus[ii]);
			fileMenu.setMnemonic(mAlias[ii]);

			for (int ss=0; ss< menumenu[ii].length; ss++) {
				int aliaslength = menuAlias[ii][ss].length();
				if(aliaslength == 1 ){
					char myalias = menuAlias[ii][ss].charAt(0);	
					newPage = new JMenuItem(menumenu[ii][ss]); // the bar where the menu can be placed
					newPage.setAccelerator(KeyStroke.getKeyStroke(menuAlias[ii][ss].charAt(0), KeyEvent.CTRL_DOWN_MASK));
					// newPage.setAccelerator(KeyStroke.getKeyStroke(myalias, Toolkit.getDefaultToolkit( ).getMenuShortcutKeyMask( ), false));
					// newPage.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.ALT_DOWN_MASK));
					// newPage.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.SHIFT_MASK|ActionEvent.ALT_MASK));
					newPage.addActionListener(this);
					fileMenu.add(newPage);
					menuContainer.add(fileMenu);

				} else {
					if (menuAlias[ii][ss] == "Del") {
						newPage = new JMenuItem("  Delete"); // the bar where the menu can be placed
						newPage.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
						newPage.addActionListener(this);
						fileMenu.add(newPage);
						menuContainer.add(fileMenu);	
					} else if (menuAlias[ii][ss] == "F3") {
						newPage = new JMenuItem("  Find Next"); // the bar where the menu can be placed
						newPage.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0));
						newPage.addActionListener(this);
						fileMenu.add(newPage);
						menuContainer.add(fileMenu);	
					} else if(menuAlias[ii][ss] == "F5"){
						newPage = new JMenuItem("  Time/Date  "); // the bar where the menu can be placed
						newPage.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
						// newPage = new JMenuItem(menumenu[ii][ss], 'N'); // the bar where the menu can be placed
					 	newPage.addActionListener(this);
						fileMenu.add(newPage);
						menuContainer.add(fileMenu);
					} else {
						newPage = new JMenuItem(menumenu[ii][ss]); // the bar where the menu can be placed
						newPage.addActionListener(this);
						fileMenu.add(newPage);
						menuContainer.add(fileMenu);
					}
				}	
			}
		}
		frame.add(menuContainer, BorderLayout.NORTH);	
		frame.pack();
		frame.setVisible(true);
		
	}

	public void saveFile(boolean savingAs){	
		fsave  = new JFileChooser();
		if (savingAs) {
			fsave.setDialogTitle("Save As");
			int option = fsave.showSaveDialog(fsave);
			if (option == JFileChooser.APPROVE_OPTION){
				try{
					BufferedWriter out = new BufferedWriter(new FileWriter(fsave.getSelectedFile().getPath()));
					out.write(area.getText());
					out.close();
					frame.setTitle(fsave.getSelectedFile().getPath() + "- Editor");
					path = fsave.getSelectedFile().getPath();
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		} else {
			try{
				BufferedWriter out = new BufferedWriter(new FileWriter(path));
				out.write(area.getText());
				out.close();
			}catch(Exception ex){
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
		}	
	}

	// public void openNewTab(){
		// JTabbedPane tabbedPane = new JTabbedPane();
  //       tabbedPane.setName("New Editor");
  //       tabbedPane.setInheritsPopupMenu(true);
// 	singlePanel = new JPanel(new CenterLayout());
//         singlePanel.setName("ColorChooser.panel");
//         singlePanel.setInheritsPopupMenu(true);

// 	chooser.setLayout( new BorderLayout() );
	// }

	@Override
	public void actionPerformed(ActionEvent e){
		String com = e.getActionCommand().toString();
		String mText = area.getText();
		if (com.trim().equals("New")) {
			System.out.println(alSaved + " Before alSaved");
			if (alSaved) {
				area.setText("");
				frame.setTitle("Untitled - Text Frame");
				path = null;
				alSaved = false;		
			} else {
				if (mText.isEmpty()) {
					frame.setTitle("Untitled - Text Frame");
				} else {
					System.out.println(path);
					int option = JOptionPane.showConfirmDialog(null, "Do you wish to save this document? ", "Warning", JOptionPane.YES_NO_CANCEL_OPTION);
					if(option == 0) {
						saveFile(true);
					} else if(option == 1){
						area.setText("");
						frame.setTitle("Untitled - Text Frame");
				// 		pageNumber++;
					}
				}
			}
		} else if (com.trim().equals("Open...")) {
			JFileChooser fopen  = new JFileChooser();
			fopen.setDialogTitle("Open");
			int option = fopen.showOpenDialog(frame);
			// int option = fopen.showOpenDialog(fopen);
			int fileToOpen = option;
			JFileChooser fileOpen = fopen;
			if (fileToOpen == JFileChooser.APPROVE_OPTION){
				area.setText("");
				try{
					Scanner scan = new Scanner(new FileReader(fileOpen.getSelectedFile().getPath()));
					while(scan.hasNext()){
						area.append(scan.nextLine()+"\n");
					}
					frame.setTitle(fileOpen.getSelectedFile().getPath() + "- Editor");
					path = fileOpen.getSelectedFile().getPath();
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}		
			
		} else if (com.trim().equals("Save")) {
			if(path == null){
				saveFile(true);
				alSaved = true;
			} else {
				saveFile(false);
				alSaved = true;
			}
			
		} else if(com.trim().equals("Save As...")){
			saveFile(true);
		} else if(com.trim().equals("Help")){
			JOptionPane.showConfirmDialog(null, "Go online fot help contents", "Warning", JOptionPane.YES_NO_CANCEL_OPTION);
		}
	}


	public static void main(String[] args){
		TextEditor txt = new TextEditor();
	}
}
