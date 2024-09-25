package game;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class InGamePanel extends JPanel implements Runnable{

    private long startingNanoTime;
	private long lastNanoTime;
	
	private float currentTime;
	private float deltaTime;
	
	private float pos = 0;
	
	Thread thread;

	public InGamePanel() {
	}
	
    @Override
	public void addNotify() {
		super.addNotify();
		if(thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}
	
	@Override
	public void run() {
		startingNanoTime = System.nanoTime();
		while(true) {
			long currentNanoTime = System.nanoTime() - startingNanoTime;
			deltaTime = (currentNanoTime - lastNanoTime) * 1e-9f;
			currentTime = currentNanoTime * 1e-9f;
			updateGame();
			repaint();
		}
	}
	
	private void updateGame() {
		pos = 110 + 100 * (float)Math.sin((double)currentTime);
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.clearRect(0, 0, getWidth(), getHeight());
		
		g.setColor(Color.red);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		g.setColor(Color.yellow);
		g.fillRect((int) pos, 10, 100, 100);
	}

}
