package com.senla.bulletinboard.controller;

import com.senla.bulletinboard.dto.IdDto;
import com.senla.bulletinboard.dto.PaymentDto;
import com.senla.bulletinboard.exception.NoSuchUserException;
import com.senla.bulletinboard.service.interfaces.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Data
@RestController
@RequestMapping(value = "/payments")
@Tag(name = "Payment Management Controller")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create payment", description = "Payment emulation")
    public IdDto createPayment(@RequestBody final PaymentDto paymentDto) throws NoSuchUserException {
        return paymentService.createPayment(paymentDto);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "View a list of payments", description = "View a list of payments")
    public List<PaymentDto> showPayments(@PathVariable final Long id) {
        return paymentService.showAllPaymentsByUserId(id);
    }
}
