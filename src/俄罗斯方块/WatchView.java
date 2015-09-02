package 俄罗斯方块;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WatchView extends JPanel{

	/**
	 * 观察游戏得分，控制单人模式开始暂停的类
	 */
	private static final long serialVersionUID = 3L;
	private JButton start,pause;
	private JLabel[][] nextUnit;
	private JPanel nextUnitPanel;
	private JLabel s,l;
	private FlowLayout layout;
	
	public int level,score;
	
	WatchView()
	{
		start = new JButton("开始");
		pause = new JButton("暂停");
		s = new JLabel("分数:0",JLabel.CENTER);
		l = new JLabel("关卡:1",JLabel.CENTER);
		s.setPreferredSize(new Dimension(100,50));
		l.setPreferredSize(new Dimension(100,50));
		resetData();
		nextUnitPanel = new JPanel(new GridLayout(4,4));
		nextUnit = new JLabel[4][4];
		for(int i=0;i<4;i++)
			for(int j=0;j<4;j++)
			{
				nextUnit[i][j] = new JLabel();
				nextUnit[i][j].setPreferredSize(new Dimension(20,20));
				nextUnit[i][j].setBackground(Color.YELLOW);
				nextUnitPanel.add(nextUnit[i][j]);
			}
		
		layout = new FlowLayout();
		layout.setVgap(30);
		this.setLayout(layout);
		this.add(nextUnitPanel);
		this.add(l);
		this.add(s);
		this.add(start);
		this.add(pause);
		this.setPreferredSize(new Dimension(100,500));
	}
	
	//初始化关卡及分数信息
	public void resetData()
	{
		score = 0;
		s.setText("分数:"+score);
		level = 1;
		l.setText("关卡:"+level);
	}
	
	//提供为按钮添加外部监听器的方法
	public void addStartListener(MouseListener m,KeyListener k)
	{
		start.addMouseListener(m);
		start.addKeyListener(k);
	}
	public void addPauseListener(MouseListener l)
	{
		pause.addMouseListener(l);
	}
	
	//更新面板上的分数信息
	public void updateData(int c,Unit p)
	{
		score += c*100;
		s.setText("分数:"+score);
		level = score/1000 + 1;
		l.setText("关卡:"+level);
		for(int i=0;i<4;i++)
			for(int j=0;j<4;j++)
				nextUnit[i][j].setOpaque(false);
		for(Point u:p.getShape())
			nextUnit[u.x][u.y].setOpaque(true);
		nextUnitPanel.updateUI();
	}
	
	public int getLevel()
	{
		return level;
	}
}
