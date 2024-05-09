//MovingObject
//ゲームに登場するすべての移動オブジェクトに共通する親クラス
import java.awt.Graphics;
import java.awt.*;

abstract class MovingObject extends Canvas  {  //abstractは抽象クラスであることを指す
 double db_x;  //(x,y)は中心座標
 double db_y;
 double db_dx;  //速度
 double db_dy;
 int x;
 int y;
 int dx;
 int dy;
 int w;  //自分のオブジェクトの横、縦幅の半分(自分の半径)
 int h;
 int hp;  //hpが0になると死亡
 static int score;
 int point;
 int heal;
 int explosion,exp_count;
 int ftr_blt_mode;

 Image explosion1_img = this.getToolkit().getImage("img/explosion1.jpg");
 Image explosion2_img = this.getToolkit().getImage("img/explosion2.jpg");
 Image explosion3_img = this.getToolkit().getImage("img/explosion3.jpg");
 Image explosion4_img = this.getToolkit().getImage("img/explosion4.jpg");
 Image explosion5_img = this.getToolkit().getImage("img/explosion5.jpg");
 Image explosion6_img = this.getToolkit().getImage("img/explosion6.jpg");
 Image explosion7_img = this.getToolkit().getImage("img/explosion7.jpg");


 //コンストラクタ1　引数がないため初期設定なし(フィールド変数の初期値は0)
 MovingObject () {}

 //コンストラクタ2　描画領域の大きさを引数に、出現の初期位置をランダムに
 MovingObject (int width, int height){
  db_x = Math.random()*width;
  db_y = Math.random()*height;
  db_dx = Math.random()*6-3;
  db_dy = Math.random()*6-3;
  x = (int)(db_x);
  y = (int)(db_y);
  dx = (int)(db_dx);
  dy = (int)(db_dy);
  w =2;
  h = 2;
  hp = 10;
  score = 0;
  explosion=exp_count = 0;
  ftr_blt_mode = 0;
 }
 
 abstract void move(Graphics buf, int abWidth, int abHeight);
 //オブジェクトを動かし、一を更新するメソッド
 //MovingObjectを継承するすべてのクラスで実装することを強制
 //moveでは、まず描いてから、vx,vyだけ移動する
 //moveを呼び出す前に衝突判定をする必要あり(moveは衝突していない際の移動)

 abstract void revive(int w,int h);
 //オブジェクトを生き返らせる中小メソッド(これも実装必須)

 boolean collisionCheck(MovingObject obj) {  //衝突判定のメソッド(引数は相手のオブジェクト)
  if(Math.abs(this.x - obj.x) <= (this.w + obj.w) && Math.abs(this.y-obj.y)<=(this.h + obj.h)) {//相手との中心間の距離が自分達のオブジェクトの半径の和より小さいなら衝突
    if(this.ftr_blt_mode == 1 && this.hp>0){
      this.hp--;
      obj.ftr_blt_mode = 1;
    }
    else if(this.hp>0 && this.heal==0){
      this.hp--;  //自分の
      obj.hp--;    //敵のＨＰを１減らす
      score += obj.point;  //敵を１体倒すごとに100ポイント　スコアが増える
    }
    else if(this.hp>0 && this.heal>0){
        obj.hp += this.heal;
        this.hp--;
    }
    return true;
  }
  else
  return false;
 }

 void explosionnn(Graphics buf, int abWidth, int abHeight){
  if(hp==0 && explosion==1 && exp_count<5){  //７枚の爆破写真を切り替えながら張っていく（最期にexplosionを0に戻す）
    buf.drawImage(explosion1_img , x-w, y-h, 2*w, 2*h, this);
    exp_count++;
  }
  else if(hp==0 && explosion==1 && exp_count<10){
    buf.drawImage(explosion2_img , x-w, y-h, 2*w, 2*h, this);
    exp_count++;
  }
  else if(hp==0 && explosion==1 && exp_count<15){
    buf.drawImage(explosion3_img , x-w, y-h, 2*w, 2*h, this);
    exp_count++;
  }
  else if(hp==0 && explosion==1 && exp_count<20){
    buf.drawImage(explosion4_img , x-w, y-h, 2*w, 2*h, this);
    exp_count++;
  }
  else if(hp==0 && explosion==1 && exp_count<25){
    buf.drawImage(explosion5_img , x-w, y-h, 2*w, 2*h, this);
    exp_count++;
  }
  else if(hp==0 && explosion==1 && exp_count<30){
    buf.drawImage(explosion6_img , x-w, y-h, 2*w, 2*h, this);
    exp_count++;
  }
  else if(hp==0 && explosion==1 && exp_count<35){
    buf.drawImage(explosion7_img , x-w, y-h, 2*w, 2*h, this);
    exp_count++;
    if(exp_count==35){
        explosion=0;
        exp_count=0;
    }
  }
 }
}