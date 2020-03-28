package read.write.talk.beyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableRetry
@ServletComponentScan
@EnableHystrixDashboard
@EnableHystrix
@EnableCircuitBreaker
public class BeYouApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeYouApplication.class, args);
	}

	@RequestMapping("/")
	public String hello(){
		return"Hello world!";
	}

}
