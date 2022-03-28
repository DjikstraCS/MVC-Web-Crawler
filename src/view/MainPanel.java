package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import model.SearchItem;

public class MainPanel extends JPanel {
	
	private JScrollPane scrollPane;
	private JTable table;
	private SearchItemTableModel tableModel;
	private JLabel tableLabel;
	private JButton addBtn;
	private JButton removeBtn;
	private JButton runBtn;
	private JPopupMenu popup;
	private MainPanelListener mainPanelListener;
	
	public MainPanel() {
		//Set up panel
		Dimension dim = getPreferredSize();
		dim.width = 350;
		setPreferredSize(dim);

		tableModel = new SearchItemTableModel();
		System.out.println(tableModel.getColumnName(1));
		table = new JTable(tableModel);
		scrollPane = new JScrollPane(table);
		tableLabel = new JLabel("Table: ");
		popup = new JPopupMenu();
		JMenuItem removeItem = new JMenuItem("Delete");
		popup.add(removeItem);
		table.add(popup);
		
		addBtn = new JButton("Add Search");
		removeBtn = new JButton("Remove");
		runBtn = new JButton("Update");
		scrollPane.setPreferredSize(new Dimension(330, 400));
		
		table.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
				int row = table.rowAtPoint(e.getPoint());
				table.getSelectionModel().setSelectionInterval(row, row);
				if(e.getButton() == MouseEvent.BUTTON3) {
					popup.show(table, e.getX(), e.getY());
				}
			}
		});
		
		removeItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int row = table.getSelectedRow();
				if(mainPanelListener != null) {
					mainPanelListener.rowDeleted(row);	
					tableModel.fireTableRowsDeleted(row, row);
				}
			}
		});
		
		
		addBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	MainPanelEvent ev = new MainPanelEvent(this);
            	
            	if(mainPanelListener != null) {
					mainPanelListener.mainPanelEventOccurred(ev);	
				}
            }
        });
		
		runBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	Thread tn = new Thread(new Runnable() {

        			@Override
        			public void run() {
        				MainPanelEvent ev = new MainPanelEvent(this);
        				mainPanelListener.mainPanelRunEventOccurred(ev);
        			}
        		});
            	
            	
            	if(mainPanelListener != null) {
					tn.start();	
				}
            }
        });
        //scrollPane.setFillsViewportHeight(true); 
		
		
		

		Border innderBorder = BorderFactory.createTitledBorder("Search settings:");
		Border outerBorder = BorderFactory.createEmptyBorder(0, 0, 0, 0);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innderBorder));
		
		layoutComponents();
	}
	
	public void layoutComponents() {
		
		setLayout(new GridBagLayout());
		
		GridBagConstraints gc = new GridBagConstraints();
		gc.anchor = GridBagConstraints.CENTER;
		
		
		/////////////////////////// Line ///////////////////
		
		gc.gridy = 0;
		
		gc.gridx = 0;
		gc.insets = new Insets(0,0,20,0);
		
		add(addBtn, gc);
		setVisible(true);
		
		
		
		///////////////////////// Line ///////////////////
		
		gc.gridy++;
		
		gc.gridx = 0;
		gc.insets = new Insets(0,0,0,0);
		add(scrollPane, gc);
		
		///////////////////////// Line ///////////////////
		
		gc.gridy++;
		
		gc.gridx = 0;
		gc.insets = new Insets(100,0,0,0);
		add(runBtn, gc);
	}
	
	public void addSearchItem(List<SearchItem> ls) {
		tableModel.setData(ls);
		tableModel.fireTableDataChanged();
	}


	public void setMainPanelListener(MainPanelListener mainPanelListener) {
		this.mainPanelListener = mainPanelListener;
		
	}
	
	public void refresh() {
		tableModel.fireTableDataChanged();
		System.out.println("Refreshed!");
	}
	
	public void setData(List<SearchItem> ls) {
		tableModel.setData(ls);
		tableModel.fireTableDataChanged();
		System.out.println("Refreshed!");
	}
	
	//public void setCategories(ArrayList<String> list) {
	//	DefaultListModel<String> categoryModel = new DefaultListModel<String>();
		//for (int i = 0 ; i < list.size(); i++) {
			//System.out.println(list.get(i));
			//categoryModel.addElement(list.get(i));
		//}
		//categoryList.setModel(categoryModel);
	//}
	
}
