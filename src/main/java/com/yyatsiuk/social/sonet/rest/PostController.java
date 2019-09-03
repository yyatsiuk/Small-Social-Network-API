package com.yyatsiuk.social.sonet.rest;
import com.yyatsiuk.social.sonet.dto.model.PostDTO;
import com.yyatsiuk.social.sonet.dto.request.PostAddRequest;
import com.yyatsiuk.social.sonet.dto.request.PostUpdateRequest;
import com.yyatsiuk.social.sonet.model.content.Post;
import com.yyatsiuk.social.sonet.service.PostService;
import com.yyatsiuk.social.sonet.utils.CustomModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("SpringElInspection")
@RestController
@RequestMapping("api/posts")
public class PostController {

    private final PostService postService;
    private final CustomModelMapper <Post, PostDTO> postCustomModelMapper;

    @Autowired
    public PostController(
            PostService postService,
            @Qualifier("postMapper") CustomModelMapper<Post, PostDTO> postCustomModelMapper) {
            this.postService = postService;
            this.postCustomModelMapper = postCustomModelMapper;
        }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDTO> getPost(@PathVariable Long postId) {
        PostDTO postResponse = postCustomModelMapper.toDTO(postService.findById(postId));
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @GetMapping(path = "users/{actorId}")
    public ResponseEntity<List<PostDTO>> getPosts(@PathVariable Long actorId, Pageable pageable) {
        List<PostDTO> postResponse = postService.findAll(actorId, pageable).stream()
                .map(postCustomModelMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @GetMapping(path = "/users/{userId}/news")
    public ResponseEntity<List<PostDTO>> getNews(@PathVariable Long userId, Pageable pageable) {
        List<PostDTO> postResponse = postService.findNews(userId, pageable).stream()
                .map(postCustomModelMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    @PreAuthorize("isPostCreator(#postId)")
    public ResponseEntity deletePost(@PathVariable Long postId) {
        postService.delete(postId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostAddRequest post) {
        PostDTO response = postCustomModelMapper.toDTO(postService.create(post));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("isPostCreator(#post.id)")
    public ResponseEntity<PostDTO> updatePost(@Valid @RequestBody PostUpdateRequest post) {
        PostDTO response = postCustomModelMapper.toDTO(postService.update(post));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/top")
    public ResponseEntity<List<PostDTO>> getTopNews(Pageable pageable) {
        List<PostDTO> postResponse = postService.findTopPosts(pageable).stream()
                .map(postCustomModelMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @GetMapping("/{userId}/liked")
    public ResponseEntity<List<PostDTO>> getLikedPosts(@PathVariable Long userId, Pageable pageable) {
        List<PostDTO> postResponse = postService.findLikedPosts(userId, pageable).stream()
                .map(postCustomModelMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @GetMapping("/{userId}/following/liked")
    public ResponseEntity<List<PostDTO>> getLikedPostsOfFollowings(@PathVariable Long userId, Pageable pageable) {
        List<PostDTO> postResponse = postService.findLikedPostsOfFollowing(userId, pageable).stream()
                .map(postCustomModelMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<List<PostDTO>> getByText(@RequestParam("text") String text, Pageable pageable) {
        List<PostDTO> postResponse = postService.findByText(text, pageable).stream()
                .map(postCustomModelMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }
}
