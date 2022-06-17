## 一 、基础知识
### 1、Dao层  
持久层，主要和数据库进行交互，创建DAO接口，然后会在配置文件中定义该类接口的实现类
### 2、service层
业务层，控制业务；和dao层一样，先设计接口，然后再设计实现类；主要负责业务模块的逻辑应用设计
### 3、controller层
主要调service层的接口，控制具体的业务流程
### 4、查找spring版本的文档
``` 
https://docs.spring.io/spring-framework/docs/5.2.1.RELEASE/spring-framework-reference/
```
### 5、spring AOP
#### 代理模式
- 静态代理
    - 先创建接口--把具体需要的功能列出来
    - 确定真实的对象
    - 找代理对象--代理对象可以自己定义功能需求，增加额外的附属条件，比如说中介费
    - 客户--找代理（创建真实的对象，扔到代理里面）
### 6、使用set和get（其实也就是封装，把属性封装起来，只提供对外访问的接口）
- 由于属性被私有化了，不能直接访问，所以需要set或者get
- set属性（属性首字母要大写）
```aidl
    private UserService userservice;//属性、变量类型
    public void setUserService(UserService userservice) {//setUserService大写
        this.userservice = userservice;
    }
```
```aidl
//私有属性，也可以设置有参的构造方法来进行传递对象
    private UserService userservice;//属性、变量类型
    public UserProxyService(UserService userservice) {
            this.userservice = userservice;
        }
```

### 7、动态代理类模板
```aidl
//代理调用处理程序，使用这个类自动生成代理类
public class ProxyInvocationHandler implements InvocationHandler {
    //1、被代理的接口Object
    private Object target;
    public void setTarget(Object target) {
        this.target = target;
    }

    //2、生成得到代理类Object
    public Object getProxy(){
        return Proxy.newProxyInstance(this.getClass().getClassLoader(),target.getClass().getInterfaces(),this);
    }

    //3、使用这个代理类，必须要实现的接口，来处理这个代理类
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //使用反射机制实现
        Object result = method.invoke(target, args); //introduce variable
        return result;
    }
}
```
### 8、使用动态代理的步骤
```aidl
        //1、创建真实角色
        UserService userService = new UserService();
        //2、代理角色--不存在（动态生成）
        ProxyInvocationHandler pih = new ProxyInvocationHandler();
        //3、设置要代理的真实对象
        pih.setTarget(userService);
        //4、动态生成代理类--接口对象
        UserServiceImpl proxy =(UserServiceImpl) pih.getProxy();
        //5、这里调用真实对象里面的方法执行，由代理对象去执行的，真实对象不用关心
        proxy.del();
```
### 9、一个代理可以代理多个真实的对象
```aidl
//代理了2个真实的对象： userService、userServiceTwo 
public static void main(String[] args) {
        //真实角色
        UserService userService = new UserService();
        UserServiceTwo userServiceTwo = new UserServiceTwo();
        //代理角色--不存在
        ProxyInvocationHandler pih = new ProxyInvocationHandler();
        //设置要代理的真实对象
        pih.setTarget(userService);
        pih.setTarget(userServiceTwo);
        //动态生成代理类--接口对象
        UserServiceImpl proxy =(UserServiceImpl) pih.getProxy();
        //这里调用真实对象里面的方法执行，由代理对象去执行的，真实对象不用关心
        proxy.del();//console输出：使用了del方法,删除一个用户
```
```aidl
        //控制台输出
          使用了del方法
          房东2减少一个用户
```

### 10、实现AOP的三种方式
- 一、原生spring方式
```aidl
   <!--配置aop-->
    <aop:config>
        <!--切入点（可以配置多个切入点）,表达式：execution(要执行的位置！)-->
        <!--.. 代表有任意的参数   * 代表所有 -->
        <aop:pointcut id="pointcut" expression="execution(* com.jinli.service.UserServiceImpl.*(..))"/>

        <!--执行环绕增加,执行哪个类，切入到哪里-->
        <aop:advisor advice-ref="log" pointcut-ref="pointcut"/>
        <aop:advisor advice-ref="afterLog" pointcut-ref="pointcut"/>
    </aop:config>
```
- 二、使用自定义类（自定义切面aspect)
  - execution(* com.jinli.diy.MyDefine. *(..)) //表达式所有类下的所有方法
```aidl
//class MyDefine
public class MyDefine {
    public void After(){
        System.out.println("-----------方法执行后----------");
    }
    public void Before(){
        System.out.println("-----------方法执行前------------");
    }
}
//--------------------------------------------------------------------------
//xml
<aop:config>
        <!--使用切面的方式实现，自定义切面(aspect)，ref：要引用的类-->
        <aop:aspect ref="MyDefine">
            <!--1、切入点-->
            <aop:pointcut id="point" expression="execution(* com.jinli.diy.MyDefine. *(..))"/>
            <!--2、通知-->
            <aop:before method="Before" pointcut-ref="pointcut"/>
            <aop:after method="After" pointcut-ref="pointcut"/>
        </aop:aspect>
    </aop:config>
```
- 切面的一些理解：
  - before : 在切入点之前执行某个方法
  - after ：在切入点之后执行某个方法
  
xml：
```aidl
      <aop:before method="Before" pointcut-ref="point"/>
      <aop:after method="test"  pointcut-ref="point"/>
      <aop:after method="After" pointcut-ref="point"/>
```
console：
```aidl
        -----------方法执行前------------
        增加了一个user
        -----------方法执行测试----------
        -----------方法执行后----------
```

## 二 、TipS
### 1、常用Archetype,
- maven-archetype-quickstart
- maven-archetype-webapp  //项目模板

### 2、常用spring依赖库
- spring-webmvc
```
<dependencies>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>5.2.1.RELEASE</version>
        </dependency>
    </dependencies>
```

```
 <!--照着改，eg：context，aop-->
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       https://www.springframework.org/schema/beans/spring-beans.xsd">
```
### 3、idea使用技巧
- ctrl+ o  构造方法重载
- ctrl + l 实现
- 按住ctrl,点进去包名，可以看到包的接口和类
- 编辑xml文件时: 注释：CTRL + SHIFT + / ; 取消注释：CTRL + SHIFT + \

## 三、question: 
### 1、如果没有依赖，尝试右键刷新
![imgs](/imgs/img01.png)


### 2、java: 错误: 不支持发行版本 17/5  ----推荐使用jdk8
jdk18会出现中文乱码问题，不要使用，jdk17配置的时候出现：不支持发行版本 17，最好也使用jdk8；

步骤：改成jdk8
![img.png](imgs/img_1.png)



![img.png](imgs/img.png)


### 3、报错slf4j找不到或者版本问题，尝试添加以下依赖
```aidl
<dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>1.7.25</version>
        </dependency>

        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-log4j12</artifactId>
            <version>1.7.25</version>
        </dependency>
```
