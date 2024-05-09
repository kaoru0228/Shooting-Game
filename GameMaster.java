// GameMaster
import java.awt.*;
import java.awt.event.*;

public class GameMaster extends Canvas implements KeyListener {
    // ■ フィールド変数
    Image buf; // 仮の画面としての buffer に使うオブジェクト(Image クラス)
    Graphics buf_gc;// buffer のグラフィックスコンテキスト (gc)用ブジェクト」
    Dimension d; // アプレットの大きさを管理するオブジェクト
    private int imgW, imgH; // キャンバスの大きさ

    private int enmyAnum = 10; // 同時に存在する敵Aの数
    private int enmyBnum = 10; // 同時に存在する敵Bの数
    private int ftrBltNum = 10; // 自機の弾の数
    private int enmyBltNum = 10; // 敵B 1隊が所有するの弾の数(EnmyBの数分用意)
    private int bossBltNum = 100;  //ボスAの弾数は100
    private int mode = -1; // -1: タイトル画面, -2: ゲームオーバー,1~ ゲームステージ
    private int i, j;
    Fighter ftr; // 自機
    FighterBullet ftrBlt[] = new FighterBullet[ftrBltNum]; // 自機の弾
    EnemyA enmyA[] = new EnemyA[enmyAnum];  // 敵A
    EnemyB enmyB[] = new EnemyB[enmyBnum];  // 敵B
    EnemyBullet enmyBlt[] = new EnemyBullet[enmyBnum*enmyBltNum];  //敵Bの発射する弾
    BossA bossA;  //ボスA
    int bossA_count = 0;  //ボスを1回のみにするためのカウント
    BossBullet bossBlt[] = new BossBullet[bossBltNum];  //ボスAの弾
    double rad = 0;
    int bblt_num = 0;
    Heart heart;
    Penetration penetration;

    Image background = this.getToolkit().getImage("img/space.jpg");

    // ■ コンストラクタ
    /* ゲームの初期設定 * ・描画領域(Image)とGC(Graphics)の作成 * ・敵,自機,弾オブジェクトの作成 */

    GameMaster(int imgW, int imgH) { // コンストラクタ (アブレット本体が引数. ゲームの初期化を行う)
        this.imgW = imgW; // 引数として取得した描画領域のサイズをローカルなプライベート変数に代入
        this.imgH = imgH; // 引数として取得した描画領域のサイズをローカルなプライベート変数に代入
        setSize(imgW, imgH); // 描画領域のサイズを設定
        addKeyListener(this);
        ftr = new Fighter(imgW, imgH); // 自機のオブジェクトを実際に作成
        bossA = new BossA(imgW, imgH);
        for (i = 0; i < ftrBltNum; i++) // 自機が発射する弾のオブジェクトを実際に作成
            ftrBlt[i] = new FighterBullet();
        for (i = 0; i < enmyAnum; i++) // 敵Aのオブジェクトを実際に作成
            enmyA[i] = new EnemyA(imgW, imgH);
        for (i = 0; i < enmyBnum; i++) // 敵Bのオブジェクトを実際に作成
            enmyB[i] = new EnemyB(imgW, imgH);
        for (i = 0; i < enmyBltNum*enmyBnum; i++) // 敵Bが発射する弾のオブジェクトを実際に作成
            enmyBlt[i] = new EnemyBullet();
        for (i = 0; i < bossBltNum; i++) // 敵Bが発射する弾のオブジェクトを実際に作成
            bossBlt[i] = new BossBullet();
        heart = new Heart(imgW, imgH);
        penetration = new Penetration(imgW, imgH);
    }

    // ■ メソッド
    // コンストラクタ内で createImage を行うと peer の関連で
    // nullpointer exception が返ってくる問題を回避するために必要
    public void addNotify() {
        super.addNotify();
        buf = createImage(imgW, imgH); // buf を画面と同サイズで作成
        buf_gc = buf.getGraphics();
    }

