package AS_API.dto;

import lombok.Builder;

public class CommentRequestDto {

    private final Long postId;
    private final Long parentCommentId;
    private final String commentContent;

    @Builder
    public CommentRequestDto(Long postId, Long parentCommentId, String commentContent) {
        this.postId = postId;
        this.parentCommentId = parentCommentId;
        this.commentContent = commentContent;
    }

    public Long getPostId() { return postId; }
    public Long getParentCommentId() { return parentCommentId; }
    public String getCommentContent() { return commentContent; }
}
