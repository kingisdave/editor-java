import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.awt.print.PrinterJob;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
// import javax.swing.event.ListSelectionListener;
// import javax.swing.event.ListSelectionEvent;
import javax.swing.event.*;

public class TextEditor implements ActionListener, ListSelectionListener, CaretListener {
	JFrame frame;
	JTextArea area;
	// JTextPane area;
	JMenu fileMenu;
	JTextField findText, replaceText, fontval1, fontval2, fontval3;
	JTabbedPane jtp;
	JMenuItem newPage;
	JFileChooser fsave;
	JList myList1,myList2,myList3;
	JLabel statusBar, fontLabel;
	int linenum = 1, word = 1, columnnum = 1, linenumber, columnnumber;
	String text="", words[];
	JPanel panel, tpanel;
	String mList, path, mlst1,mlst2,mlst3;
	// int mlst3 = 20;
	int pageNumber = 0;
	boolean alSaved, setAllFonts = false;
	Font areafont;
	String[] fontname = {"Arial","Arial Rounded MT","Bell","Berlin Sans FB","Bodoni FT","Broadway","Calibri","Calisto MT","Cambria","Comic Sans","Cooper","Consolar","Constantia","Courier","Elephant","Forte","Georgia","Gill Sans","Harrighton","Impact","Serif","Tahoma"};
	String[] fonttype = {"Regular","Oblique","Bold","Bold Obique"};
	String[] fontsize = {"8","9","10","11","12","14","16","18","20","22","24","26","28","36","48","72"};

	String[] menus = {"File", "Edit", "Format", "View", "Help"};
	char[] mAlias = { 'F', 'E', 'O', 'V', 'H'};
	String[][] menumenu = {
		{"  New     ","  New Window   ", "  Open...     ", "  Save   ", "  Save As...     ","DLine", "  Page Setup...     ", "  Print...     ","DLine", "  Exit     "},
		{"  Undo     ","DLine", "  Cut     ", "  Copy     ","  Paste     ","  Delete             Del","DLine","  Find...     ","  Find Next           F3", "  Replace...     ", "  Find with Google    ", "  Go To...     ","DLine","  Select All        ", "  Time/Date           F5"},
		{"  Word Wrap    ", "  Font...     "},
		{"  Zoom    ","  Status Bar    "},
		{"  View Help     ", "  About Notepad     "}
	};
	String[] zoomMenu = {"Zoom In", "Zoom Out", "Restore Default"};
	String[][] menuAlias = {
		{"N","N", "O","S","","","","P","",""},
		{"Z","", "X","C", "X","Del","","F","F3","H","F" , "G","", "A", "F5"},
		{"", ""}, {"",""}, {"", ""}
	};
	
	public TextEditor(){
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // To close the console window on exit of the window
		frame.setLocation(new Point(400, 100)); // To set the default location to anywhere on the window
		frame.setSize(new Dimension(500, 250));
		frame.setTitle("Text Frame"); // To set the title of the window;
		
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("textEditor.png"));
		frame.setLayout(new BorderLayout());
		area = new JTextArea(18, 35);
		areafont = new Font("Serif", Font.PLAIN, 20);
		area.setFont(areafont);

		area.setFocusable(true);
		// frame.add(area);
		// frame.add(new JScrollPane(area));
		// tpanel = new JPanel();
		// tpanel.add(area);
		// frame.add(new JScrollPane(tpanel));
		statusBar = new JLabel("Line: " + linenumber + "  Column: " + columnnumber + "   Characters: "+ text.length() + " Words: " + word + "   ");
		panel = new JPanel(new FlowLayout());
		frame.add(panel, BorderLayout.SOUTH);
		area.addCaretListener(new CaretListener(){
			@Override
			public void caretUpdate(CaretEvent ce){
				JTextArea editArea = (JTextArea)ce.getSource();
				try{
					text= area.getText();
					words = text.split(" ");
					word = words.length;
					int caretPos = editArea.getCaretPosition();
					linenum = editArea.getLineOfOffset(caretPos);
					columnnum = caretPos - editArea.getLineStartOffset(linenum);
					linenum += 1;
				}catch(Exception ex){ }

				updateStatus(linenum, columnnum, word, text);
		
			}
		});
		
		JMenuBar menuContainer = new JMenuBar(); // the bar where the menu can be placed
		
