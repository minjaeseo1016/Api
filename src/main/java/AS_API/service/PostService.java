package AS_API.service;

import AS_API.dto.PostRequestDto;
import AS_API.dto.PostResponseDto;
import AS_API.entity.Post;
import AS_API.entity.User;
import AS_API.repository.PostRepository;
import AS_API.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public void createPost(Long userId, PostRequestDto dto) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        Post post = Post.builder()
            .user(user)
            .postTitle(dto.getPostTitle())
            .content(dto.getContent())
            .postCount(0)
            .build();

        postRepository.save(post);
    }

    public List<PostResponseDto> getAllPosts() {
        return postRepository.findAll().stream()
            .map(post -> PostResponseDto.builder()
                .postId(post.getPostId())
                .postTitle(post.getPostTitle())
                .content(post.getContent())
                .postCount(post.getPostCount())
                .postDate(post.getPostDate())
                .build())
            .collect(Collectors.toList());
    }

    public PostResponseDto getPostById(Long postId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        return PostResponseDto.builder()
            .postId(post.getPostId())
            .postTitle(post.getPostTitle())
            .content(post.getContent())
            .postCount(post.getPostCount())
            .postDate(post.getPostDate())
            .build();
    }

    
    public List<PostResponseDto> getMyPosts(Long userId) {
        return postRepository.findByUser_UserId(userId).stream()
            .map(post -> PostResponseDto.builder()
                .postId(post.getPostId())
                .postTitle(post.getPostTitle())
                .content(post.getContent())
                .postCount(post.getPostCount())
                .postDate(post.getPostDate())
                .build())
            .collect(Collectors.toList());
    }
}
