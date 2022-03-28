package view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class Toolbar extends JPanel {
	
	protected static final String FormEvent = null;
	private JButton clearButton;
	private JButton helpButton;
	private JButton testButton;
	private StringListener textListener; 
	private JComboBox domainBox;
	private ToolbarListener toolbarListener; 
	
	public Toolbar() {
		
		domainBox = new JComboBox();
		clearButton = new JButton("Clear");
		helpButton = new JButton("Refresh");
		testButton = new JButton("Test");
		
		DefaultComboBoxModel domainModel = new DefaultComboBoxModel();
		
		domainModel.addElement("www.dba.dk");
		domainModel.addElement("www.ebay.com");
		domainModel.addElement("www.amazon.de");
		
		domainBox.setModel(domainModel);
		
		testButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	
            		ToolbarEvent ev = new ToolbarEvent (this);
            	
	                if(toolbarListener != null) {
	                toolbarListener.toolbarTestEventOccurred(ev);
	                }
            }
        });
		
		clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	
            		ToolbarEvent ev = new ToolbarEvent (this);
            	
	                if(toolbarListener != null) {
	                toolbarListener.toolbarClearEventOccurred(ev);
	                }
            }
        });
		
        helpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	ToolbarEvent ev = new ToolbarEvent (this);
            	
                if(toolbarListener != null) {
                toolbarListener.refreshEventOccurred(ev);
                }
            }
        });
        Border innderBorder = BorderFactory.createTitledBorder("");
        Border outerBorder = BorderFactory.createEmptyBorder(0, 0, 0, 0);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innderBorder));
	
		setLayout(new FlowLayout(FlowLayout.LEFT));
		//add(domainBox);
		
		add(clearButton);
		add(helpButton);
		add(testButton);
		
	}
	
	
	
	public void setStringListener(StringListener listener) {
		this.textListener = listener;
	}

	public void setToolbarListener(ToolbarListener listener) {
		this.toolbarListener = listener;
		
	}
	
}
