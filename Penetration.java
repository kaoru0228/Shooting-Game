//Penetration
import java.awt.*;
class Penetration extends MovingObject {
    Image penetration_img = this.getToolkit().getImage("img/penetration.jpg");
    //コンストラクタ
    Penetration(int apWidth, int apHeight) {
        super(apWidth, apWidth);
        w=12;  //敵の半径は12
        h=12;
        hp=0;  //初期状態は死亡
        point=1000;
        ftr_blt_mode=1;
    }
    void move(Graphics buf,int apWidth,int apHeight){
        if(hp>0){  //自分が生きていれば
            buf.drawImage(penetration_img , x-w , y-h , 2*w , 2*h , this);  //一定時間貫通弾になるアイテム画像を張り付ける
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