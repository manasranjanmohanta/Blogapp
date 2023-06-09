package com.myblog.blogapp.controller;

import com.myblog.blogapp.payload.PostDto;
import com.myblog.blogapp.payload.PostResponse;
import com.myblog.blogapp.service.PostService;
import com.myblog.blogapp.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/posts")
@RestController
public class PostController {

    private PostService postService;
    public PostController(PostService postService){
        this.postService = postService;
    }

    // Create and save data into db
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @GetMapping
    public PostResponse getAllPosts(@RequestParam(value = "pageNo", defaultValue = AppConstants.DEfAULT_PAGE_NUMBER, required = false) int pageNo,
                                    @RequestParam(value = "pageSize", defaultValue = AppConstants.DEfAULT_PAGE_SIZE, required = false) int pageSize,
                                    @RequestParam(value = "sortBy",defaultValue = AppConstants.DEfAULT_SORT_BY, required = false) String sortBy,
                                    @RequestParam(value = "sortDir", defaultValue = AppConstants.DEfAULT_SORT_DIR, required = false) String sortDir
                                     ){

        return postService.getAllPosts(pageNo, pageSize,sortBy, sortDir);
    }

    // get only one record by id
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") long id){
//        return new ResponseEntity<>(postService.getPostById(id),HttpStatus.FOUND);
        return ResponseEntity.ok(postService.getPostById(id));
    }

    // updating record via id
    @PutMapping("/{id}")
    public ResponseEntity<String> updatePost(@RequestBody PostDto postDto, @PathVariable("id") long id){
        PostDto dto = postService.updatePost(postDto, id);
        return new ResponseEntity<>("Post updated successfully",  HttpStatus.OK);
    }

    // deleting posts
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") long id){
        postService.deletePost(id);
        return new ResponseEntity<>("Post deleted successfully!", HttpStatus.OK);
    }
}