		for (int ii= 0; ii < menus.length; ii++) {
			
			fileMenu = new JMenu(menus[ii]);
			fileMenu.setMnemonic(mAlias[ii]);

			for (int ss=0; ss< menumenu[ii].length; ss++) {
				if (menumenu[ii][ss].equals("DLine")) {
					fileMenu.addSeparator();
				} else {
					int aliaslength = menuAlias[ii][ss].length();
					if(aliaslength == 1 ){
						// char myalias = menuAlias[ii][ss].charAt(0);	
						newPage = new JMenuItem(menumenu[ii][ss]); // the bar where the menu can be placed
						newPage.setAccelerator(KeyStroke.getKeyStroke(menuAlias[ii][ss].charAt(0), KeyEvent.CTRL_DOWN_MASK));
						
						if (menumenu[ii][ss].trim().equals("New Window")) {
							newPage.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.SHIFT_MASK|ActionEvent.CTRL_MASK));
						}
						// newPage.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.ALT_DOWN_MASK));
						newPage.addActionListener(this);
						fileMenu.add(newPage);
						menuContainer.add(fileMenu);
						if (menumenu[ii][ss].trim().equals("Save As...") && menumenu[ii][ss].trim().equals("Print...") ) {
							fileMenu.addSeparator();
						}

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
							if(menumenu[ii][ss].trim().equals("Word Wrap")){
								final JCheckBoxMenuItem wordWrap = new JCheckBoxMenuItem(menumenu[ii][ss]); // the bar where the menu can be placed
								// wordWrap.addActionListener(this);
								wordWrap.addActionListener(new ActionListener() {
						            public void actionPerformed(ActionEvent evt) {
						            	boolean checkState = wordWrap.isSelected();
										if(checkState == true){
											area.setLineWrap(true);
											area.setWrapStyleWord(true);
										}else {
											area.setLineWrap(false);
											area.setWrapStyleWord(false);
										}
						            }
						        });
								fileMenu.add(wordWrap);
								menuContainer.add(fileMenu);
							}else if(menumenu[ii][ss].trim().equals("Status Bar")){
								final JCheckBoxMenuItem status = new JCheckBoxMenuItem(menumenu[ii][ss]); // the bar where the menu can be placed
								status.addActionListener(new ActionListener() {
						            public void actionPerformed(ActionEvent evt) {
					            		boolean checkState = status.isSelected();
					            		if(checkState){
											updateStatus(linenum, columnnum, word, text);
											panel.add(statusBar);
											panel.repaint();
										}else {
									 		panel.add(statusBar);
											panel.repaint();
										}
						            }
						        });
								fileMenu.add(status);
								menuContainer.add(fileMenu);
							}else if(menumenu[ii][ss].trim().equals("Zoom")){
								JMenu zoom = new JMenu(menumenu[ii][ss]); // the bar where the menu can be placed
								for (String zoomlist : zoomMenu) {
									JMenuItem zoomIn = new JMenuItem(zoomlist); // the bar where the menu can be placed
									zoomIn.addActionListener(this);						
									zoom.add(zoomIn);
								}
								fileMenu.add(zoom);
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
			}
		}
		// area = new JTextArea(5, 20);
		// area.setFont(new Font("Serif", Font.PLAIN, 20));
		// area.setFocusable(true);
		// frame.add(area);
		// frame.add(new JScrollPane(area));
		// // mainPage.add(area, BorderLayout.CENTER);
		// // frame.add(mainPage, BorderLayout.CENTER);
		// statusBar = new JLabel("Line: " + linenumber + "  Column: " + columnnumber + "   Characters: "+ text.length() + " Words: " + word + "   ");
		// panel = new JPanel(new FlowLayout());
		// frame.add(panel, BorderLayout.SOUTH);
		// area.addCaretListener(new CaretListener(){
		// 	@Override
		// 	public void caretUpdate(CaretEvent ce){
		// 		JTextArea editArea = (JTextArea)ce.getSource();
		// 		try{
		// 			text= area.getText();
		// 			words = text.split(" ");
		// 			word = words.length;
		// 			int caretPos = editArea.getCaretPosition();
		// 			linenum = editArea.getLineOfOffset(caretPos);
		// 			columnnum = caretPos - editArea.getLineStartOffset(linenum);
		// 			linenum += 1;
		// 		}catch(Exception ex){ }

		// 		updateStatus(linenum, columnnum, word, text);
		
		// 	}
		// });
		// frame.add(menuContainer, BorderLayout.NORTH);	
		// frame.pack();
		// frame.setVisible(true);

		frame.add(menuContainer, BorderLayout.NORTH);
		jtp = new JTabbedPane(); //
		area.setFocusable(true);
		jtp.setBounds(50,50,300,300); //
 		jtp.add("first", tpanel); //
		jtp.add(new JScrollPane(area)); //	
		frame.add(jtp); //
		// frame.add(new JScrollPane(area)); //	
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

	
	public void findAndReplace()
	{
		JDialog findR = new JDialog();
		findR.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);  // To close the console window on exit of the window
		findR.setLayout(new GridLayout(3,1));
		findR.setSize(new Dimension(400, 200));
		findR.setLocation(new Point(500, 300)); // To set the default location to anywhere on the window
		findR.setIconImage(Toolkit.getDefaultToolkit().getImage("textEditor.png"));
		findText = new JTextField("Enter text to find", 20);
		replaceText = new JTextField("Enter text to replace", 20);
		findR.setTitle("Find And Replace"); // To set the title of the window;
		final JButton find = new JButton("Find");
		find.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				try{
					String search = findText.getText();
					int n = area.getText().indexOf(search);
					area.select(n,n+search.length());
				}catch(Exception ee){
					JOptionPane.showMessageDialog(null, "No text can be replaced yet");
				}
			}
		});
		final JButton replace = new JButton("replace");
		replace.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				try{
					String search = findText.getText();
					int n = area.getText().indexOf(search);
					String replacing = replaceText.getText();
					area.replaceRange(replacing, n, n+search.length());
				}catch(Exception ee){
					JOptionPane.showMessageDialog(null, "No text is selected yet");
				}
			}
		});
		JPanel panel = new JPanel();
		findR.add(findText);
		findR.add(replaceText);
		panel.add(find);
		panel.add(replace);
		findR.add(panel);
		findR.setVisible(true);
	}

	public void goToLine()
	{
		final JDialog go2 = new JDialog(frame);
		// final JDialog go2 = new JDialog();
		go2.setSize(200, 100);
		go2.setLocation(300, 200); // To set the default location to anywhere on the window
		frame.setTitle("Go To Line"); // To set the title of the window;
		JPanel pa = new JPanel(new BorderLayout());
		JLabel lb = new JLabel("Enter line number");
		pa.add(lb, BorderLayout.NORTH);
		final JTextField tf = new JTextField("1", 10);
		pa.add(tf, BorderLayout.CENTER);
		JButton bn = new JButton("Go to line");
		bn.setSize(5, 5);
		bn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				try{
					String line = tf.getText();
					area.setCaretPosition(area.getDocument().getDefaultRootElement().getElement(Integer.parseInt(line)-1).getStartOffset());
					area.requestFocusInWindow();
					go2.dispose();
				}catch(Exception ee){
					JOptionPane.showMessageDialog(null, "No text can be replaced yet");
				}
			}
		});
		
		pa.add(bn, BorderLayout.SOUTH);
		go2.add(pa);
		go2.setVisible(true);
	}

	public void showFonts()
	{
		final JDialog myfont = new JDialog();
		myfont.setSize(400, 350);
		myfont.setLocation(300, 200); // To set the default location to anywhere on the window
		myfont.setTitle("Font"); // To set the title of the window;
		myfont.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		myfont.setLayout(new BorderLayout());
		
		JPanel panel1 = new JPanel(new FlowLayout());
		
		JPanel jp1 = new JPanel(new BorderLayout());
		JLabel lb1 = new JLabel("Font:");
		jp1.add(lb1, BorderLayout.NORTH);
		fontval1 = new JTextField(15);
		jp1.add(fontval1, BorderLayout.CENTER);
		myList1 = new JList(fontname);
 		myList1.setSelectedIndex(2);
 		myList1.addListSelectionListener(this);
 		JScrollPane myscroll1 = new JScrollPane(myList1);
		jp1.add(myscroll1, BorderLayout.SOUTH);
		panel1.add(jp1);

		JPanel jp2 = new JPanel(new BorderLayout());
		JLabel lb2 = new JLabel("Font Style:");
		jp2.add(lb2, BorderLayout.NORTH);
		fontval2 = new JTextField(10);
		jp2.add(fontval2, BorderLayout.CENTER);
		myList2 = new JList(fonttype);
 		myList2.setSelectedIndex(2);
 		myList2.addListSelectionListener(this);
 		JScrollPane myscroll2 = new JScrollPane(myList2);
		jp2.add(myscroll2, BorderLayout.SOUTH);
		panel1.add(jp2);

		JPanel jp3 = new JPanel(new BorderLayout());
		JLabel lb3 = new JLabel("Size:");
		jp3.add(lb3, BorderLayout.NORTH);
		fontval3 = new JTextField(5);
		jp3.add(fontval3, BorderLayout.CENTER);
		myList3 = new JList(fontsize);
 		myList3.setSelectedIndex(2);
 		myList3.addListSelectionListener(this);
 		JScrollPane myscroll3 = new JScrollPane(myList3);
		jp3.add(myscroll3, BorderLayout.SOUTH);
		panel1.add(jp3);
		myfont.add(panel1, BorderLayout.NORTH);

		JPanel panel2 = new JPanel(new BorderLayout());
		JPanel jp21 = new JPanel(new FlowLayout());
		fontLabel = new JLabel();
		jp21.add(fontLabel);
		jp21.setBorder(BorderFactory.createTitledBorder("Sample"));
		jp21.setPreferredSize(new Dimension(180, 70));
		panel2.add(jp21, BorderLayout.EAST);
		myfont.add(panel2, BorderLayout.CENTER);

		JPanel panel3 = new JPanel(new BorderLayout());
		JPanel flow3 = new JPanel(new FlowLayout());
		JButton bn1 = new JButton("OK");
		JButton bn2 = new JButton("Cancel");
		bn1.setSize(5, 5);
		// bn1.addActionListener(this);
		bn1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				try{  
					setAllFonts = true;
					if(mlst2.equals("Regular")){
						areafont = new Font(mlst1,0,Integer.parseInt(mlst3));
					}else if(mlst2.equals("Oblique")){
						areafont = new Font(mlst1,2,Integer.parseInt(mlst3));
					}else if(mlst2.equals("Bold")){
						areafont = new Font(mlst1,1,Integer.parseInt(mlst3));
					}else{
						areafont = new Font(mlst1,3,Integer.parseInt(mlst3));
					}
					// Font font = area.getFont();
					// area.setFont(new Font(null,0,font.getSize()+10));
					if (setAllFonts) {
						area.setFont(areafont);
						frame.validate();
					}
					myfont.dispose();
				}catch(Exception ee){
					setAllFonts = false;
					JOptionPane.showMessageDialog(null, "No text can be replaced yet");
				}
			}
		});
		flow3.add(bn1);
		bn2.setSize(5, 5);
		bn2.addActionListener(this);
		flow3.add(bn2);
		panel3.add(flow3, BorderLayout.EAST);
		myfont.add(panel3, BorderLayout.SOUTH);
		myfont.setVisible(true);

	}

	@Override
	public void valueChanged(ListSelectionEvent lsv){
		mlst1 = myList1.getSelectedValue().toString();
		mlst2 = myList2.getSelectedValue().toString();
		mlst3 = myList3.getSelectedValue().toString();
		fontval1.setText(mlst1);
		fontval2.setText(mlst2);
		fontval3.setText(mlst3);
		fontLabel.setText(mlst1);
		setAllFonts = true;
	}

	private void updateStatus(int linenumber, int columnnumber, int word, String text){
		statusBar.setText("Line: " + linenumber + "  Column: " + columnnumber + "   Characters: "+ text.length() + "  Words: " + word + "   ");
	}

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

		} else if (com.trim().equals("New Window")) {
			new TextEditor();
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
			} else {
				saveFile(false);
			}
			alSaved = true;
			
		} else if(com.trim().equals("Save As...")){
			saveFile(true);
		} else if(com.trim().equals("Page Setup...")){
			PrinterJob pj = PrinterJob.getPrinterJob();
			pj.pageDialog(pj.defaultPage());
		} else if(com.trim().equals("Print...")){
			PrinterJob pj = PrinterJob.getPrinterJob();
			if (pj.printDialog()) {
				try{
					pj.print();
				}catch(Exception exp){
					System.out.print(exp);
				}
			}
		} else if(com.trim().equals("Exit")){
			if(path == null){
				saveFile(true);
			} else {
				saveFile(false);
			}
			alSaved = true;
			System.exit(0);
		} else if(com.trim().equals("Undo")){
		} else if(com.trim().equals("Redo")){
		} else if(com.trim().equals("Cut")){
			area.cut();
		} else if(com.trim().equals("Copy")){
			area.copy();
		} else if(com.trim().equals("Paste")){
			area.paste();
		} else if(com.trim().equals("Delete")){
			try{
				String sText = area.getSelectedText();
				if (!sText.equals(null)) {
					int n =  area.getText().indexOf(sText);
					area.replaceRange("", n, n+sText.length());
				} else {
					// JOptionPane.showMessageDialog(null, ex.getMessage());
					JOptionPane.showConfirmDialog(null, "You need to select a text ", "Warning", JOptionPane.CANCEL_OPTION);
				}
			}catch(Exception eee){
					JOptionPane.showConfirmDialog(null, "You need to select a text ", "Warning", JOptionPane.CANCEL_OPTION);
				// System.out.print(eee);
			}
		} else if(com.trim().equals("Find...")){
			findAndReplace();
		} else if(com.trim().equals("Find Next")){
			findAndReplace();
		} else if(com.trim().equals("Replace...")){
			findAndReplace();
		} else if(com.trim().equals("Find with Google")){
			try{
				// Desktop.getDesktop().browse(URI.create("www.google.com"));
				if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
					Desktop.getDesktop().browse(new URI("www.google.com"));
				}
			} catch(Exception exc){
				JOptionPane.showMessageDialog(null, "Not able to open url");
			}
		} else if(com.trim().equals("Go To...")){
			goToLine();
		} else if(com.trim().equals("Select All")){
			area.selectAll();
		} else if(com.trim().equals("Time/Date")){
			String date = new SimpleDateFormat("yyy/MM/dd hh:mm:ss").format(new Date());
			area.setText(date);
		// } else if(com.trim().equals("Word Wrap")){
		// 	// System.out.println(com+ " Checkbox");
		// 	boolean checkState = com.trim().isSelected();
		// 	if(checkState == true){
		// 		area.setLineWrap(true);
		// 		area.setWrapStyleWord(true);
		// 	}else {
		// 		area.setLineWrap(false);
		// 		area.setWrapStyleWord(false);
		// 	}
		} else if(com.trim().equals("Zoom In")){
			Font font = area.getFont();
			if (font.getSize() < 200) {
				if(setAllFonts){
					areafont = new Font(font.getName(),font.getStyle(),font.getSize()+10);
					area.setFont(areafont);
				} else {
					area.setFont(new Font(null,0,font.getSize()+10));
				}
				frame.validate();
			} else {
				JOptionPane.showMessageDialog(null, "Zoom in can not go above the range of 200");
			}
		} else if(com.trim().equals("Zoom Out")){
			Font font = area.getFont();
			if (font.getSize() >= 20) {
				if(setAllFonts){
					areafont = new Font(font.getName(),font.getStyle(),font.getSize()-10);
					area.setFont(areafont);
				} else {
					area.setFont(new Font(null,0,font.getSize()-10));
				}
				frame.validate();
			} else {
				JOptionPane.showMessageDialog(null, "Zoom in can not go above the range of 200");
			}
		} else if(com.trim().equals("Restore Default")){
			areafont = new Font("Serif", Font.PLAIN, 20);
			area.setFont(areafont);
			frame.validate();
		} else if(com.trim().equals("Font...")){
			showFonts();
		// } else if(com.trim().equals("Status")){
			 	// boolean checkBar = com.trim().isSelected();
		// 	if(checkBar){
		// 		updateStatus(linenum, columnnum, word, text);
		// 		panel.add(statusBar);
		// 		panel.repaint();
		// 	}else {
		// 		panel.add(statusBar);
		// 		panel.repaint();
		// // 	}
		} else if(com.trim().equals("Help")){
		} else if(com.trim().equals("About Notepad")){

			// JOptionPane.showConfirmDialog(null, "Go online fot help contents", JOptionPane.YES_NO_CANCEL_OPTION);
		}
	}

	@Override
	public void caretUpdate(CaretEvent c) {
		throw new UnsupportedOperationException("Not supported yet. ");
	}

	public static void main(String[] args){
		TextEditor txt = new TextEditor();
	}
}
