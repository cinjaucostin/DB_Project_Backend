package com.example.backendglobaldirectory.controller;

import com.example.backendglobaldirectory.dto.RejectDTO;
import com.example.backendglobaldirectory.dto.ResponseDTO;
import com.example.backendglobaldirectory.exception.UserNotFoundException;
import com.example.backendglobaldirectory.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Approve a register account request from an user.",
            description = "Approve the register, so the user can log in on the platform. It's a specific action of an admin " +
                    "so it needs an ADMIN role to perform.")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = {@Content(schema = @Schema(implementation = ResponseDTO.class),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "403",
                    description = "If an user tries to approve an account of another user and he is not authorized for" +
                            " this kind of action the server will return a 403 Forbidden status code and a specific message",
                    content = {@Content(schema = @Schema(implementation = ResponseDTO.class),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "If an user with the searched id is not found the server will return 404 Not Found " +
                            "and a specific message",
                    content = {@Content( schema = @Schema(implementation = ResponseDTO.class),
                            mediaType = "application/json")})})
    @PutMapping("/approve")
    public ResponseEntity<ResponseDTO> approveRegister(
            @Parameter(description = "User id to make approve account for.", example = "2")
            @RequestParam int uid)
            throws UserNotFoundException, FileNotFoundException {
        return this.userService.performAccountApproveOrReject(uid, true, null);
    }

    @Operation(summary = "Reject a register account request from an user.",
            description = "Same as approve but this one reject the register request, so the user will not be " +
                    "able to log in on the platform until an admin decides to approve his account.")
    @PutMapping("/reject")
    public ResponseEntity<ResponseDTO> rejectRegister(
            @Parameter(description = "User id to make reject account for.", example = "2")
            @RequestParam int uid,
            @RequestBody RejectDTO rejectDTO)
            throws UserNotFoundException, FileNotFoundException {
        return this.userService.performAccountApproveOrReject(uid, false, rejectDTO);
    }

    @Operation(summary = "Activate an account.",
            description = "Activate an account if it has been inactivated by an admin in the past. Also it needs specific authorization.")
    @PutMapping("/activate")
    public ResponseEntity<ResponseDTO> activateUser(
            @Parameter(description = "User id to make activate account for.", example = "2")
            @RequestParam int uid)
            throws UserNotFoundException {
        return this.userService.performAccountStatusSwitch(uid, true);
    }

    @Operation(summary = "Inactivate an account.",
            description = "Inactivate an account. Specific action for an ADMIN. One use case can be when the employee leaves" +
                    " the company so his account will be inactivated, not deleted.")
    @PutMapping("/inactivate")
    public ResponseEntity<ResponseDTO> inactivateUser(
            @Parameter(description = "User id to make inactivate account for.", example = "2")
            @RequestParam int uid)
            throws UserNotFoundException {
        return this.userService.performAccountStatusSwitch(uid, false);
    }

}
