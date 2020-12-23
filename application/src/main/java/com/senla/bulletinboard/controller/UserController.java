package com.senla.bulletinboard.controller;

import com.senla.bulletinboard.dto.ApiErrorDto;
import com.senla.bulletinboard.dto.BulletinBaseDto;
import com.senla.bulletinboard.dto.DialogDto;
import com.senla.bulletinboard.dto.PasswordDto;
import com.senla.bulletinboard.dto.UserDto;
import com.senla.bulletinboard.exception.EntityNotFoundException;
import com.senla.bulletinboard.exception.NoSuchUserException;
import com.senla.bulletinboard.service.BulletinService;
import com.senla.bulletinboard.service.DialogService;
import com.senla.bulletinboard.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Data
@Validated
@RestController
@RequestMapping(value = "/users")
@Tag(name = "User Management Controller")
public class UserController {

    private final DialogService dialogService;
    private final UserService userService;
    private final BulletinService bulletinService;

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "View user", description = "Show user's details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
        }),
        @ApiResponse(responseCode = "400", description = "Bad request", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorDto.class))
        }),
        @ApiResponse(responseCode = "404", description = "User not found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorDto.class))
        })
    })
    public UserDto getUser(@PathVariable final Long id) throws EntityNotFoundException {
        return userService.findDtoById(id);
    }

    @GetMapping(value = "/{id}/dialogs")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "View user's dialogs", description = "View user's dialogs")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))
        }),
        @ApiResponse(responseCode = "400", description = "Bad request", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorDto.class))
        })
    })
    public List<DialogDto> showDialogs(@PathVariable final Long id) {
        return dialogService.findDialogsByUserId(id);
    }

    @GetMapping(value = "/{id}/bulletins")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "View user's bulletins", description = "View user's bulletins")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))
        }),
        @ApiResponse(responseCode = "400", description = "Bad request", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorDto.class))
        }),
        @ApiResponse(responseCode = "404", description = "User not found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorDto.class))
        })
    })
    public List<BulletinBaseDto> showBulletins(@PathVariable final Long id,
                                               @RequestParam(defaultValue = "0", required = false) final Integer page,
                                               @RequestParam(defaultValue = "10", required = false) final Integer size)
            throws NoSuchUserException {
        return bulletinService.findBulletinsByUserId(id, page, size);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update info about user", description = "Update info about user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
        }),
        @ApiResponse(responseCode = "400", description = "Bad request", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorDto.class))
        }),
        @ApiResponse(responseCode = "404", description = "User not found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorDto.class))
        })
    })
    public UserDto updateUser(@PathVariable final Long id, @RequestBody final UserDto user) throws NoSuchUserException {
        return userService.updateUser(id, user);
    }

    @PatchMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update user's password", description = "Update user's password")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
        }),
        @ApiResponse(responseCode = "400", description = "Bad request", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorDto.class))
        }),
        @ApiResponse(responseCode = "404", description = "User not found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorDto.class))
        })
    })
    public void changePassword(@PathVariable final Long id, @Valid @RequestBody final PasswordDto passwordDto)
        throws NoSuchUserException {
        userService.changePassword(id, passwordDto);
    }
}
