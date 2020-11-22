package com.senla.bulletin_board.controller;

import com.senla.bulletin_board.dto.PaymentDto;
import com.senla.bulletin_board.dto.SubscriptionDto;
import com.senla.bulletin_board.exception.NoSuchUserException;
import com.senla.bulletin_board.service.PaymentService;
import com.senla.bulletin_board.service.SubscriptionService;
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
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createPayment(@RequestBody final PaymentDto paymentDto) throws NoSuchUserException {
        paymentService.createPayment(paymentDto);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<PaymentDto> showPayments(@PathVariable final Long id) {
        return paymentService.showAllPaymentsByUserId(id);
    }
}
