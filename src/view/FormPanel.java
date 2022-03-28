package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

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
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class FormPanel extends JPanel {
	

	private FormListener formListener; 
	private DbaFormPanel dbaPanel;
	private JButton cancelBtn;
	private JButton saveBtn;
	private JComboBox domainBox;
	
	public FormPanel() {
		//Set up panel
		Dimension dim = getPreferredSize();
		dim.width = 350;
		setPreferredSize(dim);
		
		//Set up elements
		domainBox = new JComboBox();
		dbaPanel = new DbaFormPanel(this);
		cancelBtn = new JButton("Close");
		saveBtn = new JButton("Save");
		//saveBtn.setEnabled(false);
		
		//Set up drop down
		DefaultComboBoxModel domainModel = new DefaultComboBoxModel();
		
		domainModel.addElement("Select domain:");
		domainModel.addElement("dba.dk");
		domainModel.addElement("ebay.com");
		domainModel.addElement("amazon.de");
		
		domainBox.setModel(domainModel);
		
		
		domainBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	
            	String selectedSite = domainBox.getSelectedItem().toString();
            	
				if(selectedSite.equalsIgnoreCase("Select domain:") == true) {
	        		setInvisible();
				}else if (selectedSite.equalsIgnoreCase("dba.dk") == true) {
					setInvisible();
					dbaPanel.setVisible(true);
				}else if (selectedSite.equalsIgnoreCase("ebay.com") == true) {
					setInvisible();
				}else if (selectedSite.equalsIgnoreCase("amazon.de") == true) {
					setInvisible();
				}

            }
        });
		
		saveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
            	FormEvent ev = dbaPanel.generateFormEvent();
				
				if(formListener != null) {
					formListener.formSaveEventOccurred(ev);	
				}

            }
        });
		
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
            	
				if(formListener != null) {
					formListener.formCancelEventOccurred();	
				}

            }
        });
		
		Border innderBorder = BorderFactory.createTitledBorder("New Search:");
		Border outerBorder = BorderFactory.createEmptyBorder(10, 10, 0, 10);
		setBorder(BorderFactory.createCompoundBorder(innderBorder, outerBorder));
		
		JPanel top = new JPanel(new BorderLayout());
		JPanel topRight = new JPanel(new BorderLayout()); 
		JPanel topLeft = new JPanel(new BorderLayout());
		
		topLeft.add(domainBox, BorderLayout.WEST);
		topRight.add(cancelBtn, BorderLayout.EAST);
		topRight.add(saveBtn, BorderLayout.WEST);
		topLeft.setPreferredSize(new Dimension(160,35));
		topRight.setPreferredSize(new Dimension(112,30));
		top.add(topRight, BorderLayout.EAST);
		top.add(topLeft, BorderLayout.WEST);
		top.setBorder(BorderFactory.createEmptyBorder(-1, 0, 0, 0));
		
		//topRight.setPreferredSize(new Dimension(140,50));
		top.setPreferredSize(new Dimension(300,30));
		//top.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
		
		add(top, BorderLayout.NORTH);
		add(dbaPanel, BorderLayout.CENTER);

		setInvisible();
	}


	public void setInvisible() {
		dbaPanel.setVisible(false);;
	}
	
	
	public void setFormListener(FormListener listener) {
		this.formListener = listener;
	}
	
	public DbaFormPanel getDbaPanel() {
		return dbaPanel;
	}
	
	public void enableSaveBtn() {
		saveBtn.setEnabled(true);
	}
	public void disabedSaveBtn() {
		saveBtn.setEnabled(false);
	}

	
}
