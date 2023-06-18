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
import com.example.demo.service.DownloadService;
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

    @Autowired
    private DownloadService downloadService;

    // Upload file to public
    @PostMapping(value = "/upload/file", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<File> uploadFile(@RequestParam("fileUpload") MultipartFile fileUpload,
            @RequestParam("shared") String shared,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("category") String category,
            @RequestParam("tags") Set<String> tagNames,
            @RequestParam("iduser") Long idUser,
            @RequestParam("fileImg") MultipartFile fileImg) {
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
        Users user = userService.findById(idUser).orElse(null);
        // System.out.println(shared);
        // System.out.println(tags);
        if (user.getUsername().equals("")) {
            user.setUsername("Root");// Save to default folder if the user does not select a folder to save - you can
                                     // change it
        }

        Category categoryName = fileService.findByCategoryName(category);
        File file = new File(title, fileUpload.getContentType(), fileUpload.getSize() / 1024, description, user,
                categoryName, tags);
        String link = fileService.uploadFile(fileUpload, user.getUsername(), Boolean.parseBoolean(shared));
        String linkImg = fileService.uploadFile(fileImg, user.getUsername(), true);
        PDDocument document;
        try {
            document = PDDocument.load(fileUpload.getInputStream());
            int page = document.getNumberOfPages();
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
    public ModelAndView deleteFile(@RequestBody FileForm fileForm, HttpServletRequest request) throws Exception {
        fileService.deleteFileById(fileForm.getFile_id());
        fileService.deleteFile(fileForm.getDrive_id());

        return new ModelAndView("redirect:" + "/");
    }

    // Download file
    @GetMapping("/download/{id}/{user_id}/{file_id}")
    public void downloadFile(@PathVariable String id, @PathVariable Long user_id, @PathVariable Long file_id,
            HttpServletResponse response) throws IOException, GeneralSecurityException {
        fileService.downloadFile(id, response.getOutputStream());
        downloadService.saveDownload(user_id, file_id);
    }

    @PostMapping("/search/tagname")
    public ResponseEntity<?> Search(@RequestParam("tagName") String tagName) {
        List<Tag> tags = tagServiceImpl.search(tagName);
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @GetMapping("/category/list")
    public ResponseEntity<List<Category>> getAllPackages() {
        try {
            List<Category> categories = fileService.getAllFileCategories();
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/ListFiles")
    public ResponseEntity<List<File>> getFiles() {
        try {
            List<File> files = fileService.getAllFiles();
            return new ResponseEntity<>(files, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/TopFiles")
    public ResponseEntity<List<File>> getTopFiles() {
        try {
            List<File> files = fileService.getTopFile();
            return new ResponseEntity<>(files, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<File>> searchFile(@RequestParam("tagName") String tagName) {
        List<File> files = fileService.search(tagName);
        return ResponseEntity.ok(files);
    }

    @PostMapping("/like")
    public ResponseEntity<Boolean> likeFile(@RequestBody CommentForm likeForm) {
        boolean result = likeService.save(likeForm.getUserid(), likeForm.getFileid());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<List<CommentResponse>> getCommentsByFileId(@PathVariable("id") Long fileId) {
        List<CommentResponse> commentResponses = commentService.getCommentsByFileId(fileId);
        return ResponseEntity.ok(commentResponses);
    }


    @PostMapping("/comments")
    public ResponseEntity<Boolean> saveComment(@RequestBody CommentForm commentForm) {
        try {
            boolean kq = commentService.saveComment(commentForm.getUserid(), commentForm.getFileid(),
                    commentForm.getContten());
            return new ResponseEntity<>(kq, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getFile/id")
    public ResponseEntity<File> getFile(@RequestParam("file_id") Long file_id) {
        Optional<File> optionalFile = fileService.findById(file_id);
        File file = optionalFile
                .orElseThrow(() -> new org.springframework.security.acls.model.NotFoundException("File not found"));
        file.setView(file.getView() + 1);
        fileService.save(file);
        return new ResponseEntity<>(file, HttpStatus.OK);
    }

    @DeleteMapping("/delete/like")
    public ResponseEntity<Boolean> deleteLike(@RequestBody CommentForm commentForm) {
        try {
            UserFile userFile = new UserFile(commentForm.getFileid(), commentForm.getUserid());
            boolean kq = likeService.deleteLikeById(userFile);
            return new ResponseEntity<>(kq, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/repost")
    public ResponseEntity<Boolean> saveRepost(@RequestBody CommentForm repostForm) {
        try {
            boolean kq = repostService.save(repostForm.getUserid(), repostForm.getFileid(), repostForm.getContten());
            return new ResponseEntity<>(kq, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}