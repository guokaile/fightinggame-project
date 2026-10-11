package com.itheima;

//import com.itheima.ui.FightingGame;
//import com.itheima.ui.Login;
import com.itheima.ui.FightingGame;
import com.itheima.ui.Login;

public class App {
    public static void main(String[] args) {
        //启动类
        //这个类只负责启动整个程序，里面不写任何业务逻辑
        //启动登录注册的界面
        Login login = new Login();
        login.start();
    }
}
