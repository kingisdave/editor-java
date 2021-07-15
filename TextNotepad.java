import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class TextNotepad implements ActionListener{
	JFrame frame;
	JTextArea area;
	// JTextPane area;
	JMenu fileMenu;
	JMenuItem newPage;
	String mList, path;
	JFileChooser fsave, fopen;
	int pageNumber;
	boolean checkSave = false;

	public TextNotepad(){
		frame = new JFrame();
		// frame.setForeground(Color.WHITE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // To close the console window on exit of the window
		frame.setLocation(new Point(400, 100)); // To set the default location to anywhere on the window
		frame.setSize(new Dimension(500, 300));
		frame.setTitle("Text Frame"); // To set the title of the window;
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("textEditor.png"));
		frame.setLayout(new BorderLayout());
		area = new JTextArea(25, 50);
		area.setFont(new Font("Serif", Font.BOLD, 14));
		// frame.add(area);
		frame.add(new JScrollPane(area));
		
		JMenuBar menuContainer = new JMenuBar(); // the bar where the menu can be placed
			
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		
		JMenuItem newPage = new JMenuItem("New"); // the items in each of the menu // the bar where the menu can be placed
		newPage.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
		newPage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (path == null ) {
                	checkSave = false;
					area.setText("");
					frame.setTitle("Untitled - Text Frame");
				} else {	
					if (checkSave) {
						area.setText("");
						frame.setTitle("Untitled - Text Frame");
					} else {
						int option = JOptionPane.showConfirmDialog(null, "Do you wish to save this document? ", "Warning", JOptionPane.YES_NO_CANCEL_OPTION);
						if(option == 0) {
							saveAsFile();
						} else if(option == 1){
							area.setText("");
							frame.setTitle("Untitled "+pageNumber+" - Text Frame");
							pageNumber++;
						}
					}
				}
            }
        });
		fileMenu.add(newPage);
		menuContainer.add(fileMenu);

		JMenuItem open = new JMenuItem("Open"); // the items in each of the menu // the bar where the menu can be placed
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
		open.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                fopen  = new JFileChooser();
                fopen.setDialogTitle("Open");
				int option = fopen.showSaveDialog(frame);
				if (option == JFileChooser.APPROVE_OPTION){
					area.setText("");
					try{
						Scanner scan = new Scanner(new FileReader(fopen.getSelectedFile().getPath()));
						while(scan.hasNext()){
							area.append(scan.nextLine()+"\n");
						}
						frame.setTitle(fopen.getSelectedFile().getPath() + "- Editor");
						path = fopen.getSelectedFile().getPath();
					}catch(Exception ex){
						JOptionPane.showMessageDialog(null, ex.getMessage());
					}
				}
            }
        });
		fileMenu.add(open);

		JMenuItem save = new JMenuItem("Save"); // the items in each of the menu // the bar where the menu can be placed
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
		save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	if (path == null) {
            		saveFile();
            	} else {
                	saveFile();

            	}
            }
        });
		fileMenu.add(save);

		JMenuItem saveAs = new JMenuItem("Save As"); // the items in each of the menu // the bar where the menu can be placed
		saveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
		saveAs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                saveAsFile();
            }
        });
		fileMenu.add(saveAs);

		JMenuItem pageSetup = new JMenuItem("Page Setup"); // the items in each of the menu // the bar where the menu can be placed
		// pageSetup.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
		pageSetup.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                
            }
        });
		fileMenu.add(pageSetup);

		JMenuItem printer = new JMenuItem("Print"); // the items in each of the menu // the bar where the menu can be placed
		printer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK));
		printer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                
            }
        });
		fileMenu.add(printer);

		JMenuItem exits = new JMenuItem("Exit"); // the items in each of the menu // the bar where the menu can be placed
		exits.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                
            }
        });
		fileMenu.add(exits);	
		menuContainer.add(fileMenu);

		JMenu editMenu = new JMenu("Edit");
		editMenu.setMnemonic(KeyEvent.VK_E);
		
		JMenuItem undo = new JMenuItem("Undo"); // the items in each of the menu // the bar where the menu can be placed
		undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK));
		undo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                
            }
        });
		editMenu.add(undo);

		JMenuItem cut = new JMenuItem("Cut"); // the items in each of the menu // the bar where the menu can be placed
		cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK));
		cut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                
            }
        });
		editMenu.add(cut);

		JMenuItem copy = new JMenuItem("Copy"); // the items in each of the menu // the bar where the menu can be placed
		copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
		copy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                
            }
        });
		editMenu.add(copy);

		JMenuItem paste = new JMenuItem("Paste"); // the items in each of the menu // the bar where the menu can be placed
		paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK));
		paste.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                
            }
        });
		editMenu.add(paste);

		JMenuItem delete = new JMenuItem("Delete"); // the items in each of the menu // the bar where the menu can be placed
		delete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
		delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                
            }
        });
		editMenu.add(delete);

		JMenuItem finder = new JMenuItem("Find..."); // the items in each of the menu // the bar where the menu can be placed
		finder.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_DOWN_MASK));
		finder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                
            }
        });
		editMenu.add(finder);

		JMenuItem findNext = new JMenuItem("Find Next"); // the items in each of the menu // the bar where the menu can be placed
		findNext.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0));
		findNext.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                
            }
        });
		editMenu.add(findNext);	

		JMenuItem replace = new JMenuItem("Replace"); // the items in each of the menu // the bar where the menu can be placed
		replace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, KeyEvent.CTRL_DOWN_MASK));
		replace.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                replace.setText("");
            }
        });
		editMenu.add(replace);

		JMenuItem goTo = new JMenuItem("Go To"); // the items in each of the menu // the bar where the menu can be placed
		goTo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, KeyEvent.CTRL_DOWN_MASK));
		goTo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                goTo.setText("");
            }
        });
		editMenu.add(goTo);

		JMenuItem selectAll = new JMenuItem("Select All"); // the items in each of the menu // the bar where the menu can be placed
		selectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK));
		selectAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                selectAll.setText("");
            }
        });
		editMenu.add(selectAll);

		JMenuItem timeDate = new JMenuItem("Time/Date"); // the items in each of the menu // the bar where the menu can be placed
		timeDate.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
		timeDate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                timeDate.setText("");
            }
        });
		editMenu.add(timeDate);	
		menuContainer.add(editMenu);


		JMenu formatMenu = new JMenu("Format");
		formatMenu.setMnemonic(KeyEvent.VK_F);
		
		JMenuItem wordWord = new JMenuItem("Word Warp"); // the items in each of the menu // the bar where the menu can be placed
		// wordWord.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK));
		wordWord.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                
            }
        });
		formatMenu.add(wordWord);

		JMenuItem fonter = new JMenuItem("Font"); // the items in each of the menu // the bar where the menu can be placed
		// fonter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK));
		fonter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                
            }
        });
		formatMenu.add(fonter);
		menuContainer.add(formatMenu);


		JMenu viewMenu = new JMenu("View");
		viewMenu.setMnemonic(KeyEvent.VK_F);
		
		JMenuItem statusBar = new JMenuItem("Status Bar"); // the items in each of the menu // the bar where the menu can be placed
		// statusBar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK));
		statusBar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                
            }
        });
		viewMenu.add(statusBar);
		menuContainer.add(viewMenu);


		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_F);
		
		JMenuItem viewHelp = new JMenuItem("View Help"); // the items in each of the menu // the bar where the menu can be placed
		// viewHelp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK));
		viewHelp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                
            }
        });
		helpMenu.add(viewHelp);

		JMenuItem aboutNote = new JMenuItem("About Notepad"); // the items in each of the menu // the bar where the menu can be placed
		// aboutNote.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK));
		aboutNote.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                
            }
        });
		helpMenu.add(aboutNote);
		menuContainer.add(helpMenu);
		

		frame.add(menuContainer, BorderLayout.NORTH);
		
		frame.pack();
		frame.setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e){
		
	}

	public void saveFile(){
		// fsave  = new JFileChooser();
		int option = fsave.showSaveDialog(fsave);
		if (option == JFileChooser.APPROVE_OPTION){

			try{
				BufferedWriter out = new BufferedWriter(new FileWriter(fsave.getSelectedFile().getPath()));
				out.write(area.getText());
				out.close();
				checkSave= true;
				// frame.setTitle(fsave.getSelectedFile().getPath() + "- Editor");
				// path = fsave.getSelectedFile().getPath();
			}catch(Exception ex){
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
		}
	}

	public void saveAsFile(){
		fsave  = new JFileChooser();
		fsave.setDialogTitle("Save As");
		int option = fsave.showSaveDialog(fsave);
		if (option == JFileChooser.APPROVE_OPTION){
			try{
				BufferedWriter out = new BufferedWriter(new FileWriter(fsave.getSelectedFile().getPath()));
				out.write(area.getText());
				out.close();
				frame.setTitle(fsave.getSelectedFile().getPath() + "- Editor");
				path = fsave.getSelectedFile().getPath();
				checkSave= true;
			}catch(Exception ex){
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
		}
	}


	public static void main(String[] args){
		TextNotepad txt = new TextNotepad();
	}
}