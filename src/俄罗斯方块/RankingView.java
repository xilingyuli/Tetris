package 俄罗斯方块;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.util.Collections;

public class RankingView {
    public void Op() throws Exception{

        // 创建内容面板，使用边界布局
        JPanel panel = new JPanel(new BorderLayout());

        //数据库加载驱动和连接
        Connection connection = GameFrame.getCon();
        Statement stmt = null;

        //执行查询
        stmt = connection.createStatement();
        //写sql语句 ?占位符
        String sql = "select id,name,score from dongdong";
        ResultSet rs = stmt.executeQuery(sql);

        // 表头（列名）
        Object[] columnNames = {"ID","Score"};

        // 表格所有行数据
        Object[][] rowData = new Object[10][2];

        int i = 0;
        //展开结果集数据库
        while (rs.next()){
            //通过字段检索
            String name = rs.getString("name");
            int score = rs.getInt("score");
            rowData[i][0] = name;
            rowData[i][1] = score;
            i++;
        }

        JFrame jf = new JFrame("测试窗口");
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.addWindowListener(new WindowAdapter() {
                                 public void windowClosing(WindowEvent e) {
                                     jf.setVisible(false);
//                                     JOptionPane.showMessageDialog(null,"666");
                                     String[] a = null;
                                     try {
                                         Main.main(a);
                                     }catch (Exception e1){
                                         e1.printStackTrace();
                                     }
                                 }
                             }
        );

        JTable table = new JTable(rowData, columnNames);
        // 把 表头 添加到容器顶部（使用普通的中间容器添加表格时，表头 和 内容 需要分开添加）
        panel.add(table.getTableHeader(), BorderLayout.NORTH);
        // 把 表格内容 添加到容器中心
        panel.add(table, BorderLayout.CENTER);

        table.setFont(new Font("宋体",Font.CENTER_BASELINE,20));
        jf.setContentPane(panel);
        jf.pack();
        jf.setLocationRelativeTo(null);
        table.setSize(1024,768);
        panel.setSize(1025,768);
        jf.setSize(1024,768);
        JButton jb = new JButton("back");
        jf.setVisible(true);
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
}