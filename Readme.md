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
### 10 AOP的作用
AOP是一种思想，是一种横向编程的思想，在不影响我们原来业务类的情况下，实现动态的增强。
### 11、实现AOP的三种方式
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
- 二、使用自定义类（自定义切面aspect)----比较推荐，就是要定义多个切入点
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
- 三、使用注解的方式@Aspect
   - 开启注解支持
```aidl
    <bean id = "AnnotationPointContext" class="com.jinli.diy.AnnotationPointContext"/>
    <!--开启注解支持--  注解支持方式：JDK（默认false） cglib（true）>
    <aop:aspectj-autoproxy/>
```
<aop:aspectj-autoproxy  后面有个参数，几乎不用/>
![imgs](/imgs/img_2.png)

```aidl
@Aspect  //切面注解是写在类上面的
public class AnnotationPointContext {

    @Before("execution(* com.jinli.service.UserServiceImpl.*(..))") //增加切入点
    public void Before(){
        System.out.println("-----------方法执行前------------");
    }
    @After("execution(* com.jinli.service.UserServiceImpl.*(..))")
    public void after(){
        System.out.println("-----------方法执行后----------");
    }
    @Around("execution(* com.jinli.service.UserServiceImpl.*(..))")
    public void around(ProceedingJoinPoint jp) throws Throwable {
        System.out.println("----环绕前-----");

        Signature signature = jp.getSignature();//获得签名：void com.jinli.service.UserService.add()
        System.out.println("signature： "+ signature);

        //执行方法
        Object proceed = jp.proceed();

        System.out.println("----环绕后-----");
    }

```

### 12、为什么要使用MyBatis--优秀的持久层框架
- 因为数据要做到持久化，内存断电即失，并且内存非常贵，mybatis完成持久化工作的代码块
service层调dao层，service层等待着controller层调用
- 帮助程序员更好的将数据存到数据库里面
- 传统的jdbc太过于复杂，需要框架来进行持久化
- 编写程序的逻辑
     - 搭建环境--->编写mybatis配置文件--->编写代码--->测试
- ```create database 'mybatis' == create database mybatis;// tab键上面的键```
- ``` show databases;```
- ```use databasename;```
- ```
  //1、查看所有数据库
  mysql> show databases;
  +--------------------+
  | Database           |
  +--------------------+
  | information_schema |
  | mybatis            |
  | mysql              |
  | performance_schema |
  | sys                |
  +--------------------+
  5 rows in set (0.00 sec)
  
  //2、使用某个数据库
  mysql> use mybatis;
  Database changed

  //3、建表语句，主键primary key 、 engine=innodb default charset =utf8
  mysql> create table `user`(
  -> `id` int(10) not null,
  -> `name` varchar(30) default null,
  -> `pwd` varchar( 30) default null,
  -> primary key(`id`) //这里没有逗号
  -> )engine=innodb default charset=utf8;
  Query OK, 0 rows affected (0.04 sec)
  
  //4、显示某个数据库的所有表
  mysql> show tables;
  +-------------------+
  | Tables_in_mybatis |
  +-------------------+
  | user              |
  +-------------------+
  1 row in set (0.00 sec)
  
  //5、查看表里面的具体数据
  mysql> select * from user;
  +----+-------+------+
  | id | name  | pwd  |
  +----+-------+------+
  |  1 | Jinli | 123  |
  |  2 | lvpl  | 234  |
  +----+-------+------+
  2 rows in set (0.02 sec)
  
  //6、查看表的数据结构
  mysql> desc user;
  +-------+-------------+------+-----+---------+-------+
  | Field | Type        | Null | Key | Default | Extra |
  +-------+-------------+------+-----+---------+-------+
  | id    | int(10)     | NO   | PRI | NULL    |       |
  | name  | varchar(30) | YES  |     | NULL    |       |
  | pwd   | varchar(30) | YES  |     | NULL    |       |
  +-------+-------------+------+-----+---------+-------+
  3 rows in set (0.00 sec)
  ```
