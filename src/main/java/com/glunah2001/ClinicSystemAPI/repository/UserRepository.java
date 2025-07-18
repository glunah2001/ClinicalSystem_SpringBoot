package com.glunah2001.ClinicSystemAPI.repository;

import com.glunah2001.ClinicSystemAPI.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
