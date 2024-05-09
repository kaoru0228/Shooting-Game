//Heart
import java.awt.*;
class Heart extends MovingObject {
    Image heart_img = this.getToolkit().getImage("img/heart.jpg");
    //コンストラクタ
    Heart(int apWidth, int apHeight) {
        super(apWidth, apWidth);
        w=12;  //敵の半径は12
        h=12;
        hp=0;  //初期状態は死亡
        point=100;
        heal=10;
    }
    void move(Graphics buf,int apWidth,int apHeight){
        if(hp>0){  //自分が生きていれば
            buf.drawImage(heart_img , x-w , y-h , 2*w , 2*h , this);  //ハートの画像を張り付ける
            x=x+dx;  //座標の更新
            y=y+dy;
            if(y>apHeight+h && y<-h)  //縦方向に関して画面外に出たら死亡
                hp=0;
        }
    }
    
    void revive(int apWidth, int apHeight){
        x =(int)(Math.random()*(apWidth-2*w)+w);  //初期x座標はランダム
        y = -h;  //初期y座標は上の方
        dy = 1;  //鉛直方向の初速度は1(ただゆっくり降りてくるだけ)
        if(x<apWidth/2)
            dx=(int)(Math.random()*2);
        else
            dx=-(int)(Math.random()*2);
        hp=1;
    }
}