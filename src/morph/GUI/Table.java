package morph.GUI;

import java.awt.*;

import javax.swing.*;

public class Table {
	
	
	private final JFrame gameFrame;
	
	private static Dimension OUTER_FRAME_DIMENSION = new Dimension(600,600);
	
	public Table() {
		this.gameFrame = new JFrame("Morph");
		
		this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
		this.gameFrame.setVisible(true);
	}

}