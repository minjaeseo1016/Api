package AS_API.dto;

import lombok.Builder;

public class PostRequestDto {

    private final String postTitle;
    private final String content;

    @Builder
    public PostRequestDto(String postTitle, String content) {
        this.postTitle = postTitle;
        this.content = content;
    }

    public String getPostTitle() { return postTitle; }
    public String getContent() { return content; }
}
