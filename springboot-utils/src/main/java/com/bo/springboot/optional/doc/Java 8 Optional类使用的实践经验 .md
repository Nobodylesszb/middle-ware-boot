# 前言

Java中空指针异常(NPE)一直是令开发者头疼的问题。Java 8引入了一个新的Optional类，使用该类可以尽可能地防止出现空指针异常。

Optional 类是一个可以为null的容器对象。如果值存在则isPresent()方法会返回true，调用get()方法会返回该对象。Optional提供很多有用的方法，这样开发者就不必显式进行空值检测。

本文将介绍Optional类包含的方法，并通过示例详细展示其用法。

------

# 一、基础知识

## 1.1 Optional类方法

本节基于作者的实践，给出Optional类常用的方法(**其他方法不推荐使用**)：

| 方法                                                   | 描述                                                         |
| ------------------------------------------------------ | ------------------------------------------------------------ |
| static Optional ofNullable(T value)                    | 为指定的value创建一个Optional。若value为null，则返回空的Optional |
| Optional map(Function<? super T, ? extends U> mapper)  | 若有值，则对其执行调用mapper映射函数得到返回值。若返回值不为 null，则创建包含映射返回值的Optional作为map方法返回值，否则返回空Optional |
| T orElse(T other)                                      | 若存在该值则将其返回， 否则返回 other                        |
| T orElseGet(Supplier<? extends T> other)               | 若存在该值则将其返回，否则触发 other，并返回 other 调用的结果。注意，该方法为惰性计算 |
| void ifPresent(Consumer<? super T> consumer)           | 若Optional实例有值则为其调用consumer，否则不做处理           |
| Optional filter(Predicate<? super T> predicate)        | 若有值并且满足断言条件返回包含该值的Optional，否则返回空Optional |
| T orElseThrow(Supplier<? extends X> exceptionSupplier) | 若存在该值则将其返回，否则抛出由 Supplier 继承的异常         |

其中，`map()`方法的`? super T`表示泛型 T 或其父类，`? extend U`表示泛型U或其子类。泛型的上限和下限遵循PECS(Producer Extends Consumer Super)原则，即

> 带有子类限定的可从泛型读取，带有超类限定的可从泛型写入

`Function`、`Supplier`和`Consumer`均为函数式接口，支持Lambda表达式。

## 1.2 Lambda表达式与方法引用

标准的Lambda表达式语法结构如下：

> (参数列表) -> {方法体}

只有一个参数时，可省略小括号；当方法体只有一条语句时，可省略大括号和return关键字。

