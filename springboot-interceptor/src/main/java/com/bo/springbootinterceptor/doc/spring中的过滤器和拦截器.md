## 过滤器 Filter

### 什么是过滤器？

与 Servlet 相似，过滤器是一些 web 应用程序组件，可以绑定到一个 web 应用程序中。但是与其他 web 应用组件不同的是，过滤器是“链”在容器的处理过程中的。这就意味着它们可以在请求达到 servlet 之前对其进行访问，也可以在响应信息返回到客户端之前对其进行拦截。这种访问使得过滤器可以检查并修改请求和响应的内容。

### Filter 的接口方法有哪些？

**init ：**

Filter 的初始化，在 Servlet 容器创建过滤器实例的时候调用，以确保过滤器能够正常工作。在 init() 方法执行过程中遇到如下问题时，web 容器将不会配置其可用 ：

- 抛出 ServletException
- 没有在 web 容器设定的时间内返回

**doFilter ：**

Filter 的核心方法，用于对每个拦截到的请求做一些设定好的操作。

典型用途如下：

- 在 HttpServletRequest 到达 Servlet 之前，拦截客户的 HttpServletRequest。
- 根据需要检查 HttpServletRequest ，也可以修改 HttpServletRequest 头和数据。
- 在 HttpServletResponse 到达客户端之前，拦截 HttpServletResponse。
- 根据需要检查 HttpServletResponse，可以修改 HttpServletResponse 头和数据。

**destory ：**

Filter 的销毁，在 Servlet 容器销毁过滤器实例时调用，以释放其占用的资源。只有在 doFilter() 方法中的所有线程退出或超时后，web 容器才会调用此方法。

### 如何实现一个自己的 Filter

首先我们需要创建一个类，让它实现 Filter 接口，然后重写接口中的方法：

```java
package com.demo.demofilter.demofilter.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@Order(1)   // 过滤顺序，值越小越先执行
@WebFilter(urlPatterns = "/demoFilter", filterName = "filterTest")
public class DemoFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("filter初始化中...");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        System.out.println("\ndoFilter()开始执行：发往 " + ((HttpServletRequest) servletRequest).getRequestURL().toString() + " 的请求已被拦截");

        System.out.println("检验接口是否被调用，尝试获取contentType如下： " + servletResponse.getContentType());
        // filter的链式调用；将请求转给下一条过滤链
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("检验接口是否被调用，尝试获取contentType如下： " + servletResponse.getContentType());

        System.out.println("doFilter()执行结束。\n");
    }

    @Override
    public void destroy() {
        System.out.println("filter销毁中...");
    }
}
复制代码
```

然后创建一个 Controller，对外提供两条请求路径：

```java
package com.demo.demofilter.demofilter.filter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("demoFilter")
public class FilterController {

    @GetMapping("hello")
    public String hello() {
        System.out.println("接口被调用：hello() ");
        return "hello filter";
    }

    @GetMapping("hi")
    public String hi() {
        System.out.println("接口被调用：hi()");
        return "hi filter";
    }
}
复制代码
```

启动项目，可以看到我们的过滤器已经随着程序的启动被成功初始化了



分别对这两个接口发送请求：

