package view;


import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.border.Border;

import model.SiteData;

public class TablePanel extends JPanel {
	
	private JTable table;
	private SiteDataTableModel tableModel;
	private JPopupMenu popup;
	private SiteDataTableListener siteDataTableListener;
	
	public TablePanel() {
		tableModel = new SiteDataTableModel();
		table = new JTable(tableModel);
		popup = new JPopupMenu();
		
		
		
		JMenuItem removeItem = new JMenuItem("Delete");
		JMenuItem copyItem = new JMenuItem("Copy");
		JMenuItem linkItem = new JMenuItem("Open link");
		popup.add(copyItem);
		popup.add(removeItem);
		popup.add(linkItem);
		
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
				if(siteDataTableListener != null) {
					siteDataTableListener.rowDeleted(row);	
					tableModel.fireTableRowsDeleted(row, row);
				}
			}
		});
		
		copyItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int row = table.getSelectedRow();
				int col = table.getSelectedColumn();
				String toCB = table.getValueAt(row, col).toString();
				StringSelection stringSelection = new StringSelection(toCB);
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
				System.out.println("Copied!");
			}
		});
		
		linkItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int row = table.getSelectedRow();
				String URL = table.getValueAt(row, 2).toString();
				System.out.println("Copied!");
				Desktop desktop=Desktop.getDesktop();
				try {
					desktop.browse(new URL(URL).toURI());
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		Border innderBorder = BorderFactory.createEmptyBorder(0, 0, 0,0);
		Border outerBorder = BorderFactory.createEmptyBorder(0, 0, 0,0);
		setBorder(BorderFactory.createCompoundBorder(innderBorder, outerBorder));
		
		setLayout(new BorderLayout());
		
		add(new JScrollPane(table), BorderLayout.CENTER);
		
	}
	
	public void setData(List<SiteData> db) {
		tableModel.setData(db);
	}
	
	public void refresh() {
		tableModel.fireTableDataChanged();
		
	}

	public void addSiteDataTableListemer(SiteDataTableListener listener) {
		this.siteDataTableListener = listener;	
	}
}
