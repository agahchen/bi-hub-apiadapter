package bcgov.bi.bihubadapter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"bcgov.bi.bihubadapter.controller"})
public class BihubadapterApplication {

	public static void main(String[] args) {
		SpringApplication.run(BihubadapterApplication.class, args);
	}

}