![img](https://gitee.com/nobodylesszb/upic/raw/pic/upload/pics/1588822887-1-20200507114127816.jpg)



最后使项目停止运行，则过滤器随之销毁

![img](https://gitee.com/nobodylesszb/upic/raw/pic/upload/pics/1588822894-1-20200507114134042.jpg)



### 什么是 Filter 的链式调用？

当我们配置了多个 filter，且一个请求能够被多次拦截时，该请求将沿着 `客户端 -> 过滤器1 -> 过滤器2 -> servlet -> 过滤器2 -> 过滤器1 -> 客户端` 链式流转，如下图所示 ：

![img](https://gitee.com/nobodylesszb/upic/raw/pic/upload/pics/1588822866-1-20200507114106874.jpg)



以上面的代码为例，由于我们只定义了一个过滤器，在执行到 `filterChain.doFilter(servletRequest, servletResponse);` 的时候，请求就会被直接转送到 servlet 中进行调用。

所以我们需要稍微给它改造一下，看看再添加一个 DemoFilter2 会发生什么：

```java
package com.demo.demofilter.demofilter.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@Order(2)   // 过滤顺序，值越小越先执行，值相同或不指定时按filterName排序
// 注意这里的urlPatterns要与前面保持一致
@WebFilter(urlPatterns = "/demoFilter", filterName = "filterTest2") 
public class DemoFilter2 implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("filter2初始化中...");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        System.out.println("\ndoFilter2()开始执行：发往 " + ((HttpServletRequest) servletRequest).getRequestURL().toString() + " 的请求已被拦截");

        System.out.println("检验接口是否被调用，尝试获取contentType如下： " + servletResponse.getContentType());
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("检验接口是否被调用，尝试获取contentType如下： " + servletResponse.getContentType());

        System.out.println("doFilter2()执行结束。\n");
    }

    @Override
    public void destroy() {
        System.out.println("filter2销毁中...");
    }
}
```

运行结果如下：

![img](https://gitee.com/nobodylesszb/upic/raw/pic/upload/pics/1588822851-1-20200507114051522.jpg)



可以看出，当请求同时满足多个过滤器的过滤条件时，`filterChain.doFilter()` 会将其按一定顺序（可以通过 @Order 指定）依次传递到下一个 filter，直到进入 servlet 进行接口的实际调用。调用完成后，响应结果将沿着原路返回，并在再一次经过各个 filter 后，最终抵达客户端。

## 拦截器 Interceptor

### 什么是拦截器？

拦截器是 AOP 的一种实现策略，用于在某个方法或字段被访问前对它进行拦截，然后在其之前或之后加上某些操作。同 filter 一样，interceptor 也是链式调用。每个 interceptor 的调用会依据它的声明顺序依次执行。一般来说拦截器可以用于以下方面 ：

- 日志记录 ：几率请求信息的日志，以便进行信息监控、信息统计等等
- 权限检查 ：对用户的访问权限，认证，或授权等进行检查
- 性能监控 ：通过拦截器在进入处理器前后分别记录开始时间和结束时间，从而得到请求的处理时间
- 通用行为 ：读取 cookie 得到用户信息并将用户对象放入请求头中，从而方便后续流程使用

### 拦截器的接口方法有哪些？

**preHandler：**

方法的前置处理，将在请求处理之前被调用。一般用它来对方法进行一些前置初始化操作，或是对当前请求做一些预处理；此外也可以用来进行权限校验之类的判断，来决定请求是否要继续进行下去。

该方法返回一个布尔值，若该值为 false，则请求结束，后续的 Interceptor 和 Controller 都不会再执行；若该值为 true，则会继续调用下一个 Interceptor 的 preHandler() 方法，如果已经到达最后一个 interceptor 了，就会调用当前请求的 Controller。

**postHandler：**

方法的后置处理，将在请求处理之后被调用。虽然是在 Controller  方法调用后再执行，但它的调用依然在 DispatcherServlet  进行视图渲染并返回之前，所以一般可以通过它对 Controller 处理之后的 ModelAndView 对象进行操作。

**afterCompletion：**

在整个请求处理完成（包括视图渲染）后执行，主要用来进行一些资源的清理工作。

### 如何实现一个自己的拦截器

同样，首先创建一个类，让它实现 HandlerInterceptor 接口，然后重写接口中的方法 ：

```java
package com.demo.demofilter.demofilter.interceptor;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class DemoInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandle");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        System.out.println("afterHandle");
    }
}
复制代码
```

紧接着需要对拦截器进行注册，指明使用哪个拦截器，及该拦截器对应拦截的 URL ：

```java
package com.demo.demofilter.demofilter.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //&emsp;如果有多个拦截器，继续registry.add往下添加就可以啦
        registry.addInterceptor(new DemoInterceptor()).addPathPatterns("/demoInterceptor/**");
    }

}
复制代码
```

最后是 Controller

```java
package com.demo.demofilter.demofilter.interceptor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("demoInterceptor")
public class InterceptorController {

    @GetMapping("hello")
    public String hello() {
        System.out.println("接口被调用：hello() ");
        return "hello interceptor";
    }

    @GetMapping("hi")
    public String hi() {
        System.out.println("接口被调用：hi()");
        return "hi interceptor";
    }
}
复制代码
```

运行结果如下 ：

![img](https://gitee.com/nobodylesszb/upic/raw/pic/upload/pics/1588822822-1-20200507114022279.jpg)

### 其他注意事项

在 Http 的请求执行过程中，要经过以下几个步骤 ：

1. 由 DispatcherServlet 捕获请求
2. DispatcherServlet 将接收到的 URL 和对应的 Controller 进行映射
3. 在请求到达相应的 Controller 之前，由拦截器对请求进行处理
4. 处理完成之后，进行视图的解析
5. 返回视图

所以，只有经过 DispatcherServlet 的请求才会被拦截器捕获，而我们自定义的 Servlet 请求则不会被拦截的。

## 过滤器与拦截器

作为AOP思想的两种典型实现，过滤器与拦截器有着许多相似的地方。而两者最大的区别在于 ：**过滤器是在 Servlet 规范中定义的，是由 Servlet 容器支持的；拦截器是在 Spring 容器内的，由 Spring 框架支持。**

因此，作为 Spring 的一个组件，拦截器可以通过IOC容器进行管理，获取其中的各个 bean 实例，对 spring 中的各种资源、对象，如 Service 对象、数据源、事务管理等进行调用；而过滤器则不能。

总的来说，两者主要在如下方面存在着差异 ：

- 过滤器是基于函数的回调，而拦截器是基于 Java 反射机制的
- 过滤器可以修改 request，而拦截器则不能
- 过滤器需要在 servlet 容器中实现，拦截器可以适用于 JavaEE、JavaSE 等各种环境
- 拦截器可以调用 IOC 容器中的各种依赖，而过滤器不能
- 过滤器只能在请求的前后使用，而拦截器可以详细到每个方法

最后补两张图嘿嘿 ：

1. filter、servlet、interceptor 触发时机

   ![img](https://gitee.com/nobodylesszb/upic/raw/pic/upload/pics/1588822801-1-20200507114001598.jpg)

   过滤器的触发时机是在容器后，servlet 之前，所以过滤器的 

   ```
   doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
   ```

    的入参是 ServletRequest，而不是 HttpServletRequest，因为过滤器是在 HttpServlet 之前。

   

2. 过滤器拦截器运行先后步骤

   ![img](https://gitee.com/nobodylesszb/upic/raw/pic/upload/pics/1588822772-1.jpg)

   第二步中，SpringMVC 的机制是由 DispaterServlet 来分发请求给不同的 Controller，其实这一步是在 Servlet 的 service() 方法中执行的。


。