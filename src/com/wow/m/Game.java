package com.wow.m;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JFrame implements Globals {

	private Board panel1=new Board();
	private JPanel container = new JPanel();
	

	public Game() {
		this.setTitle("Wowo");
		this.setSize(BOARD_WIDTH, BOARD_HEIGHT);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		container.setBackground(Color.white);
	    container.setLayout(new BorderLayout());
	    container.add(panel1, BorderLayout.CENTER);
	    this.setContentPane(container);
		
		this.setVisible(true);
		
		panel1.initBoard();

	}

}
