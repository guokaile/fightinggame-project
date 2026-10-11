package com.itheima.ui;

import com.itheima.domain.User;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Login {

    //这个方法表示的就是登录注册的主界面（是以控制台的形式进行展示的）
    public void start() {
        System.out.println("游戏的登录注册页面打开了~");

        ArrayList<User> usersList = new ArrayList<>();

        //不放在while循环里面，因为每次循环都要重新创建一个对象，效率低
        Scanner sc = new Scanner(System.in);
        //ctrl + alt + t 选择对应语句包裹代码
        while (true) {
            System.out.println("╔════════════════════════════════╗");
            System.out.println("    🎮 欢迎来到文字格斗游戏 🎮   ");
            System.out.println("╚════════════════════════════════╝");
            System.out.println("请选择操作：1登录 2注册 3退出");


            String choose = sc.next();

            switch (choose) {
                case "1" -> login(usersList);
                case "2" -> register(usersList);
                case "3" -> {
                    System.out.println("用户选择了退出操作~");
                    System.exit(0);
                }
                default -> System.out.println("输入有误，请重新输入~");
            }
        }
    }

    //用户登录操作
    public void login(ArrayList<User> usersList) {
        System.out.println("用户选择了登录操作~");

        //判断用户名是否存在
        //      不存在：提示注册
        //      存在：若禁用，提示联系客服
        //      存在：校验验证码是否正确
        //      验证码是否正确（3次机会）

        //1.键盘录入用户名
        Scanner sc = new Scanner(System.in);
        System.out.print("请输入用户名：");
        String username = sc.next();

        //2.判断用户名是否存在
        if (!containsUsername(usersList, username)) {
            System.out.println("用户名" + username + "不存在，请先注册！");
            //如果用户名不存在，结束登录行为，回到选择界面当中去注册
            return;
        }

        //3.存在：判断是否禁用
        /*通过username，获取到当前的用户对象，再查看账户的状态*/
        int index = findIndex(usersList, username);
        //根据索引获取到当前的用户对象
        User u = usersList.get(index);
        if (!u.isStatus()) {
            System.out.println("用户" + username + "已被禁用，请联系客服！");
            //如果用户名已被禁用，结束登录行为，回到选择界面当中去注册
            return;
        }

        //4.用户继续录入验证码和密码
        /*从用户对象中获取到正确的密码*/
        String correctPassword = u.getPassword();
        for (int i = 0; i < 3; i++) {
            System.out.println("请输入密码：");
            String password = sc.next();

            //每次验证密码的时候，都要重新输入验证码
            while (true) {
                //先生成一个正确的验证码
                String correctCode = getCode();
                System.out.println("正确的验证码是：" + correctCode);

                System.out.println("请输入验证码：");
                String code = sc.next();

                /*这里使用equalsIgnoreCase方法，因为验证码是不区分大小写的*/
                if (correctCode.equalsIgnoreCase(code)) {
                    System.out.println("验证码输入正确！");
                    //如果验证码输入正确，跳出循环
                    break;
                } else {
                    System.out.println("验证码输入错误！");
                    //如果验证码输入错误，需要重新生成一个新的验证码，并且让用户重新输入
                    continue;
                }
            }

            //校验密码是否正确
            if (correctPassword.equals(password)) {
                System.out.println("登录成功,游戏启动~");
                //创建FightingGame对象，并调用gameStart方法，启动游戏
                FightingGame fg = new FightingGame();
                fg.gameStart(username);
                break;
            } else {
                System.out.println("登录失败，密码输入错误！");
                if (i == 2) {
                    //3次机会用完
                    u.setStatus(false);//将账户的状态设置为false，表示账户已被禁用
                    System.out.println("当前账户"+username+"已锁定，请联系客服！");
                    return;
                }else{
                    System.out.println("密码错误，还剩下"+(2-i)+"次机会~");
                }
            }

        }

    }

    //用户注册操作
    public void register(ArrayList<User> usersList) {
        System.out.println("用户选择了注册操作~");

        //1.创建user对象（空参）
        User user = new User();

        //2.键盘录入用户名
        //校验用户名是否符合要求
        // （将Scanner创建对象放到外面是为了提高效率，避免每次循环都要重新创建一个对象）
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print("请输入用户名：");
            String username = sc.next();

        /*
             1.长度必须在3-16位之间
             2.只能够由字母、数字组成
             3.用户名唯一
        */
            //1.校验用户名长度是否在3-16位之间
            if (!checkLen(3, 16, username)) {
                System.out.println("用户名长度不符合要求，必须是3-16位之间！");
                continue;
            }
            //2.校验用户名是否由字母、数字组成
            if (!checkUsername(username)) {
                System.out.println("用户名不符合要求，必须是字母、数字组成！");
                continue;
            }
            //3.校验用户名是否唯一
            if (containsUsername(usersList, username)) {
                System.out.println("用户名已存在，请重新输入！");
                continue;
            }
            //4.当代码执行到这里，说明用户名是合法的，将用户名赋值给user对象
            user.setUsername(username);
            break;
        }


        //3.键盘录入密码
        while (true) {

            // 校验密码是否符合要求
            System.out.print("请输入密码：");
            String password1 = sc.next();
            //System.out.println();
            System.out.print("请再次输入密码：");
            String password2 = sc.next();

            // 校验两次输入的密码是否一致
            if (!password1.equals(password2)) {
                System.out.println("两次输入的密码不一致，请重新输入！");
                continue;
            }

            //1.校验密码长度是否在3-8位之间
            if (!checkLen(3, 8, password1)) {
                System.out.println("密码长度不符合要求，必须是3-8位之间！");
                continue;
            }
            //2.校验密码是否由字母、数字组成
            if (!checkPassword(password1)) {
                System.out.println("密码不符合要求，必须是字母+数字组成！");
                continue;
            }
            //3.当代码执行到这里，说明密码是合法的，将密码赋值给user对象
            user.setPassword(password1);
            break;
        }

        //4.将user对象添加到集合中
        usersList.add(user);

        //5.提示用户注册成功
        System.out.println("用户：" + user.getUsername() + "注册成功~");
    }



    /*
        下面是工具类的方法
    */


    //作用：在集合当中寻找username所在的索引
    public int findIndex(ArrayList<User> usersList, String username) {
        /*这里不适用增强for循环，因为增强for循环不能获取到集合元素的索引*/
        //遍历整个用户集合
        for (int i = 0; i < usersList.size(); i++) {
            User u = usersList.get(i);
            //逐一判断用户名是否匹配
            if (u.getUsername().equals(username)) {
                return i;
            }
        }
        return -1;
    }

    //作用：判断字符串的长度是否在指定范围内
    public boolean checkLen(int minLen, int maxLen, String str) {
        /*if (str.length() < minLen || str.length() > maxLen) {
            return true;
        }else {
            return false;
        }*/
        return str.length() >= minLen && str.length() <= maxLen;
    }

    //作用：判断用户名是否符合要求
    //只能够由字母（字母至少有一个）、数字组成,不能是纯数字
    public boolean checkUsername(String username) {
        return username.matches("[a-zA-Z0-9]+") && !username.matches("[0-9]+");
    }

    //作用：判断密码是否符合要求
    //只能够由字母（字母至少有一个）、数字（数字至少有一个）组成,不能是纯数字
    public boolean checkPassword(String password) {
        return password.matches("^[a-zA-Z0-9]+$")  // 密码只能由字母、数字组成
                && !password.matches("[0-9]+")     // 密码不能是纯数字
                && !password.matches("[a-zA-Z]+"); // 密码不能是纯字母
    }

    //作用：判断用户名是否唯一
    public boolean containsUsername(ArrayList<User> usersList,String username) {
        //遍历集合，判断用户名是否唯一（方法一：普通for循环）
        /*
        for (int i = 0; i < usersList.size(); i++) {
            if (usersList.get(i).getUsername().equals(username)) {
                return true;
            }
        }
        return false;
        */
        //遍历集合，判断用户名是否唯一（方法二：增强for循环【底层是 Iterator 迭代器】）
        for (User user : usersList) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    //作用：生成验证码
    public static String getCode() {
        /*
            长度为5
            由4位大写或者小写字母和1位数字组成，同一个字母可重复
            数字可以出现在任意位置
        */
        //1.把所有的大写和小写字母都放到一个容器
        /*Character是char的包装类，属于引用类型，所以可以作为泛型的参数*/
        /*泛型<>的参数只能够用引用类型，不能用基本类型*/
        ArrayList<Character> list = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            list.add((char) ('a' + i));//编译器自动把 char → Character，这个过程就是自动装箱。
            list.add((char) ('A' + i));
        }
        //2.从集合当中随机抽取4个字母
        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        for (int i = 0; i < 4; i++) {
            //随机获取集合中的索引
            int index = r.nextInt(list.size());
            char c = list.get(index);//自动拆箱
            sb.append(c);
        }
        //3.生成一个随机的数字（0-9）
        sb.append(r.nextInt(10));
        //4.数字的位置可以是任意的
        //先将sb变为字符串，调用toCharArray()方法，转换为字符数组
        char[] arr = sb.toString().toCharArray();
        //把最大索引上的数据，跟一个随机的数字索引进行交换
        int i = r.nextInt(arr.length);
        //交换
        //最大索引：arr.length-1
        //随机索引：i
        char temp = arr[i];
        arr[i] = arr[arr.length-1];
        arr[arr.length-1] = temp;
        //5.将字符数组转换为字符串
        String code = new String(arr);
        return code;
    }

}

