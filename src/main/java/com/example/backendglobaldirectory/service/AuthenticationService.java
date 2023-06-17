package com.example.backendglobaldirectory.service;

import com.example.backendglobaldirectory.dto.LoginDTO;
import com.example.backendglobaldirectory.dto.RegisterDTO;
import com.example.backendglobaldirectory.dto.ResponseDTO;
import com.example.backendglobaldirectory.entities.Image;
import com.example.backendglobaldirectory.entities.Roles;
import com.example.backendglobaldirectory.entities.Token;
import com.example.backendglobaldirectory.entities.User;
import com.example.backendglobaldirectory.exception.EmailAlreadyUsedException;
import com.example.backendglobaldirectory.repository.TokenRepository;
import com.example.backendglobaldirectory.utils.Utils;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthenticationService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenRepository tokenRepository;

    private BCryptPasswordEncoder passwordEncoder;

    @PostConstruct
    public void initPasswordEncoder() {
        passwordEncoder = new BCryptPasswordEncoder();
    }

    public void revokeAndExpireUserTokens(int userId) {
        List<Token> tokens = this.tokenRepository.findAllValidTokensByUser(userId);
        tokens.forEach((token) -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        this.tokenRepository.saveAll(tokens);
    }

    public ResponseEntity<ResponseDTO> performRegister(RegisterDTO registerDTO)
            throws EmailAlreadyUsedException {
        Optional<User> userByEmailOptional = this.userService
                .findByEmail(registerDTO.getEmail());

        if (userByEmailOptional.isPresent()) {
            throw new EmailAlreadyUsedException("Email already used.");
        }

        User newUser = new User();
        newUser.setEmail(registerDTO.getEmail());

        newUser.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        newUser.setFirstName(registerDTO.getFirstName());
        newUser.setLastName(registerDTO.getLastName());

        newUser.setDateOfEmployment(Utils.convertDateStringToLocalDateTime(
                registerDTO.getDateOfEmployment())
        );

        newUser.setJobTitle(registerDTO.getJobTitle());
        newUser.setTeam(registerDTO.getTeam());
        newUser.setDepartment(registerDTO.getDepartment());
        newUser.setRole(Roles.USER);

        newUser.setApproved(false);

        Image profileImage = new Image(
                registerDTO.getImage().getName(),
                registerDTO.getImage().getType(),
                registerDTO.getImage().getBase64Img()
        );

        newUser.setProfileImage(profileImage);

        this.userService.save(newUser);

        return new ResponseEntity<>(
                new ResponseDTO("User registered."),
                HttpStatus.OK
        );
    }

    public ResponseEntity<ResponseDTO> performLogin(LoginDTO loginDTO,
                                                    HttpServletRequest request,
                                                    HttpServletResponse response) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getEmail(),
                        loginDTO.getPassword()
                )
        );

        User user = this.userService.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        revokeAndExpireUserTokens(user.getId());


        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", List.of(user.getRole().name()));

        String jwtToken = this.jwtService.generateToken(claims, user);
        Token token = new Token(jwtToken,
                false,
                false,
                user);

        this.tokenRepository.save(token);

        return new ResponseEntity<>(new ResponseDTO(jwtToken), HttpStatus.OK);
    }



}
