package controller;

import javax.swing.SwingUtilities;

import view.MainFrame;

public class app {

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
		        new MainFrame();
				
			}
			
		});
    }
}
