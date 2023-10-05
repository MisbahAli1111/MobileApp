package com.qavi.carmaintanence.usermanagement.services.user;

import com.qavi.carmaintanence.globalexceptions.InvalidTokenException;
import com.qavi.carmaintanence.globalexceptions.RecordAlreadyExists;
import com.qavi.carmaintanence.usermanagement.constants.UserConstants;
import com.qavi.carmaintanence.usermanagement.entities.user.User;
import com.qavi.carmaintanence.usermanagement.models.GoogleResponseModel;
import com.qavi.carmaintanence.usermanagement.models.UserDataModel;
import com.qavi.carmaintanence.usermanagement.repositories.UserRepository;
import com.qavi.carmaintanence.usermanagement.utils.JWTGenerator;
import okhttp3.OkHttpClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Optional;

@Service
public class GoogleSignInService {

    private final OkHttpClient apiClient;
    private final UserService userService;
    private final JWTGenerator jwtGenerator;

    private final UserRepository appUserRepository;


    public GoogleSignInService(UserService userService, JWTGenerator jwtGenerator, UserRepository appUserRepository) {

        this.userService = userService;
        this.jwtGenerator = jwtGenerator;
        this.appUserRepository = appUserRepository;
        this.apiClient = new OkHttpClient.Builder()
                .build();
    }


    public String SignIn(UserDataModel userDataModel) {


        Optional<User> user = userService.findUserByEmail(userDataModel.getEmail());




        String accessToken = "";

        if (user.isPresent()) {
            var google =user.get().getAuthType().equals("GOOGLE");

            if(google){
                accessToken = jwtGenerator.generateJWTToken(user.get());
            } else{
                accessToken="The Provided email is not associated with Google sign up, Please provide password";
            }
        } else {

            // Create and save a new technician in the database
            User newUser = new User();
            newUser.setFirstName(userDataModel.getFirstName()); // Use values from userDataModel
            newUser.setLastName(userDataModel.getLastName());   // Use values from userDataModel
            newUser.setEnabled(true);
            newUser.setEmail(userDataModel.getEmail());        // Use values from userDataModel
            newUser.setAuthType(UserConstants.GOOGLE);
            newUser.setEmailNotificationEnabled(false);

            // Save the new user to the database
            appUserRepository.save(newUser);

            accessToken = jwtGenerator.generateJWTToken(newUser);
        }

        return accessToken;
    }

}
