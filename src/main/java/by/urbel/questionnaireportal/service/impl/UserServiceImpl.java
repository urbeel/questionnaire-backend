package by.urbel.questionnaireportal.service.impl;


import by.urbel.questionnaireportal.dto.UserDto;
import by.urbel.questionnaireportal.entity.User;
import by.urbel.questionnaireportal.mapper.UserMapper;
import by.urbel.questionnaireportal.repository.UserRepository;
import by.urbel.questionnaireportal.service.UserService;
import by.urbel.questionnaireportal.service.exceptions.EmailAlreadyUsedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    @Override
    public void update(Long id, UserDto userDto) {
        User user = userRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException(String.format("User %d not found.", id));
        });
        if (!user.getEmail().equals(userDto.getEmail()) && userRepository.existsByEmail(userDto.getEmail())) {
            throw new EmailAlreadyUsedException("Email is already used for another user.");
        }
        userMapper.updateExisting(userDto, user);
        user.setId(id);
        userRepository.save(user);
    }
}
