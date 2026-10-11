package com.itheima.domain;

import java.util.ArrayList;

//我方的英雄角色
public class HeroCharacter extends Character {
    public ArrayList<String> skillList;

    //顺手把集合的对象也创建了
    //好处：外界我要给我方角色添加技能的时候，无需考虑集合，直接调用skillList.add("技能名")即可
    public HeroCharacter() {
        super();
        skillList = new ArrayList<>();
    }

    public HeroCharacter(String name, int HP, int attack, int defence) {
        super(name, HP, attack, defence);
        skillList = new ArrayList<>();
    }

    //展示技能
    public String showSkills(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < skillList.size(); i++){
            //添加数据
            sb.append(skillList.get(i));
            //如果不是最后一个元素，需要添加逗号空格
            if(i != skillList.size() - 1){
                sb.append(", ");
            }
        }
        return sb.toString();
    }
}
