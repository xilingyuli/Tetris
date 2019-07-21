package 俄罗斯方块;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
	throws Exception{
		super();
		this.setLayout(new FlowLayout());
		game = new GameView(15,10);
		watch = new WatchView();
		this.setBackground(Color.yellow);
		this.add(game);
		this.add(watch);
		this.pack();
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		downMoveTimer = new Timer(1000,new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e){
				// TODO Auto-generated method stub
				if(!game.moveUnit(3))
				{
					try {
						updateData();
					}catch (Exception e1){
						e1.printStackTrace();
					}
				}
			}
		});
		addListener();
		isPaused = false;
	}
	
	//重新开始新游戏
	protected void resetGame()
	throws Exception{
		isPaused = false;
		game.resetGame();
		watch.resetData();
		updateData();
	}
	
	//方块落到底部时触发，更新各种数据，并创造新方块落下
	protected int updateData()
	throws Exception{
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
			throws Exception{
		if(!isPaused)
			resetGame();
		else
			isPaused = false;
		downMoveTimer.start();
	}
	
	//游戏结束
	protected void stopGame()
		throws Exception{
		JOptionPane.showMessageDialog(null, "你的最终得分是："+watch.score, "俄罗斯方块", JOptionPane.ERROR_MESSAGE);
		String inputval = JOptionPane.showInputDialog("请输入你的昵称:");
		GameFrame.StoreData(inputval,watch.score);
		this.setVisible(false);
		String a[] = null;
		Main.main(a);
	}

	//连接数据库的方法
	public static Connection getCon() throws ClassNotFoundException, SQLException {
		//建立一个空连接
		Connection con = null;
		//通过class名找到驱动类
		Class.forName("com.mysql.cj.jdbc.Driver");

		//通过驱动类建立连接并给con对象赋值
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dong1?useSSL=false&serverTimezone=UTC","root","dby19991016");

		return con;

	}

	//数据存储
	protected static void StoreData(String inputval,int Score) throws SQLException,ClassNotFoundException{
		//调用getcon()与数据库建立连接
		Connection connection = getCon();
		//写sql语句 ?占位符
		String sql = "insert into dongdong values(null,?,?)";

		//预处理
		PreparedStatement ps = connection.prepareStatement(sql);
		//改变占位符的值
		ps.setString(1,inputval);
		ps.setInt(2,Score);

		//执行
		ps.executeUpdate();
	}

	private void addListener()
	{
		watch.addStartListener(
		new MouseListener()
		{

			@Override
			public void mouseClicked(MouseEvent e){
				try {
					restartGame();
				}catch (Exception e1){
					e1.printStackTrace();
				}
				// TODO Auto-generated method stub
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