## 一 服务治理 Eureka（一）
code:eureka-server
### 1.1 什么是服务治理
服务治理是微服务架构最核心最基础的模块，主要用来实现**各种微服务实例的自动化注册和实现**。
### 1.2 服务治理组成
1. 服务注册：
    * 服务治理框架中有一个**服务注册中心**(通常为集群），每个服务单元向注册中心登记自己的**服务名**，形成一个服务清单。服务注册中心还会以心跳的方式检测注册清单中的服务是否可用，若不能用则清除。
2. 服务发现：
    * 服务间的调用通过向**服务名**发起请求调用实现，**调用方**（consumer）在调用服务**提供方**（provider)的接口的时候，并不知道服务接口的具体位置。
3. 三个组件：
   * 服务注册中心  Eureka server
   * 服务提供方    Eureka client/provider
   * 服务调用方    Consumer

### 2.1 服务治理实现 Netflix Eureka
Spring Cloud Eureka 是通过Netflix Eureka实现服务治理和发现的，既包含服务端组件和客户端组件。
### 2.2 搭建服务注册中心 Eureka server

#### 2.2.1 pom.xml
```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<groupId>com.bean</groupId>
	<artifactId>eureka-server</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>jar</packaging>

	<name>eureka-server</name>
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
			<artifactId>spring-cloud-starter-eureka-server</artifactId>
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

#### 2.2.2 application.properties
```
server.port=1111

eureka.instance.hostname=localhost
#注册中心不需要检索服务
eureka.client.fetch-registry=false
#注册中心不需要注册自己
eureka.client.register-with-eureka=false

eureka.client.serviceUrl.defaultZone=http://${eureka.instance.hostname}:${server.port}/eureka/
```

#### 2.2.3 启动类 
**关键注释：@EnableEurekaServer**
```
package com.bean;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaServerApplication.class, args);
	}
}
```

项目启动访问http://localhost:1111/
,即为Eureka Server（注册中心）<br>
Application表为服务清单。