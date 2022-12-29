package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.RoleModel;
import com.example.demo.model.RoleName;

@Repository
public interface RoleRepo extends JpaRepository<RoleModel, Integer> {
    Optional<RoleModel> findByName(RoleName roleName);
}
