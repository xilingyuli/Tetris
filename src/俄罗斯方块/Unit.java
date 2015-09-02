package 俄罗斯方块;

import java.awt.Point;
public class Unit {
	/**
	 * 对于每个方块所写的类
	 */
	 private Point location;
	 private int[] key;
	 Unit(Point l)
	 {
		 location = l;
		 key = UnitKey.getRandomKey();
	 }
	 Unit(Point l,int[] k)
	 {
		 location = l;
		 key = k;
	 }
	 
	 //获取当前方块的形状
	 public Point[] getShape()
	 {
		 return UnitKey.getShapeByKey(key);
	 }
	 
	 //按照指令获取改变形状位置的新方块
	 public Unit getChangedUnit(boolean isShapeChanged,int x,int y)
	 {
		 Point l = new Point(this.location.x + x,this.location.y + y);
		 int[] k;
		 if(isShapeChanged)
			 k = UnitKey.getNextKey(this.key);
		 else
			 k = new int[]{this.key[0],this.key[1]};
		 return new Unit(l,k);
	 }
	 
	 //获取当前方块绘出的坐标
	 public Point[] getPaintLocation()
	 {
		 Point[] s = UnitKey.getShapeByKey(key);
		 Point[] p = new Point[s.length];
		 for(int i=0;i<s.length;i++)
			 p[i] = new Point(s[i].x+location.x,s[i].y+location.y);
		 return p;
	 }
}