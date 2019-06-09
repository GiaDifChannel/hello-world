package it.giadif.supermario;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Input implements KeyListener, MouseListener, MouseMotionListener {

	public boolean up, down, left, right;
	public boolean ctrl, shift, space;
	public boolean esc;
	
	public int mouseX, mouseY;
	
	@Override
	public void mouseDragged(MouseEvent e)
	{
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_UP) up = true;
		if(e.getKeyCode() == KeyEvent.VK_DOWN) down = true;
		if(e.getKeyCode() == KeyEvent.VK_LEFT) left = true;
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) right = true;
		if(e.getKeyCode() == KeyEvent.VK_CONTROL) ctrl = true;
		if(e.getKeyCode() == KeyEvent.VK_SHIFT) shift = true;
		if(e.getKeyCode() == KeyEvent.VK_SPACE) space = true;
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) esc = true;
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_UP) up = false;
		if(e.getKeyCode() == KeyEvent.VK_DOWN) down = false;
		if(e.getKeyCode() == KeyEvent.VK_LEFT) left = false;
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) right = false;
		if(e.getKeyCode() == KeyEvent.VK_CONTROL) ctrl = false;
		if(e.getKeyCode() == KeyEvent.VK_SHIFT) shift = false;
		if(e.getKeyCode() == KeyEvent.VK_SPACE) space = false;
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) esc = false;
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
	}

}
