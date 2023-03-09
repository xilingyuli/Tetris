package Tetris;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class GameFrame extends JFrame{

	/**
	 * ����ģʽ��������Ϸ���
	 */
	private static final long serialVersionUID = 1L;
	
	protected GameView game;
	protected WatchView watch;
	protected Timer downMoveTimer;
	
	private int speed;
	private boolean isPaused;
	File f;
	URI uri;
	URL url;


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
		this.addWindowListener(new WindowAdapter() {
		});
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
	
	//���¿�ʼ����Ϸ
	protected void resetGame()
	throws Exception{
		isPaused = false;
		game.resetGame();
		watch.resetData();
		updateData();
	}
	
	//�����䵽�ײ�ʱ���������¸������ݣ��������·�������
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
	
	//��ͣ��Ϸ
	protected void pauseGame()
	{
		downMoveTimer.stop();
		isPaused = true;
	}
	
	//�ָ���Ϸ��ʼ����Ϸ
	protected void restartGame()
			throws Exception{
		if(!isPaused)
			resetGame();
		else
			isPaused = false;
		downMoveTimer.start();
	}
	
	//��Ϸ����
	protected void stopGame()
		throws Exception{
		JOptionPane.showMessageDialog(null, "������յ÷��ǣ�"+watch.score, "Tetris", JOptionPane.ERROR_MESSAGE);
		String inputval = JOptionPane.showInputDialog("����������ǳ�:");
		GameFrame.StoreData(inputval,watch.score);
		this.setVisible(false);
		String a[] = null;
		Main.main(a);
	}

	//�������ݿ�ķ���
	public static Connection getCon() throws ClassNotFoundException, SQLException {
		//����һ��������
		Connection con = null;
		//ͨ��class���ҵ�������
		Class.forName("com.mysql.cj.jdbc.Driver");

		//ͨ�������ཨ�����Ӳ���con����ֵ
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dong1?useSSL=false&serverTimezone=UTC","root","dby19991016");

		return con;

	}

	//���ݴ洢
	protected static void StoreData(String inputval,int Score) throws SQLException,ClassNotFoundException{
		//����getcon()�����ݿ⽨������
		Connection connection = getCon();
		//дsql��� ?ռλ��
		String sql = "insert into dongdong values(null,?,?)";

		//Ԥ����
		PreparedStatement ps = connection.prepareStatement(sql);
		//�ı�ռλ����ֵ
		ps.setString(1,inputval);
		ps.setInt(2,Score);

		//ִ��
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