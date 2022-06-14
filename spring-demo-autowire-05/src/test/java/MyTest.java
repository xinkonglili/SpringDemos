import com.jinli.pojo.People;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.Test;

public class MyTest {
    @Test
    public void test1(){
      ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
      People people = context.getBean("people", People.class);
      people.getDog().shout();
      people.getCat().shout();
    }
}
