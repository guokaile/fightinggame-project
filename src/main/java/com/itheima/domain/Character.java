package com.itheima.domain;

public class Character {
    public String name;
    public int HP;
    public int maxHP;
    public int attack;
    public int defence;

    public Character() {

    }
    //刚开始创建角色时，血量是满的，所以HP=最大HP
    public Character(String name, int HP, int attack, int defence) {
        this.name = name;
        this.HP = HP;
        this.maxHP = HP;
        this.attack = attack;
        this.defence = defence;
    }

    //1.判断是否存活
    public boolean isAlive() {
        return HP > 0 ;
    }

    //2.回复血量
    /*作用：回复血量，形参具体回复多少血量*/
    public void heal(int amount) {
        HP += amount;
        //回复血量不能超过最大血量
        if (HP > maxHP) {
            HP = maxHP;
        }
    }

    //3.受到伤害
    /*作用：受到N点伤害之后，还有多少血量，形参具体受到多少伤害*/
    public void takeDamage(int damage) {
        HP -= damage;
        //受到伤害后，血量不能小于0
        if (HP <= 0) {
            HP = 0;
        }
    }

    //4.展示人物的属性
    public String showInfo() {
        return name + "[当前生命：" + HP + "，攻击：" + attack + "，防御：" + defence + "]";
    }

}
