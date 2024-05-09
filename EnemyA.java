//EnemyA
import java.awt.*;
class EnemyA extends MovingObject {
    Image enemyA_img = this.getToolkit().getImage("img/EnemyA.jpg");

    //コンストラクタ
    EnemyA(int apWidth, int apHeight) {
        super(apWidth, apWidth);
        w=12;  //敵の半径は12
        h=12;
        hp=0;  //初期状態は死亡
        point=100;
        heal=0;
    }
    void move(Graphics buf,int apWidth,int apHeight){
        if(hp>0){  //敵が生きていれば
            buf.drawImage(enemyA_img , x-w , y-h , 2*w , 2*h , this);
            x=x+dx;  //座標の更新
            y=y+dy;
            if(y>apHeight)
                dy=dy*(-1);
            else if(y<-h)
                dy=dy*(-1);
            else if(x>apWidth)
                dx=dx*(-1);
            else if(x<0)
                dx=dx*(-1);
        }
    }

/*            if(y>apHeight+h && y<-h)  //縦方向に関して画面外に出たら死亡
                hp=0;
            else if(x>apWidth+w && x<-w)  //横方向に関して画面外に出たら
                hp=0;  //今は敵がゆっくり落ちているだけだからいらない  */
    
    void revive(int apWidth, int apHeight){  //新たな敵を生成
        x =(int)(Math.random()*(apWidth-2*w)+w);  //敵の初期x座標はランダム
        y = -h;  //初期y座標は上の方
        dy = 1;  //鉛直方向の初速度は1(ただゆっくり降りてくるだけ)
        if(x<apWidth/2)  //水平方向の初速度は、
            dx=(int)(Math.random()*2);  //最初真ん中より左にいるなら右向きにランダム
        else
            dx=-(int)(Math.random()*2);  //最初真ん中より右にいるなら左向きにランダム
        hp=1;
        explosion = 1;
    }
}