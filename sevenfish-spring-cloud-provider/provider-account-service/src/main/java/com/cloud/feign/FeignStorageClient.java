package com.cloud.feign;

import com.cloud.entity.Storage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "provider-storage-service")
public interface FeignStorageClient {

    @RequestMapping("findByCommodityCode")
    Storage findByCommodityCode(@RequestParam("commodityCode") String commodityCode);

    @RequestMapping("changeCount")
    void changeCount(@RequestParam("id") Long id, @RequestParam("num") Integer num);
}
