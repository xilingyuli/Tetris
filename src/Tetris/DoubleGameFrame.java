package Tetris;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class DoubleGameFrame extends GameFrame{

	/**
	 * ˫��ģʽ��������Ϸ���
	 */
	private static final long serialVersionUID = 5L;
	private CommunicateView paint;
	private Thread sendThread,receiveThread,sendDataThread,receiveDataThread;
	private Timer receiveTimer,receiveDataTimer;
	private ServerSocket s,sd;
	private Socket send,receive,sendData,receiveData;
	private int sendPort,receivePort;
	private String sendIP;
	OutputStream out;
	Robot robot;
	DoubleGameFrame(String ip,int sp,int rp)
	throws Exception{
		super();
		sendIP = ip;
		sendPort = sp;
		receivePort = rp;
		paint = new CommunicateView(this.getWidth()/2,this.getHeight()/2);
		add(paint);
		pack();
		new LinkedList<Integer>();
		try {
			robot = new Robot();
		} catch (AWTException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//���ܶԷ�ս�������Ƶ��߳�
		receiveThread = new Thread(){
			public void run() {
	             // compute primes larger than minPrime
				try {
					s = new ServerSocket(receivePort);
					receive = s.accept();
					InputStream in = receive.getInputStream();
					receiveTimer = new Timer(10, new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							try {
								paint.setImage(ImageIO.read(in));
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								//e1.printStackTrace();
								receiveTimer.stop();
								
							}
						}
					});
					receiveTimer.start();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	         }

		};
		
		//���ͼ���ս�����߳�
		sendThread = new Thread(){
			public void run() {
	             // compute primes larger than minPrime
				while(true)
				{
					try {
							send = new Socket(sendIP,sendPort);
							OutputStream out = send.getOutputStream();
							while(true)
							{
								try {
									ImageIO.write(creatView(), "png", out);
									out.flush();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									//e1.printStackTrace();
									winGame();
									break;
								}
								catch (Exception e1) {
									// TODO Auto-generated catch block
									//e1.printStackTrace();
									break;
								}
							}
							break;
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
	         }

		};
		
		//�������ݵ��̣߳���ȡһ�������
		receiveDataThread = new Thread(){
			public void run() {
	             // compute primes larger than minPrime
				try {
					sd = new ServerSocket(receivePort+1);
					receiveData = sd.accept();
					InputStream in = receiveData.getInputStream();
					receiveDataTimer = new Timer(1000, new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							try {
								if(in.available()!=0)
									game.addRow(in.read());
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								//e1.printStackTrace();
								receiveDataTimer.stop();
							}
						}
					});
					receiveDataTimer.start();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	         }

		};
		
		//�������ݵ��̣߳���ҪʱΪ�Է���������
		sendDataThread = new Thread(){
			public void run() {
	             // compute primes larger than minPrime
				while(true)
				{
					try {
							sendData = new Socket(sendIP,sendPort+1);
							out = sendData.getOutputStream();
							break;
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
	         }

		};
		
		resetGame();
	}

	//���������ؼ���ս����ͼ
	public BufferedImage creatView()
	{
		int x = this.getBounds().x+game.getBounds().x;
		int y = this.getBounds().y+game.getBounds().y;
		int w = watch.getBounds().width + watch.getBounds().x;
		int h = this.getBounds().height;
		return robot.createScreenCapture(new Rectangle(x,y,w,h));
	}
	@Override
	protected int updateData()
	{
		int i = 0;
		try {
			i = super.updateData();
		}catch (Exception e1){
			e1.printStackTrace();
		}
		if(i>1)
			try {
				out.write(i-1);
				out.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return i;
	}
	@Override
	protected void resetGame()
	{
		try {
			super.resetGame();
		}catch (Exception e1){
			e1.printStackTrace();
		}
		this.sendThread.start();
		this.receiveThread.start();
		this.sendDataThread.start();
		this.receiveDataThread.start();
		downMoveTimer.start();
	}
	@Override
	protected void restartGame()
	{
		
	}
	@Override
	protected void pauseGame()
	{
		
	}
	//��Ϸʧ��
	@Override
	protected void stopGame()
	{
		JOptionPane.showMessageDialog(null, "������", "Tetris", JOptionPane.ERROR_MESSAGE);
		receiveThread.interrupt();
		sendThread.interrupt();
		try {
			send.close();
			receive.close();
			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//Ӯ����Ϸ��������ʾ���ͷ���Դ
	protected void winGame()
	{
		JOptionPane.showMessageDialog(null, "��Ӯ�ˣ���ǰ������"+watch.score, "Tetris", JOptionPane.OK_OPTION);
		game.endGame();
		sendThread.interrupt();
		receiveThread.interrupt();
		try {
			send.close();
			receive.close();
			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
