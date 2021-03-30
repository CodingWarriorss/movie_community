package com.codeworrisors.Movie_Community_Web.service;

import com.codeworrisors.Movie_Community_Web.constant.NaverApiProperties;
import com.codeworrisors.Movie_Community_Web.dto.CreateReviewDto;
import com.codeworrisors.Movie_Community_Web.dto.UpdateReviewDto;
import com.codeworrisors.Movie_Community_Web.model.Image;
import com.codeworrisors.Movie_Community_Web.model.Member;
import com.codeworrisors.Movie_Community_Web.model.Review;
import com.codeworrisors.Movie_Community_Web.repository.ImageRepository;
import com.codeworrisors.Movie_Community_Web.repository.LikeRepository;
import com.codeworrisors.Movie_Community_Web.repository.MemberRepository;
import com.codeworrisors.Movie_Community_Web.repository.ReviewRepository;
import com.codeworrisors.Movie_Community_Web.request.NaverAPIResponse;
import com.codeworrisors.Movie_Community_Web.request.NaverMovie;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@Transactional
public class ReviewService {

    @Value("${file.path}")
    private String fileRealPath; // 이미지 저장 경로

    private final ReviewRepository reviewRepository;
    private final ImageRepository imageRepository;
    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;

    public ReviewService(ReviewRepository reviewRepository, ImageRepository imageRepository, LikeRepository likeRepository, MemberRepository memberRepository) {
        this.reviewRepository = reviewRepository;
        this.imageRepository = imageRepository;
        this.likeRepository = likeRepository;
        this.memberRepository = memberRepository;
    }

    /*
     * READ Review, Comment, Like
     *
     * */
    public List<Review> getAll(Pageable pageable) {
        List<Review> reviews = reviewRepository.findAll(pageable).getContent();
        reviews.forEach(review -> {
                    review.setLikeCount(likeRepository.countByReviewId(review.getId()));
                });

        return reviews;
    }

    public List<Review> getReviewsByMovieTitle(Pageable pageable, String movieTitle) {
        return reviewRepository.findByMovieTitle(pageable, movieTitle).getContent();
    }

    public List<Review> getReviewsByMemberId(Pageable pageable, long memberId) throws IllegalStateException {
        if (memberRepository.findById(memberId).isEmpty())
            throw new IllegalStateException("존재하지 않는 회원");

        return reviewRepository.findByMemberId(pageable, memberId).getContent();
    }


    /*
     * CREATE, UPDATE, DELETE REVIEW
     * */
    public JSONObject createReview(Member member, CreateReviewDto createReviewDto) throws IOException {
        Review saved = reviewRepository.save(
                new Review(createReviewDto.getMovieTitle().replaceAll("\n", ""),
                        createReviewDto.getContent(),
                        createReviewDto.getRating(),
                        member));

        JSONObject result = new JSONObject();
        result.put("reviewId", saved.getId());
        if (createReviewDto.getFiles() != null) {
            result.put("imageIds", saveImages(saved, createReviewDto.getFiles()));
        }
        return result;
    }


    public JSONObject updateReview(Member member, UpdateReviewDto updateReviewDto) throws IllegalStateException, NoSuchElementException, IOException {
        reviewRepository.findById(updateReviewDto.getReviewId())
                .ifPresentOrElse(review -> {
                    if (review.getMember().getId() != member.getId())
                        throw new IllegalStateException("권한 없는 리뷰에 대한 수정 요청");
                }, () -> {
                    throw new NoSuchElementException("존재하지 않는 리뷰에 대한 수정 요청");
                });

        Review review = reviewRepository.findById(updateReviewDto.getReviewId()).get();
        review.setContent(updateReviewDto.getContent());
        review.setRating(updateReviewDto.getRating());

        JSONObject result = new JSONObject();
        if (updateReviewDto.getNewFiles() != null) {
            JSONArray res_array = saveImages(review, updateReviewDto.getNewFiles());
            result.put("imageIds", res_array);
        }
        if (updateReviewDto.getDeletedFiles() != null) {
            deleteImages(review.getId(), updateReviewDto.getDeletedFiles());
        }
        return result;
    }

