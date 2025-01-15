package org.teachease.userservice.controllers;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.teachease.userservice.dtos.UserInfoDTO;
import org.teachease.userservice.entity.UserInfo;
import org.teachease.userservice.services.UserService;

@RestController
@RequestMapping("/")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/my-details")
    public UserInfoDTO getMyDetails(HttpServletRequest request) {
        String userId = request.getHeader("X-User-ID");
        return userService.getUserInfoById(userId);
    }
    @GetMapping("/view/{id}")
    public MappingJacksonValue getUserInfoById(@RequestParam String id) {
    UserInfoDTO userInfoDTO = userService.getUserInfoById(id);
        SimpleFilterProvider filters = new SimpleFilterProvider();
        filters.addFilter("UserFilter",
                SimpleBeanPropertyFilter.filterOutAllExcept("id", "name")); // Specify the fields to include

        MappingJacksonValue mapping = new MappingJacksonValue(userInfoDTO);
        mapping.setFilters(filters);
    return mapping;
    }
    @PutMapping("/update")
    public UserInfoDTO updateUserInfo(HttpServletRequest request,@RequestBody UserInfoDTO userInfoDTO) {
        String userId = request.getHeader("X-User-ID");
        userInfoDTO.setUserId(userId);
        return userService.createOrUpdateUser(userInfoDTO);
    }

}
