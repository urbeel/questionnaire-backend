package by.urbel.questionnaireportal.service.impl;


import by.urbel.questionnaireportal.constants.Messages;
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
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(Messages.USER_NOT_FOUND));
    }

    @Override
    public UserDto findById(UUID id) {
        return userMapper.userToDto(userRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException(Messages.USER_NOT_FOUND);
        }));
    }

    @Override
    public void update(UUID id, UserDto userDto) {
        User user = userRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException(Messages.USER_NOT_FOUND);
        });
        if (!user.getEmail().equals(userDto.getEmail()) && userRepository.existsByEmail(userDto.getEmail())) {
            throw new EmailAlreadyUsedException(Messages.EMAIL_USED);
        }
        userMapper.updateExisting(userDto, user);
        user.setId(id);
        userRepository.save(user);
    }
}
