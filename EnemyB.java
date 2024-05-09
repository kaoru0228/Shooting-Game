//EnemyB
import java.awt.*;
class EnemyB extends MovingObject {
    int delaytimeB = 100;  //弾の発射待ち時間(毎回1ずつ減り、0になったら発射可能)
    int r;
    //コンストラクタ
    EnemyB(int apWidth, int apHeight) {
        super(apWidth, apWidth);
        w=15;  //敵の半径は15
        h=15;
        hp=0;  //初期状態は死亡
        r=0;
        point=100;
        heal=0;
    }
    void move(Graphics buf,int apWidth,int apHeight){
        buf.setColor(Color.red);
        if(hp>0){  //敵が生きていれば
            if(r == 7)
                r = 1;
            else
                r ++;
            
            for(int i=1;i<r;i++){
                buf.drawOval(x-(1+2*i), y-(1+2*i), 2*(1+2*i), 2*(1+2*i));  //gcを使って四角形の敵を描く
            }

            x=x+dx;  //座標の更新
            y=y+dy;
            if(y>apHeight)
                dy=dy*(-1);
            else if(y<-w)
                dy=dy*(-1);
            else if(x>apWidth)
                dx=dx*(-1);
            else if(x<0)
                dx=dx*(-1);
        }
    }
    void revive(int apWidth, int apHeight){  //新たな敵を生成
        x=(int)(Math.random()*(apWidth-2*w)+w);  //敵の初期x座標はランダム
        y=-h;  //初期y座標は上の方
        dy=1;  //鉛直方向の初速度は1(ただゆっくり降りてくるだけ)
        if(x<apWidth/2)  //水平方向の初速度は、
            dx=(int)(Math.random()*2);  //最初真ん中より左にいるなら右向きにランダム
        else
            dx=-(int)(Math.random()*2);  //最初真ん中より右にいるなら左向きにランダム
        hp=1;
        explosion=1;
    }
}