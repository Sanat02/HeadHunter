package com.example.demo.service;



import com.example.demo.dto.*;

import com.example.demo.model.*;


import lombok.RequiredArgsConstructor;


import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import org.springframework.stereotype.Service;
import com.example.demo.repository.ResumeRepository;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j

public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final UserService userService;
    private final ContactsService contactsService;
    private final EducationService educationService;
    private final JobExperienceService jobExperienceService;
    private final CategoryService categoryService;

    public Page<ResumeDto> getAllResumes(int start, int end) {
        log.info("Got all users");
        List<Resume> resumes = resumeRepository.findAll();
        List<ResumeDto> resumeDtos = resumes.stream()
                .map(e -> ResumeDto.builder()
                        .id(e.getId())
                        .expectedSalary(e.getExpectedSalary())
                        .job(e.getJob())
                        .applicant(userService.mapToUserDto(userService.getUserById(e.getUserId()).orElse(null)))
                        .education(educationService.getEducationByResumeId(e.getId()).orElse(null))
                        .jobExperience(jobExperienceService.getJobExperienceById(e.getId()).orElse(null))
                        .contacts(contactsService.getContactsDtoByResumeId(e.getId()))
                        .build())
                .collect(Collectors.toList());
        var page = toPage(resumeDtos, PageRequest.of(start, end));
        return page;
    }


    public List<ResumeDto> getResumeByJob(String job) {
        log.info("Got job:" + job);
        List<Resume> resumes = resumeRepository.findByJob(job);
        List<ResumeDto> resumeDtos = resumes.stream()
                .map(e -> ResumeDto.builder()
                        .id(e.getId())
                        .job(e.getJob())
                        .expectedSalary(e.getExpectedSalary())
                        .applicant(userService.mapToUserDto(userService.getUserById(e.getUserId()).get()))
                        // .contacts(contactsService.getContactsDtoByResumeId(e.getId()))
                        .build()
                ).toList();
        return resumeDtos;
    }


    public int saveResume(ResumeDto resumeDto, Authentication auth) {
        Optional<User> mayBeUser = userService.getUserById(resumeDto.getApplicant().getId());

        int userId;
        if (!mayBeUser.isPresent()) {
            userId = userService.save(resumeDto.getApplicant());
        } else {
            userId = userService.getUserByEmail(resumeDto.getApplicant().getEmail()).get().getId();

        }

        Resume savedResume = resumeRepository.save(Resume.builder()
                .expectedSalary(resumeDto.getExpectedSalary())
                .job(resumeDto.getJob())
                .userId(userId)
                .category(categoryService.getCategoryById(Integer.parseInt(resumeDto.getCategory())).get())
                .build());


        int resumeId = savedResume.getId();

        log.info("The resume with ID " + resumeId + " is saved!");
        return resumeId;

    }

    public void updateResume(ResumeDto resumeDto) {

        Optional<Resume> resume = resumeRepository.findById(resumeDto.getId());
        if (resume.isPresent()) {
            Optional<User> mayBeUser = userService.getUserById(resumeDto.getId());
            int userId;
            if (!mayBeUser.isPresent()) {
                userId = userService.save(resumeDto.getApplicant());
            } else {
                userService.update(resumeDto.getApplicant());
                userId = resumeDto.getApplicant().getId();
            }
            Resume existingResume = resume.get();
            existingResume.setExpectedSalary(resumeDto.getExpectedSalary());
            existingResume.setJob(resumeDto.getJob());
            existingResume.setUserId(userId);

            resumeRepository.save(existingResume);

        } else {
            log.error("The resume id does not exits:" + resume.get().getId());
            throw new IllegalArgumentException("Job Resume with ID " + resumeDto.getId() + " not found.");

        }
    }

    public void deleteResume(int resumeId) {
        log.info("Deleted resume with id:" + resumeId);
        resumeRepository.deleteById(resumeId);
    }

    public List<ResumeDto> getResumesByUserId(int userId) {
        List<Resume> resumes = resumeRepository.findByUserId(userId);
        return resumes.stream()
                .map(e -> ResumeDto.builder()
                        .id(e.getId())
                        .expectedSalary(e.getExpectedSalary())
                        .job(e.getJob())
                        .build())
                .collect(Collectors.toList());

    }

    public ResumeDto getResumeById(int resumeId) {
        Optional<Resume> optionalResume = resumeRepository.findById(resumeId);

        if (optionalResume.isPresent()) {
            Resume resume = optionalResume.get();
            Category category = resume.getCategory();

            if (category != null) {
                return ResumeDto.builder()
                        .id(resumeId)
                        .job(resume.getJob())
                        .category(categoryService.mapToCategoryDto(category).getName())
                        .expectedSalary(resume.getExpectedSalary())
                        .education(educationService.getEducationByResumeId(resumeId).orElse(null))
                        .jobExperience(jobExperienceService.getJobExperienceById(resumeId).orElse(null))
                        .applicant(userService.mapToUserDto(userService.getUserById(resume.getUserId()).orElse(null)))
                        .contacts(contactsService.getContactsDtoByResumeId(resumeId))
                        .build();
            } else {
                log.error("Category is null for Resume ID: " + resumeId);
                return null;
            }
        } else {
            log.error("Resume not found for ID: " + resumeId);
            return null;
        }
    }

    private Page<ResumeDto> toPage(List<ResumeDto> list, Pageable pageable) {
        if (pageable.getOffset() >= list.size()) {
            return Page.empty();
        }
        int startIndex = (int) pageable.getOffset();
        int endIndex = (int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ?
                list.size() :
                pageable.getOffset() + pageable.getPageSize());
        List<ResumeDto> subList = list.subList(startIndex, endIndex);
        return new PageImpl<>(subList, pageable, list.size());
    }
    public ResumeDto mapToResumeDto(Resume resume) {
        if (resume == null) {
            return null;
        }
        return ResumeDto.builder()
                .id(resume.getId())
                .expectedSalary(resume.getExpectedSalary())
                .job(resume.getJob())
                .applicant(userService.mapToUserDto(userService.getUserById(resume.getUserId()).orElse(null)))
                .education(educationService.getEducationByResumeId(resume.getId()).orElse(null))
                .jobExperience(jobExperienceService.getJobExperienceById(resume.getId()).orElse(null))
                .contacts(contactsService.getContactsDtoByResumeId(resume.getId()))
                .build();
    }
}
