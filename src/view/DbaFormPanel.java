package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;

import model.PlaceholderTextField;

public class DbaFormPanel extends JPanel  {
	
	private JLabel searchLabel;
	private JLabel priceLabel;
	private JLabel arrangeByLabel;
	private JTextField searchField;
	private PlaceholderTextField minPriceField;
	private PlaceholderTextField maxPriceField;
	private JButton searchBtn;
	private FormListener formListener; 
	private JList categoryList;
	private JLabel categoryLabel;
	private JCheckBox defectCheck;
	private JLabel defectLabel;
	private JCheckBox regionCheck;
	private JLabel regionLabel;
	private JRadioButton dateField;
	private JRadioButton priceField;
	private ButtonGroup arrangeByGroup;
	private FormPanel formPanel;

	public DbaFormPanel(FormPanel formPanel) {
		//Set up panel
		this.formPanel = formPanel;
		Dimension dim = getPreferredSize();
		dim.width = 340;
		dim.height = 650;
		setPreferredSize(dim);
		
		//Set up elements
		searchLabel = new JLabel("Search: ");
		categoryLabel = new JLabel("Categories:");
		searchField = new JTextField(15);
		categoryList = new JList();
		defectLabel = new JLabel("Defect:");
		defectCheck = new JCheckBox();
		searchBtn = new JButton("Get categories");
		dateField = new JRadioButton("Date");
		dateField.setActionCommand("date");
		priceField = new JRadioButton("Price");
		priceField.setActionCommand("price");
		arrangeByGroup = new ButtonGroup();
		arrangeByLabel = new JLabel("Arrange By:");
		regionLabel = new JLabel("CPH Region:");
		regionCheck = new JCheckBox();
		priceLabel = new JLabel("Price:");
		minPriceField = new PlaceholderTextField(5);
		maxPriceField = new PlaceholderTextField(5);
		minPriceField.setPlaceholder("min");
		maxPriceField.setPlaceholder("max");
		//set up hidden 
		//setInvisible();
		
		//Set up Mnemonmics
		searchBtn.setMnemonic(KeyEvent.VK_G);
		
		//Set up drop down
		DefaultComboBoxModel domainModel = new DefaultComboBoxModel();
		
		domainModel.addElement("Select domain:");
		domainModel.addElement("www.dba.dk");
		domainModel.addElement("www.ebay.com");
		domainModel.addElement("www.amazon.de");
		
	
		//set up arrangeBy Radio Buttons
		arrangeByGroup.add(dateField);
		arrangeByGroup.add(priceField);
		dateField.setSelected(true);
		
		// Set up searchCategory box 
		DefaultListModel categoryModel = new DefaultListModel();
		categoryList.setPreferredSize(new Dimension(180,300));
		categoryList.setBorder(BorderFactory.createEtchedBorder());
		categoryList.setSelectedIndex(0);
		 
		//Set up Search button 
		searchBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				Thread tn = new Thread(new Runnable() {

					@Override
					public void run() {

						String name = searchField.getText();
						FormEvent ev = new FormEvent (this, name);
						
						
						formListener.formCategorySearchEventOccurred(ev);	
					}
				});
				if(formListener != null) {
					tn.start();	
				}
			}
		});
		
		Border innderBorder = BorderFactory.createTitledBorder("DBA:");
		Border outerBorder = BorderFactory.createEmptyBorder(10, 10, -5, 10);
		setBorder(BorderFactory.createCompoundBorder(innderBorder, outerBorder));
		

		layoutComponents();
	}
	
	public void layoutComponents() {
		
		setLayout(new GridBagLayout());
		
		GridBagConstraints gc = new GridBagConstraints();
		
		
		/////////////////////////// Line ///////////////////
		
		gc.gridy = 0;
		
		gc.weighty = 0.1;
		
		
		gc.gridx = 1;
		gc.anchor = (GridBagConstraints.FIRST_LINE_START);
		gc.insets = new Insets(0,0,0,0);
		
		//add(domainBox, gc);
		
		/////////////////////////// Line ///////////////////
		
		gc.gridy++;
		
		//gc.weighty = 0.9;

		gc.gridx = 0;
		gc.anchor = (GridBagConstraints.FIRST_LINE_END);
		gc.insets = new Insets(7,0,0,5);
		add(searchLabel, gc);
		
		gc.gridx = 1;
		gc.anchor = (GridBagConstraints.FIRST_LINE_START);
		gc.insets = new Insets(0,0,0,0);
		add((searchField), gc);
		
	/////////////////////////// Line ///////////////////
		
		gc.gridy++;


		gc.gridx = 1;
		gc.anchor = (GridBagConstraints.FIRST_LINE_START);
		gc.insets = new Insets(4,0,0,0);
		add(searchBtn, gc);	
		
		
		/////////////////////////// Line ///////////////////
		
		gc.gridy++;
		
		gc.gridx = 0;
		gc.anchor = (GridBagConstraints.FIRST_LINE_END);
		gc.insets = new Insets(7,0,0,11);
		add(categoryLabel, gc);
		
		gc.gridx = 1;
		gc.anchor = (GridBagConstraints.FIRST_LINE_START);
		gc.insets = new Insets(0,0,0,0);
		add((categoryList), gc);
		
	/////////////////////////// Line ///////////////////
		
		gc.gridy++;
		
		gc.gridx = 0;
		gc.anchor = (GridBagConstraints.FIRST_LINE_END);
		gc.insets = new Insets(0,0,0,11);
		add(defectLabel, gc);
		
		gc.gridx = 1;
		gc.anchor = (GridBagConstraints.FIRST_LINE_START);
		gc.insets = new Insets(-1,0,0,0);
		add(defectCheck, gc);
		
		/////////////////////////// Line ///////////////////
		
		gc.gridy++;
		
		gc.gridx = 0;
		gc.anchor = (GridBagConstraints.FIRST_LINE_END);
		gc.insets = new Insets(0,0,0,11);
		add(regionLabel, gc);
		
		gc.gridx = 1;
		gc.anchor = (GridBagConstraints.FIRST_LINE_START);
		gc.insets = new Insets(-1,0,0,0);
		add(regionCheck, gc);
		
		/////////////////////////// Line ///////////////////
		
		gc.gridy++;
		
		gc.gridx = 0;
		gc.anchor = (GridBagConstraints.FIRST_LINE_END);
		gc.insets = new Insets(7,0,0,11);
		add(priceLabel, gc);
		
		gc.gridx = 1;
		gc.anchor = (GridBagConstraints.FIRST_LINE_START);
		gc.insets = new Insets(0,0,0,0);
		add(minPriceField, gc);
		
		gc.gridx = 1;
		gc.anchor = (GridBagConstraints.FIRST_LINE_END);
		gc.insets = new Insets(0,0,0,0);
		add(maxPriceField, gc);
		

	}
	
	
	
	public void setFormListener(FormListener listener) {
		this.formListener = listener;
	}
	
	public void setCategories(ArrayList<String> list) {
		DefaultListModel<String> categoryModel = new DefaultListModel<String>();
		for (int i = 0 ; i < list.size(); i++) {
			System.out.println(list.get(i));
			categoryModel.addElement(list.get(i));
		}
		categoryList.setModel(categoryModel);
	}
	
	public FormEvent generateFormEvent() {
		String name = searchField.getText();
		String searchCat = (String) categoryList.getSelectedValue();
		String domain = "www.dba.dk";
		Boolean defect = defectCheck.isSelected();
		Boolean region = regionCheck.isSelected();
		String maxPrice = maxPriceField.getText();
		String minPrice = minPriceField.getText();
		
		return new FormEvent (this, domain, name, searchCat, defect, region, minPrice, maxPrice);
	}
	
}

