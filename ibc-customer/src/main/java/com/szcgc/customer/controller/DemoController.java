package com.szcgc.customer.controller;

import com.szcgc.customer.service.ICustomerService;
import com.szcgc.customer.service.IGroupItemService;
import com.szcgc.third.tyc.feign.ITycClient;
import com.szcgc.third.tyc.model.gs.GsBaseDto;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @Author liaohong
 * @create 2022/9/14 15:47
 */
@Api(tags = "CustomerTest")
@RequestMapping("demo")
@RestController
public class DemoController {

    @Autowired
    ICustomerService customerService;

    @Autowired
    IGroupItemService groupItemService;

    @Operation(summary = "test1")
    @GetMapping("debug/isok")
    public String isOk() {
        return "CustomerApplication is ok " + LocalDateTime.now();
    }

    @Operation(summary = "test2")
    @GetMapping("debug/{id}")
    public String testId(@PathVariable int id) {
        return "CustomerApplication TestId " + id;
    }

    @Operation(summary = "testCustomer")
    @GetMapping("name/{id}")
    public String testQueryId(@PathVariable int id) {
        return "CustomerApplication Query-Name " + id + ":" + customerService.findName(id);
    }

    @Operation(summary = "testGroupItem")
    @GetMapping("group/{id}")
    public String testGroupId(@PathVariable int id) {
        return "CustomerApplication Query-Group " + id + ":" + groupItemService.find(id).get();
    }

//    @Operation(summary = "testQuery")
//    @GetMapping("query-demo-3/{id}")
//    public String testUrl(@PathVariable int id) {
//        return "CustomerApplication query-demo-3 " + id ;
//    }

    @Operation(summary = "testQuery2")
    @GetMapping("{name}/{path}")
    public String testUrl(@PathVariable String name, @PathVariable String path) {
        return "CustomerApplication two url " + name + path;
    }

    @Operation(summary = "testQuery3")
    @GetMapping("{name}/{path}/{url}")
    public String testUrl(@PathVariable String name, @PathVariable String path, @PathVariable String url) {
        return "CustomerApplication third url " + name + path + url;
    }

    @Autowired
    ITycClient tycClient;

    @Operation(summary = "testTyc")
    @GetMapping("/tyc/gs")
    public String testUrl() {
        GsBaseDto info = tycClient.gs("北京豆网科技有限公司");
        return "CustomerApplication tyc gs " + info;
    }

}
