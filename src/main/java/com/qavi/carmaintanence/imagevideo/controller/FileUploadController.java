package com.qavi.carmaintanence.imagevideo.controller;

import com.qavi.carmaintanence.business.entities.Business;
import com.qavi.carmaintanence.business.services.BusinessMediaService;
import com.qavi.carmaintanence.business.services.BusinessService;
import com.qavi.carmaintanence.business.services.MaintenanceRecordService;
import com.qavi.carmaintanence.business.services.VehicleMediaService;
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



    @PostMapping("/upload/{imageof}/{id}")
    @Transactional
    public ResponseEntity<ResponseModel> uploadFiles (@RequestPart("files") MultipartFile [] files, @PathVariable String imageof,@PathVariable String id) throws IOException {

        FileUploadResponse fileUploadResponse;

        String uploadedFileKey;
        Long uploadedFileId = null;

        ArrayList<Long> uploadedFilesKeys = new ArrayList<>();
        ArrayList<String> allowedExt = new ArrayList<>(Arrays.asList("png","jpeg","jpg"));
        int count =0;
        for(MultipartFile file : files){
            String filename = file.getOriginalFilename();
            String fileExtension = FilenameUtils.getExtension(filename);
            if((file.getSize()*0.00000095367432)>maxFileSize){
                throw new InvalidFileException("file size exceeded, max size allowed is "+ maxFileSize +"MB");
            }
            if(allowedExt.contains(fileExtension)){
                if(count>=6)
                {
                   count=0;
                   break;

                }
                uploadedFileKey = fileUploadService.uploadFile(file);
                if(imageof.equalsIgnoreCase("maintanence-record")){
                    uploadedFileId = vehicleMediaService.saveFileKey(uploadedFileKey);
                    uploadedFilesKeys.add(uploadedFileId);
                }
                if(imageof.equalsIgnoreCase("profile")){
                    Long savedImgId = profileImageService.saveFileKey(uploadedFileKey);
                    userService.saveProfileImage(savedImgId,Long.valueOf(id));
                    uploadedFilesKeys.add(savedImgId);

                }
                if(imageof.equalsIgnoreCase(("business")))
                {
                    Long savedImgId = profileImageService.saveFileKey(uploadedFileKey);
                    businessService.saveProfileImage(savedImgId,Long.valueOf(id));
                    uploadedFilesKeys.add(savedImgId);
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
