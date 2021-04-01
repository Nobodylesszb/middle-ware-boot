package com.bo.springboot.practice.consumer.code;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * @auther: bo
 * @Date: 2021/4/1 23:04
 * @version:
 * @description:
 */
public class ConsumerTest {

    private static final Map<Integer, String> map = new HashMap<Integer, String>() {
        {
            put(10, "Tom");
            put(3, "Jerry");
        }
    };

    public static void test(Integer age, Consumer<String> consumer) {
        //业务处理
        System.out.println(age);

        //对处理结果的回调:下面的ifPresent参数也是Consumer接口，所有下面三种写法都可以
        //Optional.ofNullable(map.get(age)).ifPresent(e -> consumer.accept(e));
        //Optional.ofNullable(map.get(age)).ifPresent(consumer::accept);
        Optional.ofNullable(map.get(age)).ifPresent(consumer);
    }

    public static void main(String[] args) {
        //调用方法，同时编写对结果的回调：此处仅仅打印而已
        test(3, System.out::println);
    }

}
