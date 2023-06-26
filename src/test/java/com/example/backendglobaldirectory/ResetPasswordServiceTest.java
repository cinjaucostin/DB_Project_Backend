package com.example.backendglobaldirectory;
import com.example.backendglobaldirectory.dto.ForgotPasswordDTO;
import com.example.backendglobaldirectory.dto.ResponseDTO;
import com.example.backendglobaldirectory.entities.User;
import com.example.backendglobaldirectory.exception.ThePasswordsDoNotMatchException;
import com.example.backendglobaldirectory.exception.UserNotFoundException;
import com.example.backendglobaldirectory.repository.UserRepository;
import com.example.backendglobaldirectory.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ResetPasswordServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private String newPassword;

    @BeforeEach
    public void setup() throws IOException {
        Properties properties = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties");
        properties.load(inputStream);
        newPassword = properties.getProperty("newPassword");
    }

    @Test
    public void changePasswordOfExistingUserTest() throws ThePasswordsDoNotMatchException, UserNotFoundException {
        String email = "miruna@gmail.com";
        String password = newPassword;
        String confirmPassword = newPassword;

        ForgotPasswordDTO forgotPasswordDTO = new ForgotPasswordDTO();
        forgotPasswordDTO.setPassword(password);
        forgotPasswordDTO.setConfirmPassword(confirmPassword);

        User user = new User();
        user.setEmail(email);
        Optional<User> userOptional = Optional.of(user);

        when(userRepository.findByEmail(email)).thenReturn(userOptional);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        ResponseEntity<ResponseDTO> response = userService.changePassword(forgotPasswordDTO, email);

        verify(userRepository).save(user);
        assertTrue(passwordEncoder.matches(password, user.getPassword()));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Password changed.", response.getBody().getMessage());
    }

    @Test
    public void changePasswordOfNotExistingUserTest() throws ThePasswordsDoNotMatchException, UserNotFoundException {
        String email = "miruna@gmail.com";
        String password = newPassword;
        String confirmPassword = newPassword;

        ForgotPasswordDTO forgotPasswordDTO = new ForgotPasswordDTO();
        forgotPasswordDTO.setPassword(password);
        forgotPasswordDTO.setConfirmPassword(confirmPassword);

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.changePassword(forgotPasswordDTO, email);
        });

        verify(userRepository, never()).save(any());
    }
}
