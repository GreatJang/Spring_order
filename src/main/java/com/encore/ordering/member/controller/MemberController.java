package com.encore.ordering.member.controller;

import com.encore.ordering.common.ResponseDto;
import com.encore.ordering.member.domain.Member;
import com.encore.ordering.member.dto.LoginReqDto;
import com.encore.ordering.member.dto.MemberCreateReqDto;
import com.encore.ordering.member.dto.MemberResponseDto;
import com.encore.ordering.member.service.MemberService;
import com.encore.ordering.securities.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class MemberController {
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public MemberController(MemberService memberService, JwtTokenProvider jwtTokenProvider) {
        this.memberService = memberService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/member/create") // @Valid : Valid 옵션확인 유효한지 아닌지
    public ResponseEntity<ResponseDto> memberCreate(@Valid @RequestBody MemberCreateReqDto memberCreateReqDto){
        Member member = memberService.create(memberCreateReqDto);
        return new ResponseEntity<>(new ResponseDto(HttpStatus.CREATED, "member successfully created", member.getId()), HttpStatus.CREATED);
    }

    @GetMapping("/members")
    public List<MemberResponseDto> members(){
        return memberService.findAll();
    }

    @GetMapping("/member/myInfo")
    public MemberResponseDto findMyInfo() {
        return memberService.findMyInfo();
    }

//    @GetMapping("/member/{id}/orders")

//    @GetMapping("/member/myorders")


    @PostMapping("/doLogin") //doLogin은 토큰 사용으로 Map형식으로 받아주어야함 // Map<String, Object>
    public ResponseEntity<ResponseDto> memberLogin(@Valid @RequestBody LoginReqDto loginReqDto){
        Member memeber = memberService.login(loginReqDto);
//        토큰 생성
        String jwtToken = jwtTokenProvider.createToken(memeber.getEmail(), memeber.getRole().toString());
        Map<String, Object> member_info = new HashMap<>();
        member_info.put("id", memeber.getId());
        member_info.put("token", jwtToken);
        return new ResponseEntity<>(new ResponseDto(HttpStatus.OK, "member successfully logined",member_info), HttpStatus.OK);
    }
}
