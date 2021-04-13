package com.codeworrisors.Movie_Community_Web.service;

import com.codeworrisors.Movie_Community_Web.dto.ResponseDto;
import com.codeworrisors.Movie_Community_Web.dto.member.request.CreateMemberDto;
import com.codeworrisors.Movie_Community_Web.dto.member.request.ReadMemberDto;
import com.codeworrisors.Movie_Community_Web.dto.member.request.UpdateMemberDto;
import com.codeworrisors.Movie_Community_Web.dto.member.response.MemberResponseDto;
import com.codeworrisors.Movie_Community_Web.dto.member.response.MemberSelectResponseDto;
import com.codeworrisors.Movie_Community_Web.dto.member.response.MemberUpdateResponseDto;
import com.codeworrisors.Movie_Community_Web.model.Image;
import com.codeworrisors.Movie_Community_Web.model.Member;
import com.codeworrisors.Movie_Community_Web.model.Review;
import com.codeworrisors.Movie_Community_Web.model.RoleType;
import com.codeworrisors.Movie_Community_Web.property.StaticResourceProperties;
import com.codeworrisors.Movie_Community_Web.repository.ImageRepository;
import com.codeworrisors.Movie_Community_Web.repository.MemberRepository;
import com.codeworrisors.Movie_Community_Web.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class MemberService {
    public static final int SUCCESS = 1;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;
    private final ImageRepository imageRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void validateDuplicateMemberId(String memberName) throws IllegalStateException {
        memberRepository.findByMemberName(memberName)
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원");
                });
    }

    public int createMember(CreateMemberDto createMemberDto) throws IllegalStateException {
        validateDuplicateMemberId(createMemberDto.getMemberName());// 한번 더 중복체크

        Member member = new Member(createMemberDto.getMemberName(), bCryptPasswordEncoder.encode(createMemberDto.getPassword()),
                createMemberDto.getName(), createMemberDto.getEmail(), createMemberDto.getBio(),
                createMemberDto.getWebsite(), saveImg(createMemberDto.getProfileImg()), RoleType.ROLE_USER);

        memberRepository.save(member);

        return SUCCESS;
    }

    private String saveImg(MultipartFile file) {
        if (file.isEmpty()) {
            return null;
        }
        UUID uuid = UUID.randomUUID();
        String uuidFilename = uuid + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(StaticResourceProperties.IMAGE_UPLOAD_PATH + uuidFilename);
        try {
            Files.write(filePath, file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uuidFilename;
    }

    public MemberSelectResponseDto selectMember(ReadMemberDto readMemberDto) throws NoSuchElementException {
        Member selectMember = memberRepository.findByMemberName(readMemberDto.getMemberName())
                .orElseThrow(NoSuchElementException::new);

        return MemberSelectResponseDto
                .builder()
                .name(selectMember.getName())
                .bio(selectMember.getBio())
                .email(selectMember.getEmail())
                .profileImg(selectMember.getProfileImg())
                .build();
    }

    public MemberUpdateResponseDto updateMember(Member member, UpdateMemberDto updateMemberDto) throws NoSuchElementException {
        Member updateMember = memberRepository.findById(member.getId())
                .orElseThrow(NoSuchElementException::new);
        MemberResponseDto memberResponseDto = MemberResponseDto.builder()
        .name(updateMember.changeName(updateMember.getName()))
        .email(updateMember.changeEmail(updateMemberDto.getEmail()))
        .website(updateMember.changeWebsite(updateMemberDto.getWebsite()))
        .bio(updateMember.changeBio(updateMemberDto.getBio()))
        .profileImg(updateMember.changeProfile(updateMemberDto.getProfileImg()))
        .build();

        return MemberUpdateResponseDto.builder()
                .result("success")
                .member(memberResponseDto)
                .build();
    }

    private void removeImg(String uuidFilename) {
        try {
            Path filePath = Paths.get(StaticResourceProperties.IMAGE_UPLOAD_PATH + uuidFilename);
            Files.delete(filePath);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void removeFile(String uuidFilename) {
        try {
            Path filePath = Paths.get(StaticResourceProperties.IMAGE_UPLOAD_PATH + uuidFilename);
            Files.delete(filePath);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void deleteImages(Review review){
        List<Image> imagesByReviewId = imageRepository.findImagesByReviewId(review.getId());
        imagesByReviewId.forEach(image -> {
            imageRepository.delete(image);
        });
    }

    private void deleteCascades(Member member) {
        reviewRepository.findById(member.getId())
                .ifPresent(review -> {
                    review.getImageList().forEach(image -> removeFile(image.getFileName()));
                    deleteImages(review);
                    reviewRepository.delete(review);
                });
    }

    public ResponseDto deleteMember(Member member) {
        memberRepository.findById(member.getId())
                .map(deleteMember -> {
                    removeImg(deleteMember.getProfileImg());
                    deleteCascades(deleteMember);
                    memberRepository.delete(deleteMember);
                    return deleteMember;
                })
                .orElseThrow(NoSuchElementException::new);

        return ResponseDto.builder()
                .result("success")
                .build();
    }
}
