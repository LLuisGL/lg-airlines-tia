package com.lgoncalves.booking_service.clients;

import com.lgoncalves.booking_service.dtos.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${feign.user.info.id}", url = "${feign.user.info.url}")
public interface IUserREST {

    @GetMapping("/id/{id}")
    ResponseEntity<UserDTO> getById(@PathVariable String id);

}