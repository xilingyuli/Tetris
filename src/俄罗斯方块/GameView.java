package 俄罗斯方块;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameView extends JPanel{
	
	/**
	 * 进行游戏的主面板
	 */
	private static final long serialVersionUID = 2L;
	private JLabel[][] box;
	private Unit movingUnit;
	private LinkedList<Unit> nextUnit;
	GameView(int x,int y)
	{
		 super();
		 this.setLayout(new GridLayout(x,y));
		 box = new JLabel[x][y];
		 for(int i=0;i<x;i++)
			 for(int j=0;j<y;j++)
			 {
				 box[i][j] = new JLabel();
				 box[i][j].setPreferredSize(new Dimension(40,40));
				 box[i][j].setBackground(Color.GREEN);
				 this.add(box[i][j]);
			 }
		 nextUnit = new LinkedList<Unit>();
	}
	
	//返回消去的行数
	public int removeRow()
	{
		movingUnit = null;
		int count=0;
		for(int i=box.length-1;i>=0;i--)
		{
			int j;
			for(j=0;j<box[0].length;j++)
				if(!box[i][j].isOpaque())
					break;
			if(j==box[0].length)
			{
				count++;
				for(int k=i;k>0;k--)
					for(int l=0;l<box[0].length;l++)
						box[k][l].setOpaque(box[k-1][l].isOpaque());
				i++;
			}
		}
		return count;
	}
	//增加行
	public void addRow(int n)
	{
		Random r = new Random();
		for(int i=0;i<box.length-n;i++)
			for(int j=0;j<box[0].length;j++)
				box[i][j].setOpaque(box[i+n][j].isOpaque());
		for(int i=box.length-n;i<box.length;i++)
			for(int j=0;j<box[0].length;j++)
				box[i][j].setOpaque(r.nextBoolean());
		
	}
	//新增一个即将显示的方块
	public Unit creatNextUnit()
	{
		if(nextUnit.isEmpty())
			nextUnit.add(new Unit(new Point(0,box[0].length/2-1)));
		nextUnit.add(new Unit(new Point(0,box[0].length/2-1)));
		return nextUnit.getLast();
	}
	
	//试着绘制新方块，若不能绘制说明已顶满，游戏结束
	public boolean getNextUnit()
	{
		return paintUnit(nextUnit.poll());
	}
	
	//根据给出的k值试着移动或变形方块，若方块落到底部，返回false，否则返回true
	synchronized boolean moveUnit(int k)
	{
		if(movingUnit==null)
			return true;
		switch(k)
		{
		case 0:
			if(!paintUnit(movingUnit.getChangedUnit(true, 0, 0)))
				if(!paintUnit(movingUnit.getChangedUnit(true, 0, -1)))
					if(!paintUnit(movingUnit.getChangedUnit(true, 0, -2)))
						if(!paintUnit(movingUnit.getChangedUnit(true, 0, -3)));
		break;
		case 1:
			paintUnit(movingUnit.getChangedUnit(false, 0, -1));
			break;
		case 2:
			paintUnit(movingUnit.getChangedUnit(false, 0, 1));
			break;
		case 3:
			return paintUnit(movingUnit.getChangedUnit(false, 1, 0));
		}
		return true;
	}
	
	//试着用给出的unit绘制方块，若撞到边界或其他方块不能绘制就返回false
	synchronized boolean paintUnit(Unit unit)
	{
		List<Point> add = new ArrayList<Point>();
		for(Point n:unit.getPaintLocation())
			add.add(n);
		if(movingUnit!=null)
			for(Point b:movingUnit.getPaintLocation())
				add.remove(b);
		for(Point u:add)
			if(u.x<0||u.x>=box.length||u.y<0||u.y>=box[0].length||box[u.x][u.y].isOpaque())
				return false;
		if(movingUnit!=null)
			for(Point u:movingUnit.getPaintLocation())
				box[u.x][u.y].setOpaque(false);
		movingUnit = unit;
		for(Point u:movingUnit.getPaintLocation())
			box[u.x][u.y].setOpaque(true);
		this.updateUI();
		return true;
	}
	
	//重置新游戏
	public void resetGame()
	{
		for(int i=0;i<box.length;i++)
			 for(int j=0;j<box[0].length;j++)
					 box[i][j].setOpaque(false);
		movingUnit = null;
		nextUnit.clear();
	}
	
	//结束游戏
	public void endGame()
	{
		movingUnit = null;
	}
}