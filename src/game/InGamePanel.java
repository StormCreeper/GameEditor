package game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.*;
import javax.swing.JPanel;

public class InGamePanel extends JPanel implements Runnable, KeyListener {

	private long startingNanoTime;
	private long lastNanoTime;

	private double currentTime;
	private double deltaTime;

	private final GameLogic gameLogic;

	Thread thread;
	boolean isRunning = true;

	public InGamePanel(Tileset tileset, Tilemap tileMap) {
		gameLogic = new GameLogic(this, tileMap);
		addMouseListener(new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent e){
				System.out.println("Mouse pressed !");
				gameLogic.mousePressed(e);
			}


			@Override
			public void mouseReleased(MouseEvent e){
				System.out.println("Mouse released !");
				gameLogic.mouseReleased(e);
			}
		});
		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e){
				System.out.println("Mouse dragged !");
				gameLogic.mouseDragged(e);
			}
		});
	}

	@Override
	public void addNotify() {
		super.addNotify();
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}

	public void stopGame() {
		isRunning = false;
	}

	@Override
	public void run() {
		startingNanoTime = System.nanoTime();
		addKeyListener(this);
		requestFocus();
		while (isRunning) {
			long currentNanoTime = System.nanoTime() - startingNanoTime;
			deltaTime = (currentNanoTime - lastNanoTime) * 1e-9f;
			currentTime = currentNanoTime * 1e-9f;
			lastNanoTime = currentNanoTime;
			updateGame();
			repaint();
		}
	}

	private void updateGame() {
		gameLogic.update(currentTime, deltaTime);
	}

	@Override
	protected void paintComponent(Graphics g) {
		gameLogic.draw((Graphics2D) g);
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		gameLogic.keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		gameLogic.keyReleased(e);
	}

}
