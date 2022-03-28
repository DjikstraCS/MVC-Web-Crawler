package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import controller.Controller;
import model.SearchItem;

public class MainFrame extends JFrame{
	
	private TextPanel textPanel;
	private JButton btn;
	private Toolbar toolbar;
	private FormPanel formPanel;
	private JFileChooser fileChooser;
	private Controller controller;
	private TablePanel tablePanelOverview;
	private TablePanel tablePanelHistory;
	private TablePanel tablePanelNew;
	private TablePanel tablePanelOld;
	private MainPanel mainPanel;
	private Calendar currentTime;
	private TabbedPane tabbedPanel;
	
	
	
	
	public MainFrame() {
		super("webBot Alfa!");
		try { 
		    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		setLayout(new BorderLayout());
		
		mainPanel = new MainPanel();
		btn = new JButton("Search");
		toolbar = new Toolbar();
		textPanel = new TextPanel();
		formPanel = new FormPanel();
		fileChooser = new JFileChooser();
		controller = new Controller();
		tablePanelOverview = new TablePanel();
		tablePanelHistory = new TablePanel();
		tablePanelNew = new TablePanel();
		tablePanelOld = new TablePanel();
		currentTime = Calendar.getInstance();
		
		tabbedPanel = new TabbedPane(tablePanelOverview, tablePanelHistory, tablePanelNew, tablePanelOld);
		//set up Calendar
		currentTime.set(Calendar.HOUR_OF_DAY, controller.getTimer().getHour());
		currentTime.set(Calendar.MINUTE, controller.getTimer().getMinute());
		currentTime.set(Calendar.SECOND, controller.getTimer().getSecond());
		

		tablePanelOverview.setData(controller.getSiteData());
		tablePanelHistory.setData(controller.getSiteData());
		tablePanelNew.setData(controller.getSiteData());
		tablePanelOld.setData(controller.getSiteData());
		
		
		setJMenuBar(createMenuBar());
		
		tablePanelOverview.addSiteDataTableListemer(new SiteDataTableListener() {
			public void rowDeleted(int row) {
				controller.removePerson(row);
				System.out.println(row);
			}
		});
		
		mainPanel.setMainPanelListener(new MainPanelListener() {

			@Override
			public void mainPanelEventOccurred(MainPanelEvent e) {
				//mainPanel.setVisible(false);
				formPanel.setVisible(true);
			}

			@Override
			public void mainPanelRunEventOccurred(MainPanelEvent ev) {
				controller.runSearchItems();
				tablePanelHistory.setData(controller.getSiteData());
				tablePanelOverview.setData(controller.getSiteDataNew());
				tablePanelOverview.refresh();
				tablePanelHistory.refresh();
				System.out.println("Ref!");
			}

			@Override
			public void rowDeleted(int row) {
				controller.removeSiteItem(row);	
			}
		});
		
		toolbar.setToolbarListener(new ToolbarListener() {

			public void toolbarClearEventOccurred(ToolbarEvent e) {
				controller.ClearDB();
				tablePanelOverview.refresh();
			}

			@Override
			public void refreshEventOccurred(ToolbarEvent ev) {
				controller.setTimer(11,00,00);
				//tablePanel.setData(conroller.load);
				tablePanelOverview.refresh();
				mainPanel.refresh();
			}

			@Override
			public void toolbarTestEventOccurred(ToolbarEvent ev) {
				controller.listSeachItemsInStorageFile();
				System.out.println("toolbarTest!");
				
			}
		});
		
		formPanel.setFormListener(new FormListener() {
			
			
			@Override
			public void formCancelEventOccurred() {
				formPanel.setVisible(false);
				System.out.println("Set To False");
				
			}

			@Override
			public void formEventOccurred(FormEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void formDomainSelectionEventOccurred(FormEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void formCategorySearchEventOccurred(FormEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void formSearchEventOccurred(FormEvent ev) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public List<SearchItem> formSaveEventOccurred(FormEvent ev) {
				List<SearchItem> ls = controller.addSearchItemgetList(ev);
				mainPanel.addSearchItem(ls);
				return ls;
			}

		});


		formPanel.getDbaPanel().setFormListener(new FormListener() {
			
			
			@Override
			public void formEventOccurred(FormEvent e) {
				
			}

			@Override
			public void formCategorySearchEventOccurred(FormEvent e) {
				formPanel.getDbaPanel().setCategories(controller.getCategories(e));
			}

			@Override
			public void formSearchEventOccurred(FormEvent ev) {
				controller.getListings(ev);
				tablePanelOverview.refresh();
				
			}

			@Override
			public void formDomainSelectionEventOccurred(FormEvent e) {
				String selectedSite = e.getSearchField();
				if(selectedSite.equalsIgnoreCase("www.dba.dk") == true) {
					mainPanel.setVisible(true);
				}else if (selectedSite.equalsIgnoreCase("www.ebay.com") == true) {
					mainPanel.setVisible(false);
				}
				
			}

			@Override
			public List<SearchItem> formSaveEventOccurred(FormEvent ev) {
				List<SearchItem> ls = controller.addSearchItemgetList(ev);
				mainPanel.addSearchItem(ls);
				return ls;
			}

			@Override
			public void formCancelEventOccurred() {
				formPanel.setVisible(false);
				System.out.println("Set To False");
				
			}

		});
		
		setMinimumSize(new Dimension(900,700));
		add(formPanel, BorderLayout.WEST);
		formPanel.setVisible(false);
		add(mainPanel, BorderLayout.EAST);
		add(tabbedPanel, BorderLayout.CENTER);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300,800);
        setVisible(true);
        
			controller.setTimer(11,00,00);
			
			try {
				controller.loadStorage();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			mainPanel.setData(controller.getSearchItemsList());

			tablePanelOverview.setData(controller.getSiteDataNew());
			tablePanelHistory.setData(controller.getSiteData());
			tablePanelNew.setData(controller.getSiteData());
			tablePanelOld.setData(controller.getSiteData());

	}
	
	public void runSearchItems() {
		controller.runSearchItems();
		tablePanelOverview.refresh();
	}
	
	public void setMainPanelSearhItem(List<SearchItem> list) {
		//mainPanel.addSearchItem(list);
	}
	
	
	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		//menuBar.setBackground(Color.GRAY);
		
		JMenu fileMenu = new JMenu("Files");
		JMenu helpMenu = new JMenu("Help");
		
		JMenuItem importDataItem = new JMenuItem("Import Data");
		JMenuItem saveSeachItems = new JMenuItem("Export ...");
		JMenuItem loadSeachItems = new JMenuItem("Import ...");
		JMenuItem exportDataItem = new JMenuItem("Save");
		JMenuItem exitItem = new JMenuItem("Exit");
		
		//fileMenu.add(exportDataItem);
		//fileMenu.add(importDataItem);
		fileMenu.add(saveSeachItems);
		fileMenu.add(loadSeachItems);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);
		
		JMenu settingsMenu = new JMenu("Settings");
		JMenuItem showToolbar = new JCheckBoxMenuItem("Toggle toolbar");
		JMenuItem aboutItem = new JMenuItem("About");
		showToolbar.setSelected(true);
		
		helpMenu.add(settingsMenu);
		helpMenu.add(aboutItem);
		settingsMenu.add(showToolbar);
		
		
		menuBar.add(fileMenu);
		menuBar.add(helpMenu);
		
		showToolbar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ev) {
				JCheckBoxMenuItem menuItem = (JCheckBoxMenuItem)ev.getSource();
				toolbar.setVisible(menuItem.isSelected());
			}
			
		});
		fileMenu.setMnemonic(KeyEvent.VK_F);
		helpMenu.setMnemonic(KeyEvent.VK_H);
		exitItem.setMnemonic(KeyEvent.VK_X);
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		
		aboutItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(MainFrame.this, "webBot Alfa \n", "About", JOptionPane.INFORMATION_MESSAGE);
				
			}
		});
		
		saveSeachItems.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
					try {
						controller.saveToFile(fileChooser.getSelectedFile());
						
					} catch (IOException e) {
						JOptionPane.showMessageDialog(MainFrame.this, "Could not load data from file", "Error", JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
					}
				}
				
			}
		});
		
		loadSeachItems.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
					try {
						controller.loadFromFile(fileChooser.getSelectedFile());
						mainPanel.refresh();
					} catch (IOException e) {
						JOptionPane.showMessageDialog(MainFrame.this, "Could not load data from file", "Error", JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
					}
				}
				
			}
		});
		
		exportDataItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
					try {
						controller.saveToFile(fileChooser.getSelectedFile());
					} catch (IOException e) {
						JOptionPane.showMessageDialog(MainFrame.this, "Could not load data from file", "Error", JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
					}
				}
				
			}
		});
		
		importDataItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
					try {
						controller.loadFromFile(fileChooser.getSelectedFile());
						tablePanelOverview.refresh();
					} catch (IOException e) {
						JOptionPane.showMessageDialog(MainFrame.this, "Could not save data to file", "Error", JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
					}
				}
				
			}
		});
		
		exitItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				//String text = JOptionPane.showInputDialog(MainFrame.this, "Enter user", "enter user", JOptionPane.OK_OPTION|JOptionPane.INFORMATION_MESSAGE);System.out.println(text);
				
				int action = JOptionPane.showConfirmDialog(MainFrame.this, "Do you really want to exit?", "Confirm exit", JOptionPane.OK_CANCEL_OPTION);
				if(action == JOptionPane.OK_OPTION) {
					System.exit(0);
				}
				
			}
			
		});
		
		
		return menuBar;
	}

}
