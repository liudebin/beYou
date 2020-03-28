package read.write.talk.beyou.api;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import read.write.talk.beyou.data.model.AyUserDTO;
import read.write.talk.beyou.data.service.AyUserService;

import javax.annotation.Resource;
import java.util.List;

@RestController
@FeignClient
public class Hello {
//    @HystrixCommand(commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1500"),
//            // 滑动统计的桶数量
//            /**
//             * 设置一个rolling window被划分的数量，若numBuckets＝10，rolling window＝10000，
//             *那么一个bucket的时间即1秒。必须符合rolling window % numberBuckets == 0。默认1
//             */
//            @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "10"),
//            // 设置滑动窗口的统计时间。熔断器使用这个时间
//            /** 设置统计的时间窗口值的，毫秒值。
//             circuit break 的打开会根据1个rolling window的统计来计算。
//             若rolling window被设为10000毫秒，则rolling window会被分成n个buckets，
//             每个bucket包含success，failure，timeout，rejection的次数的统计信息。默认10000
//             **/
//            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000")},
//            threadPoolProperties = {
//                    @HystrixProperty(name = "coreSize", value = "15"),
//                    /**
//                     * BlockingQueue的最大队列数，当设为-1，会使用SynchronousQueue，值为正时使用LinkedBlcokingQueue。
//                     */
//                    @HystrixProperty(name = "maxQueueSize", value = "15"),
//                    /**
//                     * 设置存活时间，单位分钟。如果coreSize小于maximumSize，那么该属性控制一个线程从实用完成到被释放的时间.
//                     */
//                    @HystrixProperty(name = "keepAliveTimeMinutes", value = "2"),
//                    /**
//                     * 设置队列拒绝的阈值,即使maxQueueSize还没有达到
//                     */
//                    @HystrixProperty(name = "queueSizeRejectionThreshold", value = "15"),
//                    @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "10"),
//                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000")
//            })
//    @RequestMapping("/")
//    public String hello(){
//        return"Hello world!";
//    }

    @Resource
    private AyUserService ayUserService;

    @RequestMapping("/")
    public String hello(Model model) {
        List<AyUserDTO> ayUser = ayUserService.findAll();
        model.addAttribute("users", ayUser);
        return "ayUser";
    }

    @RequestMapping("testRetry")
    public String testRetry(){
        return"Hello world!";
    }

}
