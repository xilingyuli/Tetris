package 俄罗斯方块;

import java.awt.Point;
import java.util.Random;

public class UnitKey {
	/**
	 * 记录不同种类方块坐标的类
	 */
	private static final Point[] a1 = new Point[] {new Point(0,0),new Point(1,0),new Point(0,1),new Point(1,1)};
	private static final Point[][] a = new Point[][]{a1,a1,a1,a1};
	
	private static final Point[] b1 = new Point[] {new Point(0,0),new Point(0,1),new Point(0,2),new Point(0,3)};
	private static final Point[] b2 = new Point[] {new Point(0,0),new Point(1,0),new Point(2,0),new Point(3,0)};
	private static final Point[][] b = new Point[][]{b1,b2,b1,b2};
	
	private static final Point[] c1 = new Point[] {new Point(0,0),new Point(0,1),new Point(0,2),new Point(1,1)};
	private static final Point[] c2 = new Point[] {new Point(0,1),new Point(1,1),new Point(2,1),new Point(1,0)};
	private static final Point[] c3 = new Point[] {new Point(1,0),new Point(1,1),new Point(1,2),new Point(0,1)};
	private static final Point[] c4 = new Point[] {new Point(0,0),new Point(1,0),new Point(2,0),new Point(1,1)};
	private static final Point[][] c = new Point[][]{c1,c2,c3,c4};
	
	private static final Point[] d1 = new Point[] {new Point(0,0),new Point(1,0),new Point(1,1),new Point(2,1)};
	private static final Point[] d2 = new Point[] {new Point(1,0),new Point(1,1),new Point(0,1),new Point(0,2)};
	private static final Point[][] d = new Point[][]{d1,d2,d1,d2};
	
	private static final Point[] e1 = new Point[] {new Point(0,1),new Point(1,1),new Point(1,0),new Point(2,0)};
	private static final Point[] e2 = new Point[] {new Point(0,0),new Point(0,1),new Point(1,1),new Point(1,2)};
	private static final Point[][] e = new Point[][]{e1,e2,e1,e2};
	
	private static final Point[] f1 = new Point[] {new Point(0,0),new Point(0,1),new Point(0,2),new Point(1,2)};
	private static final Point[] f2 = new Point[] {new Point(0,1),new Point(1,1),new Point(2,1),new Point(2,0)};
	private static final Point[] f3 = new Point[] {new Point(0,0),new Point(1,0),new Point(1,1),new Point(1,2)};
	private static final Point[] f4 = new Point[] {new Point(0,1),new Point(0,0),new Point(1,0),new Point(2,0)};
	private static final Point[][] f = new Point[][]{f1,f2,f3,f4};
	
	private static final Point[] g1 = new Point[] {new Point(0,0),new Point(1,0),new Point(2,0),new Point(2,1)};
	private static final Point[] g2 = new Point[] {new Point(0,2),new Point(0,1),new Point(0,0),new Point(1,0)};
	private static final Point[] g3 = new Point[] {new Point(0,0),new Point(0,1),new Point(1,1),new Point(2,1)};
	private static final Point[] g4 = new Point[] {new Point(0,2),new Point(1,2),new Point(1,1),new Point(1,0)};
	private static final Point[][] g = new Point[][]{g1,g2,g3,g4};
	
	//所有方块形状列表
	private static final Point[][][] list = new Point[][][]{a,b,c,d,e,f,g};
	
	//通过key值获取方块的形状，用Point数组来描述
	public static Point[] getShapeByKey(int[] key)
	{
		return list[key[0]][key[1]];
	}
	
	//产生一个随机的key值
	public static int[] getRandomKey()
	{
		Random r = new Random();
		int i = r.nextInt(list.length);
		int j = r.nextInt(list[i].length);
		return new int[]{i,j};
	}
	
	//获取方块变形后的key值
	public static int[] getNextKey(int[] key)
	{
		return new int[]{key[0],(key[1]+1)%list[key[0]].length};
	}
}
