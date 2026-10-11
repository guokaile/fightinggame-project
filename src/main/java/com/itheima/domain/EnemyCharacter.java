package com.itheima.domain;

//敌方的英雄角色
public class EnemyCharacter extends Character {
    public String skill;
    public boolean defending;//当前游戏人物是否拥有防御的状态

    public EnemyCharacter() {
        super();
    }

    public EnemyCharacter(String name, int HP, int attack, int defence, String skill) {
        super(name, HP, attack, defence);
        this.skill = skill;
    }

    //重写父类的方法
    @Override
    public void takeDamage(int damage) {
        //如果处于防御状态，伤害减半
        //true:防御状态         false:未防御状态
        if (defending) {
            damage = damage / 2 > 1 ? damage / 2 : 1;
            //表示防御的状态只能持续一个回合
            defending = false;
        }
        //调用父类的方法，实现血量的减少
        super.takeDamage(damage);
    }

}
