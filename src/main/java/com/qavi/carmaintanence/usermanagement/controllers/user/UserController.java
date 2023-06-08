package com.qavi.carmaintanence.usermanagement.controllers.user;

import com.qavi.carmaintanence.usermanagement.entities.user.User;
import com.qavi.carmaintanence.usermanagement.models.ResponseModel;
import com.qavi.carmaintanence.usermanagement.models.UserDataModel;
import com.qavi.carmaintanence.usermanagement.services.user.UserService;
import com.qavi.carmaintanence.usermanagement.utils.ConverterModels;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {


    @Autowired
    private UserService userService;


    //Get All Users
    @GetMapping
    public ResponseEntity<List<UserDataModel>> getAllUser() {
        List<User> users = userService.getAllUsers();
        List<UserDataModel> convertedList = new ArrayList<>();
        for (User user : users) {
            convertedList.add(ConverterModels.convertUserToUserDataModel(user));
        }
        return new ResponseEntity<List<UserDataModel>>(convertedList, HttpStatus.OK);
    }


    //Get User By Id
    @GetMapping("/{id}")
    public ResponseEntity<UserDataModel> getUserById(@PathVariable Long id) {
        User user = userService.getUser(id);
        UserDataModel userDataModel = ConverterModels.convertUserToUserDataModel(user);
        return new ResponseEntity<UserDataModel>(userDataModel, HttpStatus.OK);
    }

    //PostUser

    @PostMapping("/register/{type}")
    public ResponseEntity<ResponseModel> createUser(@RequestBody User user,@PathVariable String type) {
        ResponseModel responseModel = ResponseModel.builder()
                .status(HttpStatus.OK)
                .message("User created successfully")
                .data(new Object())
                .build();
        if(!userService.createUser(user,type))
        {
            responseModel.setStatus(HttpStatus.EXPECTATION_FAILED);
            responseModel.setMessage("Failed to create user");
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }

    //Update User
    @PutMapping("/update-user/{id}")
    public ResponseEntity<ResponseModel> updateUser(@RequestBody UserDataModel userDataModel, @PathVariable Long id) {
        ResponseModel responseModel = ResponseModel.builder()
                .status(HttpStatus.OK)
                .message("User updated successfully")
                .data(new Object())
                .build();
        if (!userService.updateUser(userDataModel, id)) {
            responseModel.setMessage("Failed to update user");
            responseModel.setStatus(HttpStatus.EXPECTATION_FAILED);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }

    // Delete User

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable Long id) {
        Boolean deletedUser = userService.deleteUser(id);
        return new ResponseEntity<>(deletedUser, HttpStatus.OK);
    }


}
