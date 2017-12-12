## 服务治理 Eureka Client
### 1.1 服务注册实现
#### 1.1.1 pom.xml
```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<groupId>com.bean</groupId>
	<artifactId>eureka-client</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>jar</packaging>

	<name>eureka-client</name>
	<description>Demo project for Spring Boot</description>

	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>1.5.3.RELEASE</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>

	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
		<java.version>1.8</java.version>
		<spring-cloud.version>Edgware.RELEASE</spring-cloud.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-eureka</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>
	</dependencies>

	<dependencyManagement>
		<dependencies>
			<dependency>
				<groupId>org.springframework.cloud</groupId>
				<artifactId>spring-cloud-dependencies</artifactId>
				<version>${spring-cloud.version}</version>
				<type>pom</type>
				<scope>import</scope>
			</dependency>
		</dependencies>
	</dependencyManagement>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>


</project>


```
#### 1.1.2 启动类
**关键注释：@EnableDiscoveryClient**

```
package com.bean;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class EurekaClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaClientApplication.class, args);
	}
}
```

#### 1.1.3 控制器接口
这里并没有特殊配置，关键是启动类和配置文件
```
@RestController
public class Hello {

    @Autowired
    private EurekaClient client;

    @RequestMapping("/hello")
    public String hello(){

        Applications applications = client.getApplications();
        List<Application> apps = applications.getRegisteredApplications();
        System.out.println("注册服务数："+apps.size());
        for(int i=0;i<apps.size();i++){
            Application app = apps.get(i);
            System.out.println("服务名:"+app.getName());
        }

        return "hello spring";
    }


}


```
#### 1.1.4  application.properties
```
server.port=2222

#应用(服务)名称
spring.application.name=hello-service

#无需认证直接访问actuator 监控
management.security.enabled=false

#注册中心地址
eureka.client.serviceUrl.defaultZone=http://localhost:1111/eureka/

#自我保护模式关闭（生产不建议），
eureka.server.enableSelfPreservation=false

#renews 和renews threshold的比值，默认值为0.85,低于0.85进入保护模式
eureka.server.renewalPercentThreshold=0.5


#Eureka server和client之间每隔30秒会进行一次心跳通信，告诉server，client还活着。由此引出两个名词：
#Renews threshold：server期望在每分钟中收到的心跳次数
#Renews (last min)：上一分钟内收到的心跳次数。


#Eurake有一个配置参数eureka.server.renewalPercentThreshold，
#定义了renews 和renews threshold的比值，默认值为0.85。当server在15分钟内，
#比值低于percent，即少了15%的微服务心跳，server会进入自我保护状态，
#Self-Preservation。在此状态下，server不会删除注册信息，这就有可能导致在调用微服务时，实际上服务并不存在。
```