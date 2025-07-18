package com.glunah2001.ClinicSystemAPI.service;

import com.glunah2001.ClinicSystemAPI.model.User;

public interface JwtService {
    String generateAccessToken(User user);
    String generateRefreshToken(User user);
}
