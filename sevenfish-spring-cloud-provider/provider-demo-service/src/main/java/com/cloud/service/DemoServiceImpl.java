package com.cloud.service;

import com.cloud.config.db.DataSourceEnum;
import com.cloud.config.db.DataSourceType;
import com.cloud.dao.UserDao;
import com.cloud.entity.User;
import com.cloud.pojo.Product;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class DemoServiceImpl implements DemoService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${server.port}")
    private String port;

    @Autowired
    private UserDao userDao;

    @Resource
    private ThreadPoolExecutor enhanceExecutor;

    @HystrixCommand(
            fallbackMethod = "fallbackListProducts",
            threadPoolKey = "demoServiceThreadPool",
            threadPoolProperties = {
                    //线程数，默认10
                    @HystrixProperty(name="coreSize",value = "20"),
                    //阻塞队列，-1表示使用SynchronousQueue队列，大于1表示使用LinkedBlockingQueue队列，数量表示队列长度
                    @HystrixProperty(name="maxQueueSize",value = "30")
            },
            commandProperties = {
                    //配置断路器超时时间.默认为1s
                    @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value = "2000"),
            }
    )
    @Override
    public List<Product> listProducts() {
//        randomlyRun();

        List<Product> list = new ArrayList<>();
        list.add(new Product(1,"Product a from port:"+port, BigDecimal.ZERO));
        list.add(new Product(2,"Product b from port:"+port, BigDecimal.ONE));
        list.add(new Product(3,"Product c from port:"+port, BigDecimal.TEN));

        enhanceExecutor.execute(()->{
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(300,500));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        return list;
    }

    /**
     * 三分之一概率触发超时，Hystrix默认调用时间是1s
     */
    private void randomlyRun(){
        Random random = new Random();
        int randomNum = random.nextInt(3)+1;
        if (randomNum == 3){
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 后备处理
     * @return
     */
    private List<Product> fallbackListProducts(){
        List<Product> list = new ArrayList<>();
        list.add(new Product(-1,"异常商品，端口号："+port, BigDecimal.ZERO));
        return list;
    }

    @Override
    @DataSourceType(sourceType = DataSourceEnum.SLAVE)
    public List<User> listUsers() {
        return userDao.findAll();
    }

    @Override
    //MASTER可直接省略该注解，默认取MASTER
    @DataSourceType(sourceType = DataSourceEnum.MASTER)
    public void modifyUser(Long id,String name,Integer sex) {
        Optional<User> optional = userDao.findById(id);
        if (optional.isPresent()){
            User user = optional.get();
            user.setName(name);
            user.setSex(sex);
            userDao.save(user);
        }
    }
}
