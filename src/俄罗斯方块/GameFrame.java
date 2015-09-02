package 俄罗斯方块;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class GameFrame extends JFrame{

	/**
	 * 单人模式的整体游戏框架
	 */
	private static final long serialVersionUID = 1L;
	
	protected GameView game;
	protected WatchView watch;
	protected Timer downMoveTimer;
	
	private int speed;
	private boolean isPaused;
	
	GameFrame()
	{
		super();
		this.setLayout(new FlowLayout());
		game = new GameView(15,10);
		watch = new WatchView();
		this.add(game);
		this.add(watch);
		this.pack();
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		downMoveTimer = new Timer(1000,new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(!game.moveUnit(3))
					updateData();
			}
		});
		addListener();
		isPaused = false;
	}
	
	//重新开始新游戏
	protected void resetGame()
	{
		isPaused = false;
		game.resetGame();
		watch.resetData();
		updateData();
	}
	
	//方块落到底部时触发，更新各种数据，并创造新方块落下
	protected int updateData()
	{
		int c = game.removeRow();
		watch.updateData(c, game.creatNextUnit());
		speed = (int) (1000*Math.pow(0.75, watch.getLevel()-1));
		if(game.getNextUnit())
			downMoveTimer.setDelay(speed);
		else
			stopGame();
		return c;
	}
	
	//暂停游戏
	protected void pauseGame()
	{
		downMoveTimer.stop();
		isPaused = true;
	}
	
	//恢复游戏或开始新游戏
	protected void restartGame()
	{
		if(!isPaused)
			resetGame();
		else
			isPaused = false;
		downMoveTimer.start();
	}
	
	//游戏结束
	protected void stopGame()
	{
		JOptionPane.showMessageDialog(null, "你的最终得分是："+watch.score, "俄罗斯方块", JOptionPane.ERROR_MESSAGE);
	}
	private void addListener()
	{
		watch.addStartListener(
		new MouseListener()
		{

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				restartGame();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		}, 
		new KeyListener()
		{
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if(arg0.getKeyCode()==KeyEvent.VK_LEFT)
					game.moveUnit(1);
				else if(arg0.getKeyCode()==KeyEvent.VK_RIGHT)
					game.moveUnit(2);
				else if(arg0.getKeyCode()==KeyEvent.VK_UP)
					game.moveUnit(0);
				else if(arg0.getKeyCode()==KeyEvent.VK_DOWN)
					game.moveUnit(3);
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		watch.addPauseListener(
		new MouseListener()
		{

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				pauseGame();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
}