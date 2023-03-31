import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Main {
    public static void main(String[] args) {
        //前面四行是所有swing的基础
        JFrame window=new JFrame("贪吃蛇");
        window.setSize(900,800);
        window.setVisible(true);//窗口可见
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//当检测到关闭窗口时,同时关闭程序
        window.add(new GamePanel());//窗体中增加面板
        window.setLocationRelativeTo(null);//居中
        window.setResizable(false);
    }
}
