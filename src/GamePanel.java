import com.sun.org.apache.xml.internal.security.Init;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GamePanel extends JPanel implements KeyListener, ActionListener {
    int length;
    int xloc[]=new int [35];
    int yloc[]=new int [30];
    int foodx;
    int foody;
    int score;
    int difficulty=2;
    boolean end=false;//这里end表示游戏结束的话，没有end是不是就表示游戏正在进行呢？由于我们需要游戏有暂停功能，所以还要再加一个标志位
    boolean running=false;
    Random random=new Random();
    String director="R";
    Timer timer=new Timer(500,this);//定时器



    //静态初始化需完成步骤：
        // 运动部分：设置蛇头和蛇尾的出生点、设置蛇头朝向、设置食物初始位置，设置游戏积分
        //背景部分：清屏,绘制游戏区域及游戏外区域的参数，如设置面板颜色、图片、积分板块等，根据个人需求而定
    //运行时需完成步骤：
        //每隔一段时间接收键盘信号，通过信号决定移动方向（如果游戏结束则结束监听）
    //下面是运动部分数据初始化

    public void init(){
        length=3;
        for(int i=0;i<=2;i++){
            xloc[i]=200-i*25;
            yloc[i]=200;
        }
        director="R";
        foodx=50+25*random.nextInt(32);//这里必须保证产出的food是25的倍数，而且不能超出边界
        foody=100+25*random.nextInt(24);
        score=0;
//        end=false;
    }
    //下面是背景部分初始化
    protected void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        this.setBackground(Color.white);//背景色
        ImageDataStorage.header.paintIcon(this,graphics,25,10);
        graphics.fillRect(25,75,850,675);//填充图形对象(四边形)
        graphics.setColor(Color.RED);
        graphics.setFont(new Font("宋体",Font.BOLD,20));
        graphics.drawString("长度:"+length,680,30);
        graphics.drawString("分数:"+score,680,50);
        graphics.drawString("难度:"+difficulty,80,30);
        //操作模块
            //绘制蛇头
        switch (director){
            case "R"://参数  1：绘制到哪个区域    2：用什么绘制     3/4：坐标
                ImageDataStorage.right.paintIcon(this,graphics,xloc[0],yloc[0]);
            case "L":
                ImageDataStorage.left.paintIcon(this,graphics,xloc[0],yloc[0]);
            case "U":
                ImageDataStorage.up.paintIcon(this,graphics,xloc[0],yloc[0]);
            case "D":
                ImageDataStorage.down.paintIcon(this,graphics,xloc[0],yloc[0]);
        }
            //绘制蛇身
        for (int i=1;i<=length-1;i++){
            ImageDataStorage.body.paintIcon(this,graphics,xloc[i],yloc[i]);
        }
        ImageDataStorage.food.paintIcon(this,graphics,foodx,foody);


        //开始提示
        if(!running){
            graphics.setColor(Color.white);
            graphics.setFont(new Font("宋体",Font.BOLD,40));
            graphics.drawString("按回车（enter）开始游戏",245,400);
//            graphics.drawString();
//            graphics.drawString("每个食物得10分",265,400);
            graphics.drawString("按1,2,3,4可修改难度",245,450);
        }
        //结束提示
        if(end){
            graphics.setColor(Color.RED);
            graphics.setFont(new Font("宋体",Font.BOLD,40));
            graphics.drawString("游戏失败，按回车（enter）重新开始",100,400);
        }
    }
    //下面是构造方法
    // 需完成事件:
    // 初始化界面、打开键盘接收器以接收键盘信息（listener）、setfocusable                                                        （不知道干什么的,和visible有什么区别？查阅资料得知focusable表示可被选中）
    public GamePanel(){
        init();
        this.setFocusable(true);
        this.addKeyListener(this);//在这里写完之后马上去配置那个impl的方法,写完那个之后再继续写这个方法
        timer.start();
    }
    //一旦发生一次能被监测到的事件（包括按键等操作，统称action），就会调用一次这个方法,用到了前面的timer对象
    @Override
    public void actionPerformed(ActionEvent e) {
        if(running&&(!end)){
            //将后面的蛇身移到前面
            for(int i=length-1;i>0;i--){
                xloc[i]=xloc[i-1];
                yloc[i]=yloc[i-1];
            }
            //移动判断
            switch (director) {
                case "R":
                    xloc[0] = xloc[0] + 25;
                    if (xloc[0] > 850) {//出界判定
                        end = true;
                    }
                    break;
                case "L":
                    xloc[0] = xloc[0] - 25;
                    if (xloc[0] < 25) {
                        end = true;
                    }
                    break;
                case "U":
                    yloc[0] = yloc[0] - 25;
                    if (yloc[0] > 725) {
                        end = true;
                    }
                    break;
                case "D":
                    yloc[0] = yloc[0] + 25;
                    if (yloc[0] < 75) {
                        end = true;
                    }
                    break;
            }
            //吃饭成功判断
            if(xloc[0]==foodx&&yloc[0]==foody){
                length++;
                score+=10*difficulty*difficulty;
                foodx=50+25*random.nextInt(32);
                foody=100+25*random.nextInt(24);
            }
            //exception:追尾
            for (int i = 1; i < length; i++) {
                if (xloc[0] == xloc[i] && yloc[0] == yloc[i]) {
                    end = true;
                    break;
                }
            }
            repaint(25,0,850,745);
            timer.start();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode=e.getKeyCode();
        if(!running){
            if (keyCode==KeyEvent.VK_1){
                difficulty=1;
                timer.setDelay(1000);
                repaint();
            }
            if (keyCode==KeyEvent.VK_2){
            timer.setDelay(500);
                difficulty=2;
                repaint();
            }
            if (keyCode==KeyEvent.VK_3){
            timer.setDelay(100);
                difficulty=3;
                repaint();
            }
            if (keyCode==KeyEvent.VK_4){
                timer.setDelay(50);
                difficulty=4;
                repaint();
            }
        }
        if(keyCode==KeyEvent.VK_ENTER){
            if(end){
                end=false;
                init();
            }
            if(!end){//如果游戏未结束，按enter则将running位取反,以达到暂停/继续游戏的结果
                running=!running;
            }
            repaint();//这个repaint是继承过来的方法,负责刷新页面,没键盘按下一次都需要repaint一次
        }
        if(keyCode==KeyEvent.VK_RIGHT){
            if (!director.equals("L")){
                director="R";
            }
        }
        else if(keyCode==KeyEvent.VK_LEFT){
            if(!director.equals("R")){
                director="L";
            }
        }
        else if(keyCode==KeyEvent.VK_UP){
            if (!director.equals("D")){
                director="U";
            }
        }
        else if (keyCode==KeyEvent.VK_DOWN){
            if (!director.equals("U")){
                director="D";
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
