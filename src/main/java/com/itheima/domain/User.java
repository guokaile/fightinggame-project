package com.itheima.domain;

import java.util.Random;

public class User {
    // id，用户名，密码，状态
    private String id;
    private String username;
    private String password;
    private boolean status;

    // 空参构造方法
    public User() {
        //调用createId方法生成id值
        id = createId();
        //修改status默认状态为true
        status = true;
    }

    // 有参构造方法
    public User( String username, String password) {
        id = createId();
        this.username = username;
        this.password = password;
        status = true;
    }

    //id是用户无法设置的，是系统自动生成的，格式为：heima+5位随机数
    //提升到了类级别，为了封装性更好，权限设置为private
    private Random r = new Random();//为了提高效率，只创建一次随机数对象
    private String createId(){
        StringBuilder sb = new StringBuilder("heima");

        //Random r = new Random();如果写在方法里面，每次调用方法就会创建一个新的随机数对象，效率低
        //所以要写在类里面，只创建一次随机数对象
        for (int i = 0; i < 5; i++) {
            int num = r.nextInt(10);
            sb.append(num);
        }
        //将sb转换为字符串返回
        return sb.toString();
    }

    public String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //布尔类型的特点：get is
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
