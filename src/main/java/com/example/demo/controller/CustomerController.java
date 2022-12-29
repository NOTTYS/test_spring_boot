package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.CustomerModel;
import com.example.demo.service.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    CustomerService cService;
    @GetMapping("/get")
    public List<CustomerModel> getAll() {
        return cService.getAll();
    }

    @GetMapping("/get/{id}")
    public CustomerModel getId(@PathVariable Integer id) {
        return cService.getById(id);
    }

    @PostMapping("/post")
    public CustomerModel addOne(@Valid @RequestBody(required = true) CustomerModel customer) {
        return cService.addOne(customer);
    }

    @PostMapping("/posts")
    public List<CustomerModel> addOne(@Valid @RequestBody List<CustomerModel> customer) {
        return cService.addAll(customer);
    }

    @PutMapping("/put/{id}")
    public CustomerModel updateOne(@Valid @RequestBody CustomerModel customer, @PathVariable Integer id) {
        return cService.updateOne(customer);
    }

    @DeleteMapping("/delete")
    public String deleteOne(@PathVariable Integer id) {
        return cService.deleteOne(id);
    }
}