    // ■ メソッド (Canvas)
    public void paint(Graphics g) {
        buf_gc.setColor(Color.white); // gcの色を白に
        buf_gc.fillRect(0, 0, imgW, imgH); // gc を使って白の四角を描く(背景の初期化)
//        buf_gc.drawImage(background, 0, 0, imgW,  imgH, this);
        
/*        if (backH+150 < 480){
            buf_gc.drawImage(Background, 0, 0, imgW,  imgH, backW, backH, backW+200, backH+150, this);
            backH++;
            //System.out.println("paint");
        }else{
            backH = 0;
        }*/

        switch (mode) {
        case -2: // ゲームオーバー画面(スペースキーを押されたらタイトル画面
            buf_gc.setColor(Color.black); // ゲームオーバー画面を描く
            buf_gc.drawString(" == Game over == ", imgW / 2 - 80, imgH / 2 - 20);
            buf_gc.drawString(" Your Score :" + String.valueOf(MovingObject.score), imgW / 2 - 80, imgH / 2 + 20);
            buf_gc.drawString(" Hit SPACE key * ", imgW / 2 - 80, imgH / 2 + 40);
            
            this.initialize();  //敵をリセット
            heart.hp = 0;  //回復アイテムもリセット
            penetration.hp = 0;
            ftr.x = (int)(imgW/2);  //自機のスタート位置をリセット
            ftr.y = (int)(imgH*0.9);
            break;
        case -1: // タイトル画面(スペースキーを押されたらゲーム開始)
            buf_gc.setColor(Color.black); // タイトル画面を描く
            MovingObject.score = 0;  //スコアをリセット
            buf_gc.drawString(" == Shooting Game Title == ", imgW / 2 - 80, imgH / 2 - 20);
            buf_gc.drawString("Hit SPACE bar to start game", imgW / 2 - 80, imgH / 2 + 20);
            break;
        default: // ゲーム中
            buf_gc.setColor(Color.black);

            // *** ランダムにオブジェクトを生成 ***
        
            if(heart.hp < 1 && Math.random()<0.001)  //0.1%の確率で回復アイテムを生成
                heart.revive(imgW, imgH);

            if(penetration.hp < 1 && Math.random()<0.1)  //0.1%の確率で回復アイテムを生成
                penetration.revive(imgW, imgH);

            makeEnmyA: if (bossA.hp < 1 && Math.random()<0.1) { // 10%の確率で一匹生成
                for (i = 0; i < enmyAnum; i++) {
                    if (enmyA[i].hp == 0) {  //敵が死んでいたら
                        enmyA[i].revive(imgW, imgH);  //敵の生成
                        break makeEnmyA;
                    }
                }
            }
            makeEnmyB: if (bossA.hp < 1 && Math.random() < 0.1) { // 10%の確率で一匹生成
                for (i = 0; i < enmyBnum; i++) {
                    if (enmyB[i].hp == 0) {  //敵が死んでいたら
                        enmyB[i].revive(imgW, imgH);  //敵の生成
                        break makeEnmyB;
                    }
                }
            }
            if(bossA_count == 0 && MovingObject.score > 1500){  //scoreが10,000を超えたら、ボス出現
                this.initialize();
                bossA.revive(imgW, imgH);
                bossA_count = 1;
            }

            // ** 自分の弾を発射 **
            if (ftr.sflag == true && ftr.delaytime == 0) { // もしスペースキーが押されていて&待ち時間がゼロ
                for (i = 0; i < ftrBltNum; i++) {// 全部の弾に関して前から探査して
                    if (ftrBlt[i].hp == 0) {// 非アクティブの (死んでいる)弾があれば
                        ftrBlt[i].revive(ftr.x, ftr.y); // 自機から弾を発射して、
                        ftr.delaytime = 5;// 自機の弾発射待ち時間を元に戻す
                        break;
                    }
                }  // for loop を抜ける
            }
            else if (ftr.delaytime > 0)
                ftr.delaytime--;// 弾を発射しない(出来ない)場合は //待ち時間を1減らす

            // ** EnemyBの弾を発射 **
            for(i=0;i<enmyBnum;i++){
                if (enmyB[i].hp !=0 && enmyB[i].delaytimeB == 0) { //もし待ち時間がゼロなら
                    for (j = 1; j <= enmyBltNum; j++) {// 全部の弾に関して前から探査して
                        if (enmyBlt[i*enmyBltNum+j].hp == 0) {// 非アクティブの (死んでいる)弾があれば   //i*enmyBltNum+jは、EnemyB[i]のj番目の弾を指す。
                            int enmyblt_dx = enmyBlt[i*enmyBltNum+j].enmyblt_x_velocity(ftr.x ,ftr.y,enmyB[i].x,enmyB[i].y);  //弾の水平速度を取得
                            int enmyblt_dy = enmyBlt[i*enmyBltNum+j].enmyblt_y_velocity(ftr.x ,ftr.y,enmyB[i].x,enmyB[i].y);  //弾の鉛直速度を取得
                            System.out.println("dx;"+enmyblt_dx + ",dy;" + enmyblt_dy);  //弾の速度を描写
                            System.out.println("enemyB_x;"+enmyB[i].x + ",enemyB_y;" + enmyB[i].y);
                            enmyBlt[i*enmyBltNum+j].revive(enmyB[i].x ,enmyB[i].y ,enmyblt_dx ,enmyblt_dy); // 弾を発射して、
                            enmyB[i].delaytimeB = 100;// 弾発射待ち時間を元に戻す
                            break;
                        }
                    }  // for loop を抜ける
                }
                else if (enmyB[i].delaytimeB > 0)
                    enmyB[i].delaytimeB--;// 弾を発射しない(出来ない)場合は //待ち時間を1減らす
            }

            // ** BossAの弾を発射 **
            if(bossA.hp > bossA.hp_max*2/3){
                for(i=0;i<bossBltNum;i++){
                    if(bossBlt[i].hp == 0 && bossA.delaytime == 0){
                        int bossBlt_dx = bossBlt[i].tracking_dx(ftr.x,ftr.y,bossA.x,bossA.y);
                        int bossBlt_dy = bossBlt[i].tracking_dy(ftr.x,ftr.y,bossA.x,bossA.y);
                        bossBlt[i].revive_tracking(bossA.x,bossA.y,bossBlt_dx,bossBlt_dy);
                        bossA.delaytime = 100;
                        break;
                    }
                    else if(bossA.delaytime>0)
                        bossA.delaytime--;
                }
            }

            if(bossA.hp_max/3 < bossA.hp && bossA.hp <= bossA.hp_max*2/3){
                bossA.x = (int)(imgW/2);
                bossA.y = (int)(imgH/3);
                bossBlt[bblt_num].revive_every_direction(bossA.x,bossA.y,rad);
                rad += Math.PI/8;
                bblt_num += 1;
                if(rad >= 2*Math.PI)
                    rad = 0;
                if(bblt_num == bossBltNum)
                bblt_num = 0;
                System.out.println("弾番号"+bblt_num+"偏角"+rad/Math.PI);
            }

            // ** 自機の生死を判断**
            if (ftr.hp < 1)
                mode = -2;
            // ** オブジェクトの描画＆移動**
            for (i=0; i < ftrBltNum; i++){
                for (j=0 ; j < enmyAnum ; j++)
                    ftrBlt[i].collisionCheck(enmyA[j]);  //自機弾と敵Aの衝突判定
                for (j=0 ; j < enmyBnum ; j++)
                    ftrBlt[i].collisionCheck(enmyB[j]);  //自機弾と敵Bの衝突判定
                for(j=0;j<enmyBnum*enmyBltNum;j++)
                    ftrBlt[i].collisionCheck(enmyBlt[j]);  //自機弾と敵Bの弾の衝突判定
                for(j=0;j<bossBltNum;j++)
                    ftrBlt[i].collisionCheck(bossBlt[j]);  //自機弾と敵Bの弾の衝突判定 
                ftrBlt[i].collisionCheck(bossA);  //自機弾とボスの衝突判定
                ftrBlt[i].move(buf_gc, imgW, imgH, ftr.ftr_blt_mode);  //自機弾のmove
            }
            for (i=0; i < enmyAnum; i++){
                enmyA[i].collisionCheck(ftr);  //敵Aと自機の衝突判定
                enmyA[i].move(buf_gc, imgW, imgH);  //敵Aのmove
                enmyA[i].explosionnn(buf_gc, imgW, imgH);  //敵Aが死んだら爆発
            }
            for (i=0; i < enmyBnum; i++){
                enmyB[i].collisionCheck(ftr);  //敵Bと自機の衝突判定
                enmyB[i].move(buf_gc, imgW, imgH);  //敵Bのmove
                enmyB[i].explosionnn(buf_gc, imgW, imgH);  //敵Bが死んだら爆発
            }
            for(i=0;i<enmyBnum*enmyBltNum;i++){
                enmyBlt[i].collisionCheck(ftr);  //敵Bの弾と自機の衝突判定
                enmyBlt[i].move(buf_gc, imgW, imgH);  //敵Bの弾のmove
            }
            for(i=0;i<bossBltNum;i++){
                bossBlt[i].collisionCheck(ftr);  //ボスAと自機の衝突判定
                bossBlt[i].move(buf_gc, imgW, imgH);  //ボスAの弾のmove
            }
            ftr.collisionCheck(bossA);  //自機とボスの衝突判定
            heart.collisionCheck(ftr);  //回復アイテムをとったら回復
            heart.move(buf_gc, imgW, imgH);  //回復アイテムのmove
            bossA.move(buf_gc, imgW, imgH);  //ボスのmove
            bossA.explosionnn(buf_gc, imgW, imgH);  //ボスAが死んだら爆発
            penetration.collisionCheck(ftr);  //貫通団に切り替わるアイテムとの衝突判定
            penetration.move(buf_gc, imgW, imgH);  //貫通団に切り替わるためのアイテムのmove
            ftr.move(buf_gc, imgW, imgH);  //自機のmove
            buf_gc.drawString("Life :" + String.valueOf(ftr.hp), ftr.x, ftr.y+20);
            buf_gc.drawString("Score : " + String.valueOf(MovingObject.score), 5, 10);
            if(bossA.hp>0)
                buf_gc.drawString("BossA  HP ", 5, 25);
            if(ftr.ftr_blt_mode==1 && ftrBlt[0].ftr_blt_mode==0)
                ftr.ftr_blt_mode=0;

            // 状態チェック
/*            for (i = 0; i < enmyAnum; i++) {
                System.out.print(enmyA[i].hp + " ");  //敵Aの生存状態を描写
            }
            for (i = 0; i < enmyBnum; i++) {
                System.out.print(enmyB[i].hp + " ");  //敵Bの生存状態を描写
            }
            System.out.println("");*/
            MovingObject.score += 1;  //1ループごとにスコアが増える（生存時間によるスコア）
        }
        g.drawImage(buf, 0, 0, this);
        }

