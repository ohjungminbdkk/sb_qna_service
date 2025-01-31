package com.sbs.qna_service.boundedContext.user;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sbs.qna_service.boundedContext.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	public SiteUser create(String username, String email, String password) {
		SiteUser user = new SiteUser();
		user.setUsername(username);
		user.setEmail(email);
		// BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); //
		// Bcrypt 암호화 알고리즘
		user.setPassword(passwordEncoder.encode(password));// BcryptPasswordEncoder 객체를 직접 생성하여 사용하지 않고 빈으로 등록한 Password
															// Encoder 객체를 주입받아 사용할 수 있도록 수정
		this.userRepository.save(user);
		return user;
	}

    public SiteUser getUser(String username) {
        Optional<SiteUser> siteUser = userRepository.findByusername(username);
        if (siteUser.isPresent()) {
            return siteUser.get();
        } else {
            throw new DataNotFoundException("siteuser not found");
        }
    }
}