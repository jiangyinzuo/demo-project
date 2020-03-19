package pers.jiangyinzuo.carbon;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import pers.jiangyinzuo.carbon.service.rpc.producer.CreditDropService;


/**
 * @author Jiang Yinzuo
 */
public class Client {

    public static void main(String[] args) {

        // 当前应用配置
        ApplicationConfig application = new ApplicationConfig();
        application.setName("client");

        // 连接注册中心配置
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("zookeeper://127.0.0.1:2181");

//        registry.setUsername("aaa");
//        registry.setPassword("bbb");
        // 引用远程服务
        ReferenceConfig<CreditDropService> reference = new ReferenceConfig<>(); // 此实例很重，封装了与注册中心的连接以及与提供者的连接，请自行缓存，否则可能造成内存和连接泄漏
        reference.setApplication(application);
        reference.setRegistry(registry); // 多个注册中心可以用setRegistries()
        reference.setInterface(CreditDropService.class);
        reference.setVersion("1.0.0");
        System.out.println("hel");
// 和本地bean一样使用xxxService
        CreditDropService xxxService = reference.get();
        xxxService.addCreditDrop(1L, 2.3);// 注意：此代理对象内部封装了所有通讯细节，对象较重，请缓存复用
    }
}
