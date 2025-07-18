package com.glunah2001.ClinicSystemAPI.service;

import com.glunah2001.ClinicSystemAPI.dto.UserRegisterDto;
import com.glunah2001.ClinicSystemAPI.enums.Role;
import com.glunah2001.ClinicSystemAPI.exception.ExceptionUtils;
import com.glunah2001.ClinicSystemAPI.exception.ResourceAlreadyExists;
import com.glunah2001.ClinicSystemAPI.model.User;
import com.glunah2001.ClinicSystemAPI.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User registerPatient(final UserRegisterDto userDto){
        try{
            final User user = User.builder()
                    .cedula(userDto.cedula())
                    .name(userDto.name())
                    .firstLastname(userDto.firstLastname())
                    .secondLastname(userDto.secondLastname())
                    .username(userDto.username())
                    .email(userDto.email())
                    .password(passwordEncoder.encode(userDto.password()))
                    .role(Role.ROLE_PATIENT)
                    .active(true)
                    .build();
            return userRepository.save(user);
        }catch(DataIntegrityViolationException e){
            String message = ExceptionUtils.extractConstraintMessage(e, userDto);
            throw new ResourceAlreadyExists(message);
        }

    }

}
