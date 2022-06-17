import com.jinli.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    public static void main(String[] args) {
        //使用spring的方式，三部曲
        //1、获取ClassPathXmlApplicationContext，applicationContext.xml对象
       ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
       //2、context.getBean获取bean，动态代理，代理的是接口，bean注册的是类，所以需要强制转换
       UserService userService = (UserService) context.getBean("userService");
       //3、使用接口去调用类里面的方法
       userService.add();


    }
}
