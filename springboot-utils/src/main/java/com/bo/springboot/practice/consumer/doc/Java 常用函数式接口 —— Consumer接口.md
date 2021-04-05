### 1、接口概述

`Consumer` 接口也比较简单，只有两个方法，一个是抽象方法，一个是默认方法：

```
@FunctionalInterface
public interface Consumer<T> {

    void accept(T t);

    default Consumer<T> andThen(Consumer<? super T> after){
        Objects.requireNonNull(after);
        return (T t) -> { accept(t); after.accept(t); };
    }
}
```

### 2、`accept` 方法

该方法接收一个接口泛型类型的对象，没有返回值。

看看集合的 `forEach` 方法的使用：

```
public class ConsumerTest {
    public static void main(String[] args) {
        List<String> names = new ArrayList<String>() {
            {
                add("Tom");
                add("Jerry");
            }
        };
        names.forEach(e -> System.out.println(e));
        names.forEach(System.out::println);
    }
}
```

我们使用 `forEach` 方法的时候，该方法就是使用了 `Consumer` 接口作为参数。这是我们最常见的使用 `Consumer` 的方式。

除了打印信息，一般我们对于集合中的对象的某些数据需要更改，也经常使用forEach 遍历，然后对于每个对象做一些业务操作。

虽然 `forEach` 常用，但是一般我们都很少关注 `forEach` 的实现，也很少自己写个方法用到 `Consumer` 接口：

```
    default void forEach(Consumer<? super T> action) {
        Objects.requireNonNull(action);
        for (T t : this) {
            action.accept(t);
        }
    }
```

### 3、Consumer接口使用场景-回调

```
public class ConsumerTest {

    private static final Map<Integer, String> map = new HashMap<Integer, String>() {
        {
            put(10, "Tom");
            put(3, "Jerry");
        }
    };

    public static void main(String[] args) {
        //调用方法，同时编写对结果的回调：此处仅仅打印而已
        test(3, System.out::println);
    }

    public static void test(Integer age, Consumer<String> consumer) {
        //业务处理
        System.out.println(age);

        //对处理结果的回调:下面的ifPresent参数也是Consumer接口，所有下面三种写法都可以
        //Optional.ofNullable(map.get(age)).ifPresent(e -> consumer.accept(e));
        //Optional.ofNullable(map.get(age)).ifPresent(consumer::accept);
        Optional.ofNullable(map.get(age)).ifPresent(consumer);
    }
}
```

`Consumer` 接口一般作为方法参数来实现回调的功能，例如上面的例子，`test` 函数传递待处理的对象 `age` ,经过业务处理得到其它结果对象，之后调用 `accept` 对结果对象进行处理。

**实际中回调处理的对象是根据入参得到其它结果**。比如传入姓名从数据库查询数据，回调函数将数据保存在其它地方，那么这个其它地方就需要调用者自己处理，比如存文件。

### 4、默认方法andThen

方法源码：

```
    default Consumer<T> andThen(Consumer<? super T> after) {
        Objects.requireNonNull(after);
        return (T t) -> { accept(t); after.accept(t); };
    }
```

使用场景：

```
    public static void main(String[] args) {
        Consumer<String> first = (x) -> System.out.println(x.toLowerCase());
        Consumer<String> next = (x) -> System.out.println(x.toUpperCase());
        first.andThen(next).accept("Hello World");
    }
```

andThen 接收一个 Consumer 接口，返回的也是一个 Consumer 接口，同样的，调用该方法的也是 Consumer 接口。这个地方比较绕，需要对照上面两处代码仔细分析。

`first` 是一个 `Consumer` 接口，当调用 `andThen` 方法的时候，并不是执行 `(T t) -> { accept(t); after.accept(t); }` 这段代码，而是返回了一个 `Consumer` 接口，注意它的结构是 **给一个对象 t，然后大括号中消费 t** 。 处理逻辑是当前 `Consumer` 执行 `accept` 方法，然后再让 `after` 这个 `Consumer` 执行 `accept` 方法。理解 `andThen` 方法返回的结构特别重要。

当`first.andThen(next)`执行完成，得到一个 `Consumer` 接口接口后，再次调用 `accept("Hello World")` 时，实际上对 Hello World 字符串执行的内容就是大括号里面的内容了：`accept(t); after.accept(t);` 也就是上面的先输出小写字符串、再输出大写的字符串了。

### 5、andThen 方法使用场景

```
public class ConsumerTest {

    private static final Map<Integer, Consumer<String>> QUEUE = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        resolve(1, s -> System.out.println(s.toUpperCase()));
        resolve(1, s -> System.out.println(s.toLowerCase()));
        resolve(1, s -> System.out.println(s.substring(0, 2)));
        QUEUE.get(1).accept("Hello World");
    }

    public static void resolve(Integer id, Consumer<String> callback) {
        final Consumer<String> existing = QUEUE.get(id);
        if (callback == null) callback = i -> {};
        if (existing != null && callback != existing) {
            callback = existing.andThen(callback);
        }
        QUEUE.put(id, callback);
    }
}
```

结果如下：

> HELLO WORLD
> hello world
> He

上面代码中的 resolve 方法根据 id , 向 map 类型的 QUEUE 中增加多个回调函数，最后执行的时候，多个回调函数都被执行，很类似责任链的设计模式。可以结合上面的讲解仔细理解

```
callback = existing.andThen(callback);
```

以及在执行的地方：

```
QUEUE.get(1).accept("Hello World");
```