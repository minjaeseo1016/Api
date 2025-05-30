package AS_API.controller;

import AS_API.dto.CommentRequestDto;
import AS_API.dto.CommentTreeDto;
import AS_API.service.CommentService;
import AS_API.config.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

  
    @PostMapping
    public ResponseEntity<?> createComment(@RequestBody CommentRequestDto requestDto,
                                           @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long userId = userDetails.getUser().getUserId();
        commentService.createComment(userId, requestDto);
        return ResponseEntity.ok().build();
    }

    
    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentTreeDto>> getCommentsByPostId(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getCommentsByPostId(postId));
    }
}
