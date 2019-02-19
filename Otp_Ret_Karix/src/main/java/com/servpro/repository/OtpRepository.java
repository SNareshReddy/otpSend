package com.servpro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.servpro.entity.OtpUtil;

@Repository
public interface OtpRepository extends JpaRepository<OtpUtil, String> {

	OtpUtil findByMobilenumber(String mobilenumber);

	OtpUtil getByMobilenumber(String mobilenumber);

}
