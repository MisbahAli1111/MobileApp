package com.qavi.carmaintanence.imagevideo.controller;

import com.qavi.carmaintanence.business.entities.Business;
import com.qavi.carmaintanence.business.entities.MaintenanceRecordMedia;
import com.qavi.carmaintanence.business.services.*;
import com.qavi.carmaintanence.globalexceptions.InvalidFileException;
import com.qavi.carmaintanence.imagevideo.models.FileUploadResponse;
import com.qavi.carmaintanence.imagevideo.service.FileUploadService;
import com.qavi.carmaintanence.usermanagement.models.ResponseModel;
import com.qavi.carmaintanence.usermanagement.services.user.ProfileImageService;
import com.qavi.carmaintanence.usermanagement.services.user.UserService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/file")
public class FileUploadController {

    @Value("${upload.maxFileSize}")
    private int maxFileSize;
    @Autowired
    FileUploadService fileUploadService;

    @Autowired
    ProfileImageService profileImageService;

    @Autowired
    UserService userService;

    @Autowired
    VehicleMediaService vehicleMediaService;

    @Autowired
    MaintenanceRecordService maintenanceRecordService;

    @Autowired
    BusinessMediaService businessMediaService;

    @Autowired
    BusinessService businessService;

    @Autowired
    VehicleService vehicleService;

    @Autowired
    MaintenanceRecordMediaService maintenanceRecordMediaService;




    @PostMapping("/upload/{mediaof}/{id}")
    @Transactional
    public ResponseEntity<ResponseModel> uploadFiles (@RequestPart("files") MultipartFile [] files, @PathVariable String mediaof,@PathVariable String id) throws IOException {

        FileUploadResponse fileUploadResponse;

        String uploadedFileKey;
        Long uploadedFileId = null;
        System.out.println("Received mediaof: " + mediaof);


        ArrayList<Long> uploadedFilesKeys = new ArrayList<>();
        List<MaintenanceRecordMedia> uploadedFileIds = new ArrayList<>();
        ArrayList<String> allowedExt = new ArrayList<>(Arrays.asList("png","jpeg","jpg","mp4","mkv"));
        int count =0;
        for(MultipartFile file : files){
            String filename = file.getOriginalFilename();
            String fileExtension = FilenameUtils.getExtension(filename);

            if(allowedExt.contains(fileExtension)){

                uploadedFileKey = fileUploadService.uploadFile(file);
                if(mediaof.equalsIgnoreCase("vehicle")){ //add vehicle
                    uploadedFileId = vehicleMediaService.saveFileKey(uploadedFileKey);
                    vehicleService.saveVehicleImage(uploadedFileId,Long.valueOf(id));
                    uploadedFilesKeys.add(uploadedFileId);
                }
                else if (mediaof.equalsIgnoreCase("record")) {
                    uploadedFileId = maintenanceRecordMediaService.saveFileKey(uploadedFileKey);
                    maintenanceRecordService.saveRecordImage(uploadedFileId,Long.valueOf(id));
                    uploadedFilesKeys.add(uploadedFileId);
                }



                else if(mediaof.equalsIgnoreCase("profile")){
                    Long savedImgId = profileImageService.saveFileKey(uploadedFileKey);
                    userService.saveProfileImage(savedImgId,Long.valueOf(id));
                    uploadedFilesKeys.add(savedImgId);

                }
                else if(mediaof.equalsIgnoreCase("business"))
                {
                    uploadedFileId = businessMediaService.saveFileKey(uploadedFileKey);
                    businessService.saveProfileImage(uploadedFileId,Long.valueOf(id));
                    uploadedFilesKeys.add(uploadedFileId);
                }
                else{
                    throw new RuntimeException("request parameter not valid, must be 'service', 'job', or 'profile'");
                }
                count++;
            }
            else{
                if(!files[0].getOriginalFilename().equalsIgnoreCase("")){
                    throw new InvalidFileException("invalid file format, only jpeg/jpg , png formats allowed");
                }
            }
        }
        fileUploadResponse = FileUploadResponse.builder()
                .successfullyUploadedFileKeys(uploadedFilesKeys)
                .build();

        ResponseModel response = ResponseModel.builder().status(HttpStatus.OK)
                .message("file upload result")
                .data(fileUploadResponse).build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping("{filekey:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filekey) {
        Resource file = fileUploadService.loadByFileKey(filekey);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filekey=\"" + file.getFilename() + "\"")
                .body(file);
    }
}