### 13、mybatis配置文件
```aidl
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.jdbc.Driver"/> //&amp转义字符
                <property name="url" value="jdbc:mysql://localhost:3306/mybatis?useSSL=true&amp;useUnicode=true&amp;characterEncoding=utf8"/>
                <property name="username" value="root"/>
                <property name="password" value="jinli666"/>
            </dataSource>
        </environment>
    </environments>

</configuration>
```

### 13、注解
Annotation：不是程序本身，但是可以对程序做出解释，可以被其他程序读取（通过反射去读取），注解还有检查跟约束的作用
1、@Override 重写的注解
2、Thread类继承Runnable接口：有个@Functio nalInterface注解
3、@Deprecated 已废弃的
4、@SupperWarnings(value="unchecked")  //注解还可以添加参数all，参数类型、参数名    
5、注解可以在package，calss，method、field上使用，可以通过反射机制来实现对元数据的访问
- 内置注解
   - @Override：一定要去重写父类的方法
   - @Deprecated
   - @SupperWarnings
- 元注解
   - 负责解释其他注解
   - @target：用于描述注解的使用范围
   - @Retention:需要在什么级别保存该注释的信息runtime>class>sources
   - @Document
   - @Inherited：说明子类可以继承父类的注解
     - 自定义注解-不能有2个public修饰的注解
        - ```
          @Target(value=ElementType.METHOD) //注解只能在方法上使用
          //注解可以显示赋值
           public @interface myAnnotation{
              String name() default "";//参数类型+参数名
              int age() default 0;
              int id() default -1; //代表不存在
              String school() default {"清华大学"，"北京大学"};
          }
          ```
### 14、反射---通过对象去反射类
  - 1、java，C,C++是静态语言，就是因为反射机制的存在，程序在运行的时候还能跑进去加一些hock，改变它运行时里面的数据
      - 动态语言：在程序运行的时候可以改变变量的值
  - 2、反射机制概述
      - 反射机制允许程序在执行期借助于reflection api取得任何类的内部信息，并能操作任意对象的内部属性及方法
        - ```Class c = Class.forName("java.lang.String") //反射可以直接读取private修饰的属性```
  - 3、理解class类并获取class实例
    - 方法1、已知具体的类，通过类.class()获取
    - 方法2、已知到某个类的实例，通过类.getClass()获取
    - 方法3、已知一个类的全名，且该类在类的路径下，可通过Class.forName("该类的类名")获取
    - 方法4、内置的基本数据类型可以直接用类名.Type
    - 方法5、使用ClassLoader获取
    - 题外--------------------------->哪些类型可以有Class对象
        
  - 4、类加载和classloder
      - 加载完类之后，在堆内存的方法区中就会产生一个Class类型的对象，这个对象（就像一面镜子）包含了完整的类的结构的信息，我们可以通过这个对象看到类的结构，所以我们形象的称之为反射

  - 5、创建运行时类的对象
  - 6、获取运行时类的完整结构
  - 7、调用运行时类的指定结构
  - 优缺点
      - 优点：可以实现动态的创建对象和编译，体现很大的灵活性
      - 缺点：对性能有影响，反射相当于一种解释操作，他会告诉虚拟机，我们希望他做什么并且他去满足我们的要求，这种加了一层解释会比直接new，直接执行我们的操作慢很多倍
  - 反射：Class，Method，Field，Constructor
  
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
- ctrl+/      这个是多行代码分行注释，每行一个注释符号 
- ctrl+shift+/    这个是多行代码注释在一个块里，只在开头和结尾有注释符号
- 取消多行注释快捷键,和添加快捷键的方法一样

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
### 4 、Error: could not open `D:\software\jdk8\jdk1.8\lib\amd64\jvm.cfg
```
1、%JAVA_HOME%\bin;%JAVA_HOME%\jre\bin;//移到Path环境变量的最前面
2、删掉该目录下 C:\Program Files\Common Files\Oracle\Java\javapath的除javac的其余3个文件

```