## 高可用注册中心
code:eureka-server1,eureka-server2,eureka-client-hello
### 1.1 服务中心的高可用
* Eureka Server的高可用实际上就是将自己作为服务向其他注册中心注册自己，形成相互注册的服务中心，以实现服务清单互相同步，达到高可用的效果。

### 1.2 设计思想
* 在Eureka的服务智力设计中，所有节点都是服务提供方，也是服务消费者，服务注册中心也不例外

### 2.1 高可用实现 
#### 2.1.1 application.properties配置
```
#开启注册自己
eureka.client.register-with-eureka=true
#开启检索服务
eureka.client.fetch-registry=true

```
其他配置如 **1 服务治理 Eureka Server（一）**

#### 2.1.2 两节点案例
* 节点：localhost1,可写ip
```
spring.application.name=eureka-server
server.port=1111   

#开启注册自己
eureka.client.register-with-eureka=true
#开启检索服务
eureka.client.fetch-registry=true

eureka.instance.hostname=localhost1
eureka.client.serviceUrl.defaultZone=http://localhost2:1112/eureka/


```




* 节点 ：localhost2，可写ip
```
spring.application.name=eureka-server
server.port=1112

#开启注册自己
eureka.client.register-with-eureka=true
#开启检索服务
eureka.client.fetch-registry=true

eureka.instance.hostname=localhost2
eureka.client.serviceUrl.defaultZone=http://localhost1:1111/eureka/


```
**两个节点都分别向对方注册自己**



### 2.2 客户端配置
Eureka Client节点就要将所有的注册中心都写入配置文件
```
server.port=2001

#应用(服务)名称
spring.application.name=hello-service

eureka.client.serviceUrl.defaultZone=http://localhost1:1111/eureka/,http://localhost2:1112/eureka/


```











