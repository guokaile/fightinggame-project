# 文字格斗游戏 fightinggame

一个基于 Java 的控制台文字格斗游戏：登录注册后创建角色，与多名敌人进行回合制战斗。

## 功能特性

- 🎮 控制台登录 / 注册系统（用户列表管理）
- ⚔️ 角色创建与属性分配（HP、攻击、防御）
- 🧙 多名敌人依次挑战：初级战士、敏捷刺客、重装坦克、神秘法师
- 📈 每胜利一场，下一场敌人属性增强（HP+10、ATK+3、DEF+2），难度递增
- ✨ 技能系统：玩家和敌人各有专属技能

## 技术栈

- Java 25
- Maven
- 纯 JDK，无第三方依赖

## 项目结构

```
src/main/java/com/itheima/
├── App.java                  # 程序入口，启动登录界面
├── domain/                   # 领域模型
│   ├── Character.java        # 角色基类（HP/攻击/防御/技能）
│   ├── HeroCharacter.java    # 玩家角色
│   ├── EnemyCharacter.java   # 敌人角色
│   └── User.java             # 用户（登录/注册）
└── ui/                       # 界面层
    ├── Login.java            # 登录注册界面
    └── FightingGame.java     # 游戏战斗主界面
docs/                         # 项目文档（架构图、模块依赖图、核心业务流程图）
```

## 运行方式

```bash
mvn compile
java -cp target/classes com.itheima.App
```

## 游戏截图

```
╔════════════════════════════════╗
    🎮 欢迎来到文字格斗游戏 🎮
╚════════════════════════════════╝
请选择操作：1登录 2注册 3退出
```
