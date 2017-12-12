package com.bean.controller;

import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.netflix.discovery.shared.Applications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Downey on 2017/12/12.
 */

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
