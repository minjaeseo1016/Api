package AS_API.service;

import AS_API.exception.CustomException;
import AS_API.exception.ErrorCode;
import AS_API.repository.UserRepository;
import AS_API.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void registerUser(User user){
        validateDuplicateUser(user);
        userRepository.save(user);
    }

    private void validateDuplicateUser(User user) {
        User findUser = userRepository.findByUid(user.getUid());
        if (findUser != null) {
            throw new CustomException(ErrorCode.USER_ALREADY_EXIST);
        }
    }


    public User findUserByUid(String uid) {
        User user = userRepository.findByUid(uid);
        if (user == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        return user;
    }

    public void deleteUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            userRepository.deleteById(userId);
        } else {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
    }

}
