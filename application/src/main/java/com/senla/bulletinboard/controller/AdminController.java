package com.senla.bulletinboard.controller;

import com.senla.bulletinboard.dto.ApiErrorDto;
import com.senla.bulletinboard.dto.IdDto;
import com.senla.bulletinboard.dto.TariffDto;
import com.senla.bulletinboard.dto.UserDto;
import com.senla.bulletinboard.enumerated.UserRole;
import com.senla.bulletinboard.exception.EntityNotFoundException;
import com.senla.bulletinboard.exception.NoSuchUserException;
import com.senla.bulletinboard.service.TariffService;
import com.senla.bulletinboard.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Data
@RestController
@RequestMapping(value = "/admin")
@Tag(name = "Admin Management Controller")
public class AdminController {

    private final UserService userService;
    private final TariffService tariffService;

    @PatchMapping(value = "/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Change user role", description = "Change user role")
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
    public UserDto changeUserRole(@PathVariable final Long id, @RequestParam(value = "role") final UserRole role)
        throws NoSuchUserException {
        return userService.updateUserRole(id, role);
    }

    @GetMapping(value = "/users")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "View a list of available users", description = "View a list of available users")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))
        }),
        @ApiResponse(responseCode = "400", description = "Bad request", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorDto.class))
        })
    })
    public List<UserDto> showAllUsers() {
        return userService.findAllDto();
    }

    @PostMapping(value = "/tariffs")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create tariff", description = "Store new tariff to database")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Successful operation", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = IdDto.class))
        }),
        @ApiResponse(responseCode = "400", description = "Bad request", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorDto.class))
        })
    })
    public IdDto addTariff(@RequestBody final TariffDto tariffDto) {
        return tariffService.post(tariffDto);
    }

    @PutMapping(value = "/tariffs/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update tariff", description = "Update tariff")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = TariffDto.class))
        }),
        @ApiResponse(responseCode = "400", description = "Bad request", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorDto.class))
        }),
        @ApiResponse(responseCode = "404", description = "User not found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorDto.class))
        })
    })
    public TariffDto updateTariff(@PathVariable final Long id, @RequestBody final TariffDto tariffDto)
        throws EntityNotFoundException {
        return tariffService.updateTariff(id, tariffDto);
    }
}
