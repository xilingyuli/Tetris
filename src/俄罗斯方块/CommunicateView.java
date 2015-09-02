package 俄罗斯方块;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class CommunicateView extends JPanel{

	/**
	 * 双人模式中控制观察对方战况面板的类
	 */
	private static final long serialVersionUID = 4L;
	private Image img;
	
	CommunicateView(int x,int y)
	{
		super();
		this.setPreferredSize(new Dimension(x,y));
	}
	
	//绘制对方战况
	public void setImage(BufferedImage img)
	{
		this.img = img;
		this.repaint();
	}
	
	public void paint(Graphics g)
	{
		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), null);
	}
}
