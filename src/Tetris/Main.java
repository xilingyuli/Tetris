package Tetris;

import java.awt.EventQueue;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Main {

	/**
	 * 0.����Ϸʵ������ҵ��Ҫ��ļ���ȫ�����ܣ���ϸ�������£�
	 * 
	 * 1.�����������ܣ�ͨ�����̷�������ݷ��飬���ƺ����㷨�����Լ�д��
	 * 
	 * 2.��Ϸ�����мƷ֣������ݷ��������¹ؿ����ӿ췽������ٶ�
	 * 
	 * 3.�����Ҳ����۲쵽��һ��������ֽ׶η������ؿ�
	 * 
	 * 4.��Ϸ�е���ģʽ��˫��ģʽ����Ϸ��ʼʱ������Ϣ���ѡ��ģʽ
	 * 
	 * 5.����ģʽ�¿���ʱ��ʼ����ͣ����Ϸ�������֪���յ÷�
	 * 
	 * 6.����������˫��ģʽ��Ĭ��˫��ģʽ��ip���ڱ��أ��ɸ���
	 * 
	 * 7.˫��ģʽ�¿ɿ����Է�ս������ͨ���Լ���ȥ����Ϊ�Է�������������һ��ʧ��ʱ��Ϸ��������֪ͨ˫���ɹ���ʧ��
	 * 
	 */
	public static void main(String[] args) throws SQLException,ClassNotFoundException {

				Object[] options ={ "����", "˫��","���а�"};
				int m = JOptionPane.showOptionDialog(null, "��ѡ����Ϸģʽ", "Tetris",JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, "����");
				if(m==0)
				{
					try { //ѡ����ģʽ
						singleGame();
					}catch (Exception e1){
						e1.printStackTrace();
					}
				}
				else if(m==1){ //ѡ��˫��ģʽ
					try {
						doubleGame();
					}catch (Exception e1){
						e1.printStackTrace();
					}
				}
				else if(m==2){ //�������в鿴
					try {
						Ranking();
					}catch (Exception e1){
						e1.printStackTrace();
					}
                }




	}
	private static void singleGame() throws Exception{  //���쵥����Ϸ�������
		GameFrame player = new GameFrame();
		player.setVisible(true);
	}
	private static void doubleGame() throws Exception{ //����˫����Ϸ�������
		DoubleGameFrame player1 = new DoubleGameFrame("192.168.17.185",5000,4000);
		player1.setVisible(true);
	}

	private static void Ranking()throws Exception{  //�����������
		RankingView rank = new RankingView();

	}
}
