package gui.elements;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import gui.GuiLogic;

public class ImageButton extends JButton implements MouseListener{

	private static final long serialVersionUID = 1L;
	static GuiLogic logic = GuiLogic.getInstance();

	public ImageButton(String image) {
		this(image, -1, -1);
	}
	
	public ImageButton(String image, int width, int height) {
		super("", logic.getImage(image, width, height));
		setBorderPainted(false);
		setContentAreaFilled(false); 
		setFocusPainted(false);
        setOpaque(false);
        setBorder(BorderFactory.createCompoundBorder(
	               BorderFactory.createLineBorder(Color.orange, 2),
	               BorderFactory.createLineBorder(Color.orange, 0)));
        addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		setBorder(BorderFactory.createCompoundBorder(
	               BorderFactory.createLineBorder(Color.red, 2),
	               BorderFactory.createLineBorder(Color.orange, 0)));
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		setBorder(BorderFactory.createCompoundBorder(
	               BorderFactory.createLineBorder(Color.orange, 2),
	               BorderFactory.createLineBorder(Color.orange, 0)));
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		setBorderPainted(true);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		setBorderPainted(false);
		setBorder(BorderFactory.createCompoundBorder(
	               BorderFactory.createLineBorder(Color.orange, 2),
	               BorderFactory.createLineBorder(Color.orange, 0)));
	}
}