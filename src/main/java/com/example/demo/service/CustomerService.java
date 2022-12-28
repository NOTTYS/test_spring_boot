package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.CustomerModel;
import com.example.demo.repository.CustomerRepo;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepo cusRepo;

    public List<CustomerModel> getAll() {
        return cusRepo.findAll();
    }

    public CustomerModel getById(Integer id) {
        return cusRepo.findById(id).orElse(null);
    }

    public CustomerModel addOne(CustomerModel customer) {
        return cusRepo.save(customer);
    }
    
    public List<CustomerModel> addAll(List<CustomerModel> customer) {
        return cusRepo.saveAll(customer);
    }

    public CustomerModel updateOne(CustomerModel customer) {
        CustomerModel existingcustomer = cusRepo.findById(customer.getId()).orElse(null);
        existingcustomer.setFirstName(customer.getFirstName());
        existingcustomer.setLastName(customer.getLastName());
        existingcustomer.setAge(customer.getAge());
        existingcustomer.setPosition(customer.getPosition());
        existingcustomer.setSalary(customer.getSalary());
        return cusRepo.save(existingcustomer);
    }

    public String deleteOne(Integer id) {
        cusRepo.deleteById(id);
        return "ID "+id+"is deleted!";
    }
}
