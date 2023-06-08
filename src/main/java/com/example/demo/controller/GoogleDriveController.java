package com.example.demo.controller;

import com.example.demo.dto.request.CommentForm;
import com.example.demo.dto.request.FileForm;
import com.example.demo.dto.request.FollowForm;
import com.example.demo.dto.response.CommentResponse;
import com.example.demo.model.Category;
import com.example.demo.model.Comment;
import com.example.demo.model.File;
import com.example.demo.model.UserFile;
import com.example.demo.model.Tag;
import com.example.demo.model.Users;
import com.example.demo.service.CommentService;
import com.example.demo.service.ILikeService;
import com.example.demo.service.IRepostService;
import com.example.demo.service.impl.FileServiceImpl;
import com.example.demo.service.impl.TagServiceImpl;
import com.example.demo.service.impl.UserServiceImpl;

import javassist.NotFoundException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequestMapping("/file")
@CrossOrigin("*")
@RestController(value = "googleDriveController")
public class GoogleDriveController {

    @Autowired
    FileServiceImpl fileService;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    TagServiceImpl tagServiceImpl;

    @Autowired
    ILikeService likeService;

    @Autowired
    IRepostService repostService;

    @Autowired
    private CommentService commentService;



    // Upload file to public
    @PostMapping(value = "/upload/file",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE} )
    public ResponseEntity<File>  uploadFile(@RequestParam("fileUpload") MultipartFile fileUpload,
                                @RequestParam("shared") String shared,
                                 @RequestParam("title") String title,
                                 @RequestParam("description") String description,
                                 @RequestParam("category") String category,
                                 @RequestParam("tags") Set<String> tagNames,
                                 @RequestParam("iduser") Long idUser,
                                 @RequestParam("img") String linkImg
                                ) {
                                    Set<Tag> tags = tagNames.stream()
                                    .map(TagName -> {
                                        Optional<Tag> optionalTag = tagServiceImpl.findByTagName(TagName);
                                        Tag tag = optionalTag.orElseGet(() -> {
                                            Tag newTag = new Tag(TagName);
                                            tagServiceImpl.save(newTag);
                                            return newTag;
                                        });
                                        return tag;
                                    })
                                    .collect(Collectors.toSet());
                                    Users user= userService.findById(idUser).orElse(null);
        // System.out.println(shared);
        // System.out.println(tags);
        if (user.getUsername().equals("")){
            user.setUsername("Root");// Save to default folder if the user does not select a folder to save - you can change it
        }
       
        Category categoryName = fileService.findByCategoryName(category);
        File file = new File(title, fileUpload.getContentType(), fileUpload.getSize()/1024,description,user,categoryName,tags);
        String link =fileService.uploadFile(fileUpload, user.getUsername(), Boolean.parseBoolean(shared));
        PDDocument document;
        try {
            document = PDDocument.load(fileUpload.getInputStream());
            int page=document.getNumberOfPages();
            file.setView(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
        file.setLink(link);
        file.setLinkImg(linkImg);
        fileService.save(file);
    
        return ResponseEntity.ok(file);
    }

    // Delete file by id thêm xóa id @RequestBody
    @GetMapping("/delete/file/{id}")
    public ModelAndView deleteFile(@RequestBody FileForm fileForm,HttpServletRequest request) throws Exception {
        fileService.deleteFileById(fileForm.getFile_id());
       fileService.deleteFile(fileForm.getDrive_id());
        
        return new ModelAndView("redirect:" + "/");
    }

    // Download file
    @GetMapping("/download/file/{id}")
    public void downloadFile(@PathVariable String id, HttpServletResponse response) throws IOException, GeneralSecurityException {
        fileService.downloadFile(id, response.getOutputStream());
    }

    @PostMapping("/search")
    public ResponseEntity<?> Search( @RequestParam("tagName") String tagName) {
        List<Tag> tags= tagServiceImpl.search(tagName);
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @GetMapping("/category/list")
    public List<Category> getCategoryList() {
        try {
            return fileService.getAllFileCategories();
        } catch (Exception e) {
            return fileService.getAllFileCategories();
        }
    }
    
    @GetMapping("/Files")
    public ResponseEntity<?> getFiles() {
           List<File> files = fileService.getAllFiles();
           return new ResponseEntity<>(files,HttpStatus.OK);
    }

    @GetMapping("TopFiles")
    public ResponseEntity<?> getTopFiles() {
           List<File> files = fileService.getTopFile();
           
           return new ResponseEntity<>(files,HttpStatus.OK);
    }


    @PostMapping("/search/File")
    public ResponseEntity<?> SearchFile( @RequestParam("tagName") Long tagName) {
        List<File> files= fileService.search(tagName);
        return new ResponseEntity<>(files, HttpStatus.OK);
    }

    @PostMapping("/file/like")
    public ResponseEntity<?> LikeFile(@RequestBody CommentForm likeForm) {
        boolean kq=likeService.save(likeForm.getUserid(), likeForm.getFileid());
        return new ResponseEntity<>(kq,HttpStatus.OK);
    }
 
    @GetMapping("/files/comments/id")
    public ResponseEntity<List<CommentResponse>> getCommentsByFileId(@RequestBody CommentForm commentForm) {
        List<CommentResponse> commentsResponses = commentService.getCommentsByFileId(commentForm.getFileid());
        return ResponseEntity.ok(commentsResponses);
    }
 
    @PostMapping("/files/comments")
    public ResponseEntity<?> saveComment(@RequestBody CommentForm commentForm) {
        boolean kq = commentService.saveComment(commentForm.getUserid(),commentForm.getFileid(),commentForm.getContten());
        return new ResponseEntity<>(kq,HttpStatus.OK);
    }

    @GetMapping("/getFile/id")
    public ResponseEntity<?> getFile(@RequestBody FileForm fileForm) {
          Optional<File> optionalFile =fileService.findById(fileForm.getFile_id());
          File file = optionalFile.isPresent() ? optionalFile.get() : null; // lấy giá trị trong optional và gán cho user, hoặc gán giá trị null nếu optional rỗng.
          if (file == null) {
              throw new org.springframework.security.acls.model.NotFoundException("File not found");
          }else{
            file.setView(file.getView() + 1);
            fileService.save(file);
          }
          
        return new ResponseEntity<>(file,HttpStatus.OK);
    }

    @DeleteMapping("/delete/like")
    public ResponseEntity<?> deleteLike(@RequestBody CommentForm fileForm) {
        UserFile id=new UserFile(fileForm.getFileid(), fileForm.getUserid());
        boolean kq=likeService.deleteLikeById(id);
        return new ResponseEntity<>(kq,HttpStatus.OK);
    }

    @PostMapping("/file/repost")
    public ResponseEntity<?> RepostFile(@RequestBody CommentForm repostForm) {
        boolean kq=repostService.save(repostForm.getUserid(), repostForm.getFileid(),repostForm.getContten());
        return new ResponseEntity<>(kq,HttpStatus.OK);
    }

}