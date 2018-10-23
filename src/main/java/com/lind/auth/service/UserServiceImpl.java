package com.lind.auth.service;

import com.lind.auth.domain.User;
import com.lind.auth.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * Created by SuperS on 2017/9/25.
 *
 * @author SuperS
 */
@Service
public class UserServiceImpl implements UserService {
  private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
  private final Logger log = LoggerFactory.getLogger(getClass());
  @Autowired
  private UserRepository userRepository;

  @Override
  public void createUser(User user) {
    User existing = userRepository.findByUsername(user.getUsername());
    Assert.isNull(existing, "user already exists: " + user.getUsername());

    String hash = encoder.encode(user.getPassword());
    user.setPassword(hash);
    userRepository.save(user);
    log.info("new user has been created: {}", user.getUsername());
  }
}
