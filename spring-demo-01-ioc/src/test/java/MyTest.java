import com.jinli.service.UserServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    public static void main(String[] args) {
        //获取ApplicationContext:拿到spring容器  CPX快速输入
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        //拿到容器之后，需要什么，get什么
        UserServiceImpl useDaoServiceImpl = (UserServiceImpl) context.getBean("UserServiceImpl");
        useDaoServiceImpl.getUser();
    }
}
