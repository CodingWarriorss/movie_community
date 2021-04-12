package com.codeworrisors.Movie_Community_Web.service;

import com.codeworrisors.Movie_Community_Web.dto.CreateMemberDto;
import com.codeworrisors.Movie_Community_Web.dto.UpdateMemberDto;
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
import org.springframework.context.annotation.PropertySource;
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

        Member member = new Member();
        member.setMemberName(createMemberDto.getMemberName());
        member.setPassword(bCryptPasswordEncoder.encode(createMemberDto.getPassword()));
        member.setName(createMemberDto.getName());
        member.setEmail(createMemberDto.getEmail());
        member.setBio(createMemberDto.getBio());
        member.setWebsite(createMemberDto.getWebsite());
        if (createMemberDto.getProfileImg() != null) {
            member.setProfileImg(saveImg(createMemberDto.getProfileImg()));
        }
        member.setRole(RoleType.ROLE_USER);
        memberRepository.save(member);
        return 1;
    }

    private String saveImg(MultipartFile file) {
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

    public JSONObject selectMember(Member member) throws NoSuchElementException {
        JSONObject result = new JSONObject();

        memberRepository.findByMemberName(member.getMemberName())
                .ifPresentOrElse(m -> {
                            result.put("name", m.getName());
                            result.put("email", m.getEmail());
                            result.put("website", m.getWebsite());
                            result.put("bio", m.getBio());
                            result.put("profileImg", m.getProfileImg());
                        },
                        () -> {
                            throw new NoSuchElementException("존재하지 않는 회원에 대한 정보조회 요청");
                        });

        return result;
    }

    public JSONObject updateMember(Member member, UpdateMemberDto updateMemberDto) throws NoSuchElementException {
        JSONObject result = new JSONObject();

        memberRepository.findById(member.getId())
                .ifPresentOrElse(m -> {
                    m.setName(updateMemberDto.getName());
                    m.setEmail(updateMemberDto.getEmail());
                    m.setWebsite(updateMemberDto.getWebsite());
                    m.setBio(updateMemberDto.getBio());
                    if (updateMemberDto.getProfileImg() != null) {
                        removeImg(member.getProfileImg());
                        String profileImg = saveImg(updateMemberDto.getProfileImg());
                        m.setProfileImg(profileImg);
                    }
                    result.put("name", m.getName());
                    result.put("email", m.getEmail());
                    result.put("website", m.getWebsite());
                    result.put("bio", m.getBio());
                    result.put("profileImg", m.getProfileImg());
                }, () -> {
                    throw new NoSuchElementException("존재하지 않는 회원에 대한 정보수정 요청");
                });

        return result;
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

    public void deleteMember(Member member) {
        memberRepository.findById(member.getId())
                .ifPresentOrElse(m -> {
                            removeImg(member.getProfileImg());
                            deleteCascades(member);
                            memberRepository.delete(m);
                        },
                        () -> {
                            throw new NoSuchElementException("존재하지 않는 회원에 대한 정보삭제 요청");
                        });
    }
}
