package gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class Window extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	public Window() {
        super();
        build();
		pack();	
	}

	public Window(String name) {
		super(name);
        build();
		pack();
	}

	protected void build() {
		Container contentPane = getContentPane();
		contentPane.setLayout(new FlowLayout());
		contentPane.setMinimumSize(new Dimension(200, 200));
		setContentPane(contentPane);
		
		setLocation(new Point(100, 100));
		setResizable(true);
		setDefaultLookAndFeelDecorated(true); 
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setBackground(Color.BLACK);
        setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {		
	}			
}


