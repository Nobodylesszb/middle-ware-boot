package com.jwt.token.demo.utils;

/**
 * @auther: bo
 * @Date: 2020/8/4 15:45
 * @version:
 * @description:
 */
public class ThreadLocalUtils {

    private final static ThreadLocal<String> USER_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 设置当前线程中的用户
     *
     * @param user 登录的用户
     */
    public static void setUser(String user) {
        USER_THREAD_LOCAL.set(user);
    }

    /**
     * 获取线程中的用户
     *
     * @return 返回用户
     */
    public static String getUser() {

        return USER_THREAD_LOCAL.get();
    }

    public static void removeUser() {
        USER_THREAD_LOCAL.remove();
    }

}
