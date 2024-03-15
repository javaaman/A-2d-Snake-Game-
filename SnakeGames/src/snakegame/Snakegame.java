package snakegame;

import javax.swing.JFrame;

public class Snakegame extends JFrame{
	
	Snakegame(){
		super("Snake Games");
		
		add(new Board());
		pack();
		
		
		setLocationRelativeTo(null);
		setResizable(false);
	
	}
public static void main(String[] args) {
	new Snakegame().setVisible(true);;
	
}
}