    // メソッド
    public void keyTyped(KeyEvent ke) {
    }// 今回は使わないが実際には必要

    public void keyPressed(KeyEvent ke) {  //キーボードを押している間True
        int cd = ke.getKeyCode();
        switch (cd) {
        case KeyEvent.VK_LEFT:
            ftr.lflag = true;
            break;
        case KeyEvent.VK_RIGHT:
            ftr.rflag = true;
            break;
        case KeyEvent.VK_UP:
            ftr.uflag = true;
            break;
        case KeyEvent.VK_DOWN:
            ftr.dflag = true;
            break;
        case KeyEvent.VK_SPACE:
            ftr.sflag = true;
            if (this.mode != 1) {
                this.mode++;
                ftr.hp = 5;  //自機のhpを設定
            }
            break;
        }
    }

    public void keyReleased(KeyEvent ke) {  //キーボードを離すとFalse
        int cd = ke.getKeyCode();
        switch (cd) {
        case KeyEvent.VK_LEFT:
            ftr.lflag = false;
            break;
        case KeyEvent.VK_RIGHT:
            ftr.rflag = false;
            break;
        case KeyEvent.VK_UP:
            ftr.uflag = false;
            break;
        case KeyEvent.VK_DOWN:
            ftr.dflag = false;
            break;
        case KeyEvent.VK_SPACE:
            ftr.sflag = false;
            break;
        }
    }

    public void initialize(){  //自機以外のオブジェクトのhpをすべて0にするメソッド
        for(i=0;i<ftrBltNum;i++)
            ftrBlt[i].hp = 0;
        for(i=0;i<enmyAnum;i++)  //敵Aをリセット
            enmyA[i].hp=0;
        for(i=0;i<enmyBnum;i++) //敵Bをリセット
            enmyB[i].hp=0;
        for(i=0;i<enmyBnum*enmyBltNum;i++)  //敵Bの弾をリセット
            enmyBlt[i].hp = 0;
        bossA.hp = 0;  //ボスをリセット
        for(i=0;i<bossBltNum;i++)  //敵Bの弾をリセット
            bossBlt[i].hp = 0;
        bossA_count = 0;
    }
}