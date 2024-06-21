import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description:
 * @author: ${author}
 * @date: ${date}
 */
@SpringBootApplication
@MapperScan("com.tj.cloud")
public class DynamicApplication {
    public static void main(String[] args) {
        SpringApplication.run(DynamicApplication.class);
    }
}
