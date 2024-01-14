package com.shop.coffee;

import com.shop.constant.Role;
import com.shop.dto.MemberFormDto;
import com.shop.entity.Member;
import com.shop.repository.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class  DataLoader implements CommandLineRunner {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();  // 비밀번호 인코더 설정
    }

    @Override
    public void run(String... args) throws Exception {
        MemberFormDto memberFormDto = new MemberFormDto();

        memberFormDto.setName("admin");
        memberFormDto.setEmail("admin@admin.com");
        memberFormDto.setAddress("Test Address");
        memberFormDto.setPassword("11111111");


        Member member = Member.createMember(memberFormDto, passwordEncoder);
        member.setRole(Role.ADMIN);

        memberRepository.save(member);
    }
}