详细的Lambda语法介绍可参考[深入理解Java8 Lambda表达式](http://swiftlet.net/archives/3331)。

如果Lambda表达式里只调用了一个方法，还可以使用Java 8新增的方法引用(`method reference`)写法，以提升编码简洁度。方法引用有如下四种类型：

| 类别                               | 方法引用格式           | 等效的lambda表达式                      |
| ---------------------------------- | ---------------------- | --------------------------------------- |
| 静态方法的引用                     | Class::staticMethod    | (args) -> Class.staticMethod(args)      |
| 对特定对象的实例方法的引用         | object::instanceMethod | (args) -> obj.instanceMethod(args)      |
| 对特定类型任意对象的实例方法的引用 | Class::instanceMethod  | (obj, args) -> obj.instanceMethod(args) |
| 构造方法的引用                     | ClassName::new         | (args) -> new ClassName(args)           |

例如：System.out::println等同于x->System.out.println(x)，String::toLowerCase等同于x->x.toLowerCase()，BigDecimal::new等同于x->new BigDecimal(x)。

详情也可参考[Method References](https://docs.oracle.com/javase/tutorial/java/javaOO/methodreferences.html)或[Java 8 Method Reference: How to Use it](https://www.codementor.io/eh3rrera/using-java-8-method-reference-du10866vx)。

# 二、用法示例

为充分体现Optional类的“威力”，首先以组合方式定义`Location`和`Person`两个类。

**Location类：**

```java
public class Location {
    private String country;
    private String city;

    public Location(String country, String city) {
        this.country = country;
        this.city = city;
    }

    public void setCountry(String country) { this.country = country; }
    public void setCity(String city) { this.city = city; }

    public String getCountry() { return country; }
    public String getCity() { return city; }

    public static void introduce(String country) {
        System.out.println("I'm from " + country + ".");
    }
}
```

**Person类：**

```java
public class Person {
    private String name;
    private String gender;
    private int age;
    private Location location;

    public Person() {
    }
    public Person(String name, String gender, int age) {
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

    public void setName(String name) { this.name = name; }
    public void setGender(String gender) { this.gender = gender; }
    public void setAge(int age) { this.age = age; }
    public Person setLocation(String country, String city) {
        this.location = new Location(country, city);
        return this;
    }

    public String getName() { return name; }
    public String getGender() { return gender; }
    public int getAge() { return age; }
    public Location getLocation() { return location; }

    @Override
    public String toString() {
        return "Person{" + "name='" + name + '\'' + '}';
    }

    public void greeting(Person person) {
        System.out.println("Hello " + person.getName() + "!");
    }

    public static void showIdentity(Person person) {
        System.out.println("Person: {" + "name='" + person.getName() + '\'' + ", gender='"
                + person.getGender() + '\'' + ", age=" + person.getAge() + '}');
    }
}
```

注意，*以上两个类仅作演示示例用，并不代表规范写法*。例如，Person类所提供的构造方法未包含`location`参数，而是通过`setLocation()`方法间接设置。这是为了简化Person对象初始化及构造`location`为null的情况。此外，`greeting()`作为实例方法，却未访问任何实例字段。

下文将基于`Location`和`Person`类，展示Optional的推荐用法。考虑到代码简洁度，示例中尽量使用方法引用。

## 2.1 map + orElse

功能描述：判断Person在哪个城市，并返回城市小写名；失败时返回nowhere。

**传统写法1：**

```java
public static String inWhichCityLowercaseTU(Person person) { //Traditional&Ugly
    if (person != null) {
        Location location = person.getLocation();
        if (location != null) {
            String city = location.getCity();
            if (city != null) {
                return city.toLowerCase();
            } else {
                return "nowhere";
            }
        } else {
            return "nowhere";
        }
    } else {
        return "nowhere";
    }
}
```

可见，层层嵌套，繁琐且易错。

**传统写法2：**

```java
public static String inWhichCityLowercaseT(Person person) { //Traditional
    if (person != null
            && person.getLocation() != null
            && person.getLocation().getCity() != null) {
        return person.getLocation().getCity().toLowerCase();
    }
    return "nowhere";
}
```

这种写法优于前者，但级联判空很容易"淹没"正常逻辑(return句)。

**新式写法：**

```java
public static String inWhichCityLowercase(final Person person) {
    return Optional.ofNullable(person)
        .map(Person::getLocation)
        .map(Location::getCity)
        .map(String::toLowerCase)
        .orElse("nowhere");
}
```

采用Optional的写法，逻辑层次一目了然。似无判空，胜却判空，尽在不言中。

## 2.2 map + orElseThrow

功能描述：判断Person在哪个国家，并返回国家大写名；失败时抛出异常。

传统写法类似上节，新式写法如下：

```java
public static String inWhichCountryUppercase(final Person person) {
    return Optional.ofNullable(person)
        .map(Person::getLocation)
        .map(Location::getCountry)
        .map(String::toUpperCase)
        .orElseThrow(NoSuchElementException::new);
    // 或orElseThrow(() -> new NoSuchElementException("No country information"))
}
```

## 2.3 map + orElseGet

功能描述：判断Person在哪个国家，并返回from + 国家名；失败时返回from Nowhere。

新式写法：

```java
private String fromCountry(final String country) {
    return "from " + country;
}
private String fromNowhere() {
    return "from Nowhere";
}
private String fromWhere(final Person person) {
    return Optional.ofNullable(person)
            .map(Person::getLocation)
            .map(Location::getCountry)
            .map(this::fromCountry)
            .orElseGet(this::fromNowhere);
}
```

## 2.4 map + filter + ifPresent

功能描述：当Person在中国时，调用`Location.introduce()`；否则什么都不做。

**传统写法：**

```java
public static void introduceChineseT(final Person person) {
    if (person != null
            && person.getLocation() != null
            && person.getLocation().getCountry() != null
            && "China".equals(person.getLocation().getCountry())) {
        Location.introduce("China");
    }
}
```

**新式写法：**

```java
public static void introduceChinese(final Person person) {
    Optional.ofNullable(person)
        .map(Person::getLocation)
        .map(Location::getCountry)
        .filter("China"::equals)
        .ifPresent(Location::introduce);
}
```

注意，`ifPresent()`用于无需返回值的情况。

## 2.5 Optional + Stream

Optional也可与Java 8的Stream特性共用，例如：

```java
private static void optionalWithStream() {
    Stream<String> names = Stream.of("Zhou Yi", "Wang Er", "Wu San");
    Optional<String> preWithL = names
            .filter(name -> name.startsWith("Wang"))
            .findFirst();
    preWithL.ifPresent(name -> {
        String u = name.toUpperCase();
        System.out.println("Get " + u + " with family name Wang!");
    });
}
```

## 2.6 测试与输出

测试代码及其输出如下：

```java
public class OptionalDemo {
    //methods from 2.1 to 2.5
    public static void main(String[] args) {
        optionalWithStream();
        // 输出：Get WANG ER with family name Wang!

        Person person = new Person(); //fetchPersonFromSomewhereElse()
        System.out.println(new OptionalDemo().fromWhere(person));
        // 输出：from Nowhere

        List<Person> personList = new ArrayList<>();
        Person mike = new Person("mike", "male", 10).setLocation("China", "Nanjing");
        personList.add(mike);
        System.out.println(inWhichCityLowercase(mike));
        // 输出：nanjing

        Person lucy = new Person("lucy", "female", 4);
        personList.add(lucy);
        personList.forEach(lucy::greeting);
        // 输出：Hello mike!\nHello lucy!
        // 注意，此处仅为展示object::instanceMethod写法

        personList.forEach(Person::showIdentity);
        // 输出：Person: {name='mike', gender='male', age=10}
        //      Person: {name='lucy', gender='female', age=4}
        personList.forEach(OptionalDemo::introduceChinese);
        // 输出：I'm from China.
        System.out.println(inWhichCountryUppercase(lucy));
        // 输出：Exception in thread "main" java.util.NoSuchElementException
        //          at java.util.Optional.orElseThrow(Optional.java:290)
        //          at com.huawei.vmf.adapter.inventory.OptionalDemo.inWhichCountryUppercase(OptionalDemo.java:47)
        //          at com.huawei.vmf.adapter.inventory.OptionalDemo.main(OptionalDemo.java:108)
    }
}
```

## 2.7 真实项目代码

原始实现如下：

```java
public String makeDevDetailVersion(final String strDevVersion, final String strDevDescr, final String strDevPlatformName)
{
    String detailVer = "VRP";

    if (null != strDevPlatformName && !strDevPlatformName.isEmpty())
    {
        detailVer = strDevPlatformName;
    }

    String versionStr = null;
    Pattern verStrPattern = Pattern.compile("Version(\\s)*([\\d]+[\\.][\\d]+)");
    if(strDevDescr != null)
    {
        Matcher verStrMatcher = verStrPattern.matcher(strDevDescr);
        if (verStrMatcher.find())
        {
            versionStr = verStrMatcher.group();
        }
        if (null != versionStr)
        {
            Pattern digitalPattern = Pattern.compile("([\\d]+[\\.][\\d]+)");
            Matcher digitalMatcher = digitalPattern.matcher(versionStr);
            if (digitalMatcher.find())
            {
                detailVer = detailVer + digitalMatcher.group() + " ";
            }
        } 
    }

    return detailVer + strDevVersion;
}
```

采用Optional类改写如下(正则匹配部分略有修改)：

```java
private static final Pattern VRP_VER = Pattern.compile("Version\\s+(\\d\\.\\d{3})\\s+");
private static String makeDetailedDevVersion(final String strDevVersion, final String strDevDescr, final String strDevPlatformName) {
    String detailVer = Optional.ofNullable(strDevPlatformName)
            .filter(s -> !s.isEmpty()).orElse("VRP");

    return detailVer + Optional.ofNullable(strDevDescr)
            .map(VRP_VER::matcher)
            .filter(Matcher::find)
            .map(m -> m.group(1))
            .map(v -> v + " ").orElse("")
            + strDevVersion;
}
```

# 三、规则总结

使用Optional时，需注意以下规则：

1. Optional的包装和访问都有成本，因此不适用于一些特别注重性能和内存的场景。

2. 不要将null赋给Optional，应赋以`Optional.empty()`。

3. 避免调用isPresent()和get()方法，而应使用`ifPresent()`、`orElse()`、 `orElseGet()`和`orElseThrow()`。举一`isPresent()`用法示例：

   ```java
   private static boolean isIntegerNumber(String number) {
       number = number.trim();
       String intNumRegex = "\\-{0,1}\\d+";
       if (number.matches(intNumRegex)) {
           return true;
       } else {
           return false;
       }
   }
   // Optional写法1（含NPE修复及正则表达式优化）
   private static boolean isIntegerNumber1(String number) {
       return Optional.ofNullable(number)
               .map(String::trim)
               .filter(n -> n.matches("-?\\d+"))
               .isPresent();
   }
   // Optional写法2（含NPE修复及正则表达式优化，不用isPresent）
   private static boolean isIntegerNumber2(String number) {
       return Optional.ofNullable(number)
               .map(String::trim)
               .map(n -> n.matches("-?\\d+"))
               .orElse(false);
   }
   ```

4. Optional应该只用处理返回值，而不应作为类的字段(Optional类型不可被序列化)或方法(包括constructor)的参数。

5. 不要为了链式方法而使用Optional，尤其是在仅仅获取一个值时。例如：

   ```java
   // good
   return variable == null ? "blablabla" : variable;
   // bad
   return Optional.ofNullable(variable).orElse("blablabla");
   // bad
   Optional.ofNullable(someVariable).ifPresent(this::blablabla)
   ```

   滥用Optional不仅影响性能，可读性也不高。应尽可能避免使用null引用。

6. 避免使用Optional返回空的集合或数组，而应返回`Collections.emptyList()`、`emptyMap()`、`emptySet()`或`new Type[0]`。注意不要返回null，以便调用者可以省去繁琐的null检查。

7. 避免在集合中使用Optional，应使用`getOrDefault()`或`computeIfAbsent()`等集合方法。

8. 针对基本类型，使用对应的`OptionalInt`、`OptionalLong`和`OptionalDouble`类。

9. 切忌过度使用Optional，否则可能使代码难以阅读和维护。

   常见的问题是Lambda表达式过长，例如：

   ```java
   private Set<String> queryValidUsers() {
       Set<String> userInfo = new HashSet<String>(10);
   
       Optional.ofNullable(toJSonObject(getJsonStrFromSomewhere()))
               .map(cur -> cur.optJSONArray("data"))
               .map(cur -> { // 大段代码割裂了"思路"
                   for (int i = 0; i < cur.length(); i++) {
                       JSONArray users = cur.optJSONObject(i).optJSONArray("users");
   
                       if (null == users || 0 == users.length()) {
                           continue;
                       }
   
                       for (int j = 0; j < users.length(); j++) {
                           JSONObject userObj = users.optJSONObject(j);
                           if (!userObj.optBoolean("stopUse")) { // userObj可能为null!
                               userInfo.add(userObj.optString("userId"));
                           }
                       }
                   }
                   return userInfo;
               });
   
       return userInfo;
   }
   ```

   通过简单的抽取方法，可读性得到很大提高：

   ```java
   private Set<String> queryValidUsers() {
       return Optional.ofNullable(toJSonObject(getJsonStrFromSomewhere()))
               .map(cur -> cur.optJSONArray("data"))
               .map(this::collectNonStopUsers)
               .orElse(Collections.emptySet());
   }
   
   private Set<String> collectNonStopUsers(JSONArray dataArray) {
       Set<String> userInfo = new HashSet<String>(10);   
       for (int i = 0; i < dataArray.length(); i++) {
           JSONArray users = dataArray.optJSONObject(i).optJSONArray("users");
           Optional.ofNullable(users).ifPresent(cur -> {
               for (int j = 0; j < cur.length(); j++) {
                   Optional.ofNullable(cur.optJSONObject(j))
                           .filter(user -> !user.optBoolean("stopUse"))
                           .ifPresent(user -> userInfo.add(user.optString("userId")));
               }
           });
       }
       return userInfo;
   }
   ```