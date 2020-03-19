package pers.jiangyinzuo.carbon;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import pers.jiangyinzuo.carbon.service.rpc.producer.CreditDropService;
import pers.jiangyinzuo.carbon.service.rpc.producer.impl.CreditDropServiceImpl;

import java.util.concurrent.CountDownLatch;

public class Producer {
    public static void main(String[] args) throws InterruptedException {
        // 服务实现
        CreditDropService xxxService = new CreditDropServiceImpl();

// 当前应用配置
        ApplicationConfig application = new ApplicationConfig();
        application.setName("xxx");

// 连接注册中心配置
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("zookeeper://127.0.0.1:2181");
        registry.setUsername("aaa");
        registry.setPassword("bbb");

// 服务提供者协议配置
        ProtocolConfig protocol = new ProtocolConfig();
        protocol.setName("dubbo");
        protocol.setPort(12345);
        protocol.setThreads(200);

// 注意：ServiceConfig为重对象，内部封装了与注册中心的连接，以及开启服务端口

// 服务提供者暴露服务配置
        ServiceConfig<CreditDropService> service = new ServiceConfig<>(); // 此实例很重，封装了与注册中心的连接，请自行缓存，否则可能造成内存和连接泄漏
        service.setApplication(application);
        service.setRegistry(registry); // 多个注册中心可以用setRegistries()
        service.setProtocol(protocol); // 多个协议可以用setProtocols()
        service.setInterface(CreditDropService.class);
        service.setRef(xxxService);
        service.setVersion("1.0.0");
        System.out.println("hel");
// 暴露及注册服务
        service.export();
        System.out.println("done");
        new CountDownLatch(1).await();
    }
}
