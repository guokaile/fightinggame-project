package com.itheima.ui;

import com.itheima.domain.EnemyCharacter;
import com.itheima.domain.HeroCharacter;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class FightingGame {
    public void gameStart(String username) {
        //1.显示游戏的标题
        System.out.println("╔════════════════════════════════╗");
        System.out.println("    🎮 " + username +"欢迎来到文字格斗游戏 🎮   ");
        System.out.println("╚════════════════════════════════╝");

        //2.创建玩家角色（名字+属性分配）
        HeroCharacter player = createPlayerCharacter(username);

        //3.展示玩家角色的属性
        System.out.println("角色创建成功~");
        System.out.println("初始属性为："+player.showInfo());
        System.out.println("初始技能为："+player.showSkills());

        //4.创建多个敌人列表
        ArrayList<EnemyCharacter> enemyList = new ArrayList<>();
        enemyList.add(new EnemyCharacter("初级战士",80,15,10,"猛击"));
        enemyList.add(new EnemyCharacter("敏捷刺客",60,20,5,"快速攻击"));
        enemyList.add(new EnemyCharacter("重装坦克",120,10,20,"防御姿态"));
        enemyList.add(new EnemyCharacter("神秘法师",70,25,8,"火球术"));

        //5.准备战斗（依次和多个敌人进行战斗）
        int count = 1;//记录当前是第几个敌人
        int wins = 0;//记录玩家胜利的次数
        while(player.isAlive()){
            //进入循环，开始准备战斗

            //5.1重置敌人的属性，敌人属性每场HP+10,ATK+3,DEF+2
            if(wins != 0){
                //获取到每个敌人的信息，进行属性点的增加
                for (int i = 0; i < enemyList.size(); i++) {
                    EnemyCharacter c = enemyList.get(i);
                    //每场maxHP+10
                    c.maxHP += 10;
                    c.HP = c.maxHP;
                    //每场ATK+3
                    c.attack += 3;
                    //每场DEF+2
                    c.defence += 2;
                    //每场战斗之前，重置敌人的防御状态为false
                    c.defending = false;
                }
            }

            //5.2随机选择敌人（Random）
            Random r = new Random();
            int index = r.nextInt(enemyList.size());
            EnemyCharacter enemy = enemyList.get(index);
            System.out.println(enemy.showInfo());

            //System.exit(0);

            //5.3开始战斗
            System.out.println("============================");
            System.out.println("第"+count+"场战斗开始~ 对手："+enemy.name);

            //和当前的敌人是第几回合
            int round = 1;
            while(player.isAlive()){
                //显示双方的状态（生命值）
                System.out.println("============================");
                System.out.println("第"+round+"回合开始！");
                //打印敌我双方的状态（生命值）
                System.out.println(getHealthBar(player.name,player.HP,player.maxHP));
                System.out.println(getHealthBar(enemy.name,enemy.HP,enemy.maxHP));

                //System.exit(0);

                //5.4玩家回合：选择行动（1.普通攻击 /2.强力一击/3.生命汲取）
                playerTurn(player,enemy);

                //5.5判断敌人是否被击败
                if(!enemy.isAlive()){
                    System.out.println("恭喜您~ 您击败了"+enemy.name+"！");
                    //玩家胜利，连胜的次数+1
                    wins++;
                    //结束循环，进入下一场战斗
                    break;
                }

                //5.6敌人回合：选择行动（50%的几率普通攻击/50%的几率技能攻击/不同的敌人采取不同的技能进行攻击）
                enemyTurn(enemy,player);
                /*可以使用Alt+Enter生成对应的方法*/

                //5.7判断玩家是否被击败
                if(!player.isAlive()){
                    System.out.println("您被"+enemy.name+"击败了···");
                    //结束循环，结束战斗
                    break;
                }
                //如果玩家没有被击败，继续下一个回合
                round++;
            }

            //5.8和一个敌人战斗结束，玩家胜利（继续战斗），玩家失败（游戏停止）
            if(player.isAlive()){
                //计算玩家要恢复多少生命值（20-40）
                int healHP = r.nextInt(21) + 20;
                //回复玩家的血量
                player.heal(healHP);
                //提示
                System.out.println("战斗结束！您回复了"+healHP+"点生命值");
                System.out.println("当前胜场："+wins);
                System.out.println("======================================");
            }

            //5.9每胜利三场，人物的属性就需要增加
            if(player.isAlive() && wins > 0 && wins % 3 == 0){
                System.out.println("恭喜您~ 您获得了属性提升！");
                //提升最大生命
                player.maxHP += 30;
                //提升攻击力
                player.attack += 5;
                //提升防御力
                player.defence += 5;
                //提示
                System.out.println("最大生命值+30，攻击力+5，防御力+5");
                System.out.println("您的属性已提升，当前属性为："+player.showInfo());
                System.out.println("======================================");
            }

            //5.10询问玩家是否继续游戏
            if (player.isAlive()){
                System.out.println("是否继续下一场战斗？（y/n）");
                Scanner sc = new Scanner(System.in);
                String choice = sc.nextLine();
                if("y".equals(choice)){
                    //继续下一场战斗
                    count++;
                    //循环继续执行，开始下一场战斗
                    continue;
                }else if("n".equals(choice)){
                    //玩家选择不继续游戏
                    break;
                }else{
                    System.out.println("没有这个选项，默认游戏继续");
                    count++;
                    continue;
                }
            }



        }

        //6.游戏的最终结算
        System.out.println("═════════════════════════════════");
        System.out.println("游戏结束！您共胜利了"+wins+"场战斗");
        System.out.println("感谢您游玩文字版格斗游戏");
        //停止虚拟机运行
        System.exit(0);
    }




    //定义一个方法打印敌我双方的状态
    private String getHealthBar(String name,int HP,int maxHP) {
        //满血状态下，打印20个方块
        int barLength = 20;

        //根据当前生命值，打印对应的方块
        int filled = (int) ((HP * 1.0/maxHP)*barLength);

        StringBuilder sb = new StringBuilder();
        sb.append(name).append("：[");

        //利用for循环，打印对应的方块
        for (int i = 0; i < barLength; i++) {
            if(i < filled){
                sb.append("█");
            }else{
                sb.append("░");
            }
        }
        sb.append("]").append(HP).append("/").append(maxHP).append("HP");

        return sb.toString();

    }





    //作用：用来创建一个玩家的角色
    //参数：username 玩家的用户名
    //返回值（结果）：

    public HeroCharacter createPlayerCharacter(String username) {

        System.out.println("创建您的角色：");
        System.out.println("您的角色名称为："+username);

        //属性分配
        int points = 20;

        System.out.println("您有"+points+"点属性点，您可以根据您的需求分配属性");
        System.out.println("1.生命值（每点 + 10HP）");
        System.out.println("2.攻击力（每点 + 2 ATK）");
        System.out.println("3.防御力（每点 + 1DEF）");

        Scanner sc = new Scanner(System.in);
        //定义数组，把要提示的属性都放到数组中
        String[] attributes = {"生命值","攻击力","防御力"};
        //定义数组记录三个属性分配的属性点
        int[] values = new int[3];

        for (int i = 0; i < attributes.length; i++) {
            System.out.println("分配点数到" + attributes[i] + "（剩余" + points + "）：");
            //input表示当前用户键盘录入的数据（要分配的属性点）
            int input = sc.nextInt();

            //如果要分配的属性点为负数
            if (input < 0) {
                System.out.println("无效输入！默认分配0点！");
                input = 0;
            }
            //如果分配点数超出剩余点数
            if(input > points){
                System.out.println("属性点不足！剩余属性点全部分配到"+attributes[i]);
                input = points;
            }
            //计算一下剩余还有多少个属性点
            points = points - input;
            //记录分配的属性点
            values[i] = input;
        }

        //创建玩家角色的对象（在循环外，等三个属性都分配完再创建）
        HeroCharacter player = new HeroCharacter(
                username, //玩家的用户名
                100 + values[0] * 10,  //生命值
                10 + values[1] * 2, //攻击力
                values[2] //防御力
        );
        //添加玩家的技能
        player.skillList.add("普通攻击");
        player.skillList.add("强力一击");
        player.skillList.add("生命汲取");

        //返回玩家角色的对象
        return player;
    }

    //5.4玩家回合：选择行动（1.普通攻击 /2.强力一击/3.生命汲取）
    private void playerTurn(HeroCharacter player, EnemyCharacter enemy) {
        System.out.println("=====玩家回合=====");
        System.out.println("1.普通攻击");
        System.out.println("2.强力一击");
        System.out.println("3.生命汲取");
        System.out.println("请选择您的行动：");
        Scanner sc = new Scanner(System.in);
        int choose = sc.nextInt();
        switch (choose) {
            default:
                System.out.println("没有这个技能！默认使用普通攻击！");
            case 1:
                //调用普通攻击的方法
                int damage1 = calculateDamage(player.attack, enemy.defence);
                System.out.println("你对"+enemy.name+"使用了普通攻击，造成了"+damage1+"点伤害！");
                //扣血的操作
                enemy.takeDamage(damage1);
                break;
            case 2:
                if(player.HP > 10){
                    //消耗我方10HP
                    player.takeDamage(10);
                    //计算对对方的伤害
                    //我方的攻击力 * 1.8 - 对方的防御力 = 伤害
                    int damage2 = calculateDamage((int) (player.attack * 1.8), enemy.defence);
                    enemy.takeDamage(damage2);
                    System.out.println("消耗10HP，你对"+enemy.name+"使用了强力一击，造成了"+damage2+"点伤害！");
                }else {
                    System.out.println("您体力不足，攻击失败！");
                }
                break;
            case 3:
                if(player.HP > 5){
                    //消耗我方5HP
                    player.takeDamage(5);
                    //计算回复的血量
                    Random r = new Random();
                    int healHp = r.nextInt(21);
                    //真正回复血量
                    player.heal(healHp);
                    System.out.println("消耗5HP，您回复了"+healHp+"点生命值！");
                }else {
                    System.out.println("您体力不足，回复生命失败！");
                }
                break;
        }
    }

    //5.6敌人回合：选择行动（50%的几率普通攻击/50%的几率技能攻击/不同的敌人采取不同的技能进行攻击）
    private void enemyTurn(EnemyCharacter enemy, HeroCharacter player) {
        System.out.println("=====敌人"+enemy.name+"回合=====");

        //计算当前敌人使用的技能
        //50%的几率普通攻击/50%的几率技能攻击
        //表示敌人要采取的攻击手段
        String action = "普通攻击";

        //进行几率的计算
        Random r = new Random();
        int num =  r.nextInt(10);//0-4--->普通攻击，5-9--->技能攻击

        if(num >= 5){
            action = enemy.skill;
        }

        //根据不同的情况，采用不同的攻击手段】
        switch (action) {
            case "普通攻击":
                System.out.println("敌人采取了普通攻击");
                //计算敌人对玩家的伤害
                int damage1 = calculateDamage(enemy.attack, player.defence);
                //提示敌人对玩家的伤害结果
                System.out.println("⚔️"+enemy.name+"对你使用了普通攻击，造成了"+damage1+"点伤害！");
                //扣血的操作
                player.takeDamage(damage1);
                break;
            case "猛击":
                System.out.println("当前的战士采取了猛击");
                //计算敌人对玩家的伤害
                int damage2 = calculateDamage((int) (enemy.attack * 1.5), player.defence);
                //提示敌人对玩家的伤害结果
                System.out.println("⚔️"+enemy.name+"对你使用了猛击，造成了"+damage2+"点伤害！");
                //扣血的操作
                player.takeDamage(damage2);
                break;
            case "快速攻击":
                System.out.println("当前的刺客采取了快速攻击");
                int damage3 = 0;
                for (int i = 0; i < 2; i++) {
                    int temp = calculateDamage(enemy.attack / 2, player.defence);
                    damage3 += temp;
                }
                System.out.println("⚔️"+enemy.name+"对你使用了快速攻击，造成了"+damage3+"点伤害！");
                //扣血的操作
                player.takeDamage(damage3);
                break;
            case "防御姿态":
                System.out.println("当前的坦克采取了防御姿态");
                //修改变量
                enemy.defending = true;
                System.out.println("当前的"+enemy.name+"采取了防御姿态！");
                break;
            case "火球术":
                System.out.println("当前的法师采取了火球术");
                int damage4 = calculateDamage((int) (enemy.attack * 1.8), player.defence);
                System.out.println("⚔️"+enemy.name+"对你使用了火球术，造成了"+damage4+"点伤害！");
                //扣血的操作
                player.takeDamage(damage4);
                break;
        }
    }


    //作用：用来计算双方战斗的时候，造成的伤害
    //普通攻击的调用方式calculateDamage(我方攻击力，对方的防御力)
    //技能攻击的调用方式calculateDamage(我方的攻击力 * 1.8，对方的防御力)
    public int calculateDamage(int attack, int defense) {
        //伤害 = 攻击力 - 防御力
        int damage = attack - defense;
        if(damage < 1){
            damage = 1;
        }
        return damage;
    }

}