package AS_API.dto;

import lombok.Builder;

import java.time.LocalDateTime;

public class CommentResponseDto {

    private final Long commentId;
    private final Long postId;
    private final Long parentCommentId;
    private final String commentContent;
    private final LocalDateTime createdAt;

    @Builder
    public CommentResponseDto(Long commentId, Long postId, Long parentCommentId,
                              String commentContent, LocalDateTime createdAt) {
        this.commentId = commentId;
        this.postId = postId;
        this.parentCommentId = parentCommentId;
        this.commentContent = commentContent;
        this.createdAt = createdAt;
    }

    public Long getCommentId() { return commentId; }
    public Long getPostId() { return postId; }
    public Long getParentCommentId() { return parentCommentId; }
    public String getCommentContent() { return commentContent; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
