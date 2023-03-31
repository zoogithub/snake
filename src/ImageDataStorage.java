import javax.swing.*;
import java.net.URL;

public class ImageDataStorage {
    private static URL url_header=ImageDataStorage.class.getResource("/resource/header.jpg");
    //下面是静态url
    private static URL upurl=ImageDataStorage.class.getResource("/resource/up.jpg");
    private static URL downurl=ImageDataStorage.class.getResource("/resource/down.jpg");
    private static URL lefturl=ImageDataStorage.class.getResource("/resource/left.jpg");
    private static URL righturl=ImageDataStorage.class.getResource("/resource/right.jpg");
    private static URL bodyurl=ImageDataStorage.class.getResource("/resource/body.jpg");
    private static URL foodurl=ImageDataStorage.class.getResource("/resource/food.jpg");
    //下面为蛇头方向及蛇身图片对象
    public static ImageIcon up=new ImageIcon(upurl);//这个imageicon和imagedata有什么区别？好像没学
    public static ImageIcon down=new ImageIcon(downurl);
    public static ImageIcon left=new ImageIcon(lefturl);
    public static ImageIcon right=new ImageIcon(righturl);
    public static ImageIcon body=new ImageIcon(bodyurl);
    public static ImageIcon food=new ImageIcon(foodurl);
    public static ImageIcon header=new ImageIcon(url_header);
}