    private JSONArray saveImages(Review review, List<MultipartFile> newFiles) throws IOException {
        JSONArray imageIds = new JSONArray();

        for (MultipartFile file : newFiles) {
            String uuidFilename = saveFile(file);
            Image saved = imageRepository.save(new Image(uuidFilename, review));
            imageIds.add(saved.getId());
        }

        return imageIds;
    }

    private String saveFile(MultipartFile file) throws IOException {
        UUID uuid = UUID.randomUUID(); // 식별자 생성
        String uuidFilename = uuid + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(fileRealPath + uuidFilename);
        Files.write(filePath, file.getBytes());
        return uuidFilename;
    }

    private void deleteImages(long reviewId, List<Long> deletedFiles) {
        Collections.sort(deletedFiles);
        int idx = 0, cnt = deletedFiles.size();

        // 현재 리뷰 id로 검색한 이미지 리스트에서만 삭제
        for (Image image : imageRepository.findImagesByReviewId(reviewId)) {
            if (cnt == 0) break;
            if (image.getId() != deletedFiles.get(idx)) continue;

            removeFile(image.getFileName());
            imageRepository.delete(image);
            idx++;
            cnt--;
        }
    }

    public void deleteReview(Member member, long reviewId) throws IllegalStateException, NoSuchElementException {
        reviewRepository.findById(reviewId).ifPresentOrElse(
                review -> {
                    if (review.getMember().getId() != member.getId())
                        throw new IllegalStateException("권한 없는 리뷰에 대한 삭제 요청");

                    review.getImageList().forEach(image -> removeFile(image.getFileName()));
                    reviewRepository.delete(review);
                },
                () -> {
                    throw new NoSuchElementException("존재하지 않는 리뷰에 대한 삭제 요청");
                });
    }

    private void removeFile(String uuidFilename) {
        try {
            Path filePath = Paths.get(fileRealPath + uuidFilename);
            Files.delete(filePath);
        } catch (IOException e) {
            System.out.println("이미지 파일 삭제 오류");
        }
    }


    /*
     * Naver 영화 API 통신
     */
    public String searchMovie(String title) throws IOException {
        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", NaverApiProperties.CLIENT_ID);
        requestHeaders.put("X-Naver-Client-Secret", NaverApiProperties.CLIENT_SECRET);
        return get(NaverApiProperties.API_URL + title, requestHeaders);
    }

    private String get(String apiUrl, Map<String, String> requestHeaders) throws IOException {
        // 요청
        HttpURLConnection conn = connect(apiUrl);
        try {
            conn.setRequestMethod("GET");
            for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
                conn.setRequestProperty(header.getKey(), header.getValue());
            }

            // 응답
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출

                return readBody(conn.getInputStream());
            } else { // 에러 발생
                return readBody(conn.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            conn.disconnect();
        }
    }

    private HttpURLConnection connect(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private String readBody(InputStream body) {
        InputStreamReader streamReader = new InputStreamReader(body);

        ObjectMapper jsonParser = new ObjectMapper();

        //Jaskson 사용 Deserializing? 후 sorting 다시 serializing
        try {
            NaverAPIResponse naverAPIResponse = jsonParser.readValue(body, NaverAPIResponse.class);
            naverAPIResponse.getItems().sort(new Comparator<NaverMovie>() {
                @Override
                public int compare(NaverMovie o1, NaverMovie o2) {
                    double o1Rating = Double.parseDouble(o1.getUserRating());
                    double o2Rating = Double.parseDouble(o2.getUserRating());
                    return (o1Rating > o2Rating) ? -1 : 1;
                }
            });

            return jsonParser.writeValueAsString(naverAPIResponse);
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }

//
//    public Image createImage(Image image) {
//        return imageRepository.save(image);
//    }
//
//
//    public Review createReview(Review review) {
//        return reviewRepository.save(review);
//    }
//
//
//    public Review updateReview(Review review) {
//        if (reviewRepository.findById(review.getId()).isEmpty())
//            return null;
//        return reviewRepository.save(review);
//    }
//
//
//    public void deleteReview(Review review) {
//        reviewRepository.delete(review);
//    }
//
//    public void clear() {
//        reviewRepository.deleteAll();
//    }
//
//    public Review getReviewById(long reviewId) {
//        return reviewRepository.findById(reviewId).get();
//    }
//
//
}
