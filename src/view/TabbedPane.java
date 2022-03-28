package view;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.border.Border;

public class TabbedPane  extends JPanel{
	
	private JTabbedPane tabbedPane;
	

	public TabbedPane(TablePanel tablePanelOverview, TablePanel tablePanelHistory, TablePanel tablePanelNew, TablePanel tablePanelOld) {
		tabbedPane = new JTabbedPane();
		
		tabbedPane.addTab("New",new JScrollPane(tablePanelOverview));
		tabbedPane.addTab("History",new JScrollPane(tablePanelHistory));
		//tabbedPane.addTab("New",new JScrollPane(tablePanelNew));
		//tabbedPane.addTab("Old",new JScrollPane(tablePanelOld));
		
		Border innderBorder = BorderFactory.createTitledBorder("Site Data:");
		Border outerBorder = BorderFactory.createEmptyBorder(0, 0, 0,0);
		setBorder(BorderFactory.createCompoundBorder(innderBorder, outerBorder));
		
		setLayout(new BorderLayout());		
		
		add(tabbedPane, BorderLayout.CENTER);
	}

}
