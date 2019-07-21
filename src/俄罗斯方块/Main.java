package 俄罗斯方块;

import java.awt.EventQueue;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Main {

	/**
	 * 0.本游戏实现了作业所要求的几乎全部功能，详细功能如下：
	 * 
	 * 1.基本操作功能，通过键盘方向键操纵方块，控制函数算法均是自己写的
	 * 
	 * 2.游戏过程中计分，并根据分数进入新关卡，加快方块掉落速度
	 * 
	 * 3.可在右侧面板观察到下一个方块和现阶段分数、关卡
	 * 
	 * 4.游戏有单人模式和双人模式，游戏开始时弹出消息框可选择模式
	 * 
	 * 5.单人模式下可随时开始和暂停，游戏结束后告知最终得分
	 * 
	 * 6.可联网进行双人模式，默认双人模式的ip设在本地，可更改
	 * 
	 * 7.双人模式下可看到对方战况，可通过自己消去多行为对方增长行数，有一方失败时游戏结束，并通知双方成功或失败
	 * 
	 */
	public static void main(String[] args) throws SQLException,ClassNotFoundException {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				Object[] options ={ "单人", "双人","排行榜"};
				int m = JOptionPane.showOptionDialog(null, "请选择游戏模式", "俄罗斯方块",JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, "单人");
				if(m==0)
				{
					try { //选择单人模式
						singleGame();
					}catch (Exception e1){
						e1.printStackTrace();
					}
				}
				else if(m==1){ //选择双人模式
					try {
						doubleGame();
					}catch (Exception e1){
						e1.printStackTrace();
					}
				}
				else if(m==2){ //进入排行查看
					try {
						Ranking();
					}catch (Exception e1){
						e1.printStackTrace();
					}
                }

			}
			private void singleGame() throws Exception{  //构造单人游戏界面面板
				GameFrame player = new GameFrame();
				player.setVisible(true);
			}
			private void doubleGame() throws Exception{ //构造双人游戏界面面板
				DoubleGameFrame player1 = new DoubleGameFrame("192.168.17.185",5000,4000);
				player1.setVisible(true);
			}

			private void Ranking()throws Exception{  //构造排行面板
				RankingView rank = new RankingView();
				rank.setVisible(true);
            }
		});
	}

}
