package com.smartresume.ai.controller;

import com.smartresume.ai.model.AtsEvaluationResult;
import com.smartresume.ai.model.Resume;
import com.smartresume.ai.service.IAiService;
import com.smartresume.ai.service.IResumeService;
import com.smartresume.ai.util.ApiResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ============================================================================
 * OOP CONCEPTS DEMONSTRATED:
 * 6. PACKAGES (Controller package)
 * 9. GENERICS (Uniform ApiResponse<T> across all endpoints)
 * 5. INTERFACE (Dependency Injection of service interfaces)
 * 10. FILE OPERATIONS (Save, load, and export endpoints)
 * 11. AI INTEGRATION (AI Enhance, ATS Score, and Skill Suggestion endpoints)
 * ============================================================================
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ResumeApiController {

    private final IResumeService resumeService;
    private final IAiService aiService;

    public ResumeApiController(
            IResumeService resumeService,
            @Qualifier("geminiAiService") IAiService aiService) {
        this.resumeService = resumeService;
        this.aiService = aiService;
    }

    // ==========================================
    // HEALTH & STATUS
    // ==========================================

    @GetMapping("/")
    public ApiResponse<Map<String, Object>> root() {
        Map<String, Object> info = new HashMap<>();
        info.put("service", "Smart AI Resume Creator API");
        info.put("status", "ONLINE");
        info.put("aiProvider", aiService.getProviderName());
        info.put("version", "1.0.0");
        return ApiResponse.success(info, "SmartResume AI Backend is Running");
    }

    @GetMapping("/health")
    public ApiResponse<Map<String, Object>> health() {
        Map<String, Object> status = new HashMap<>();
        status.put("healthy", true);
        status.put("aiEngineAvailable", aiService.isAvailable());
        status.put("aiProvider", aiService.getProviderName());
        return ApiResponse.success(status, "System health operational");
    }

    // ==========================================
    // AI GENERATION ENDPOINTS
    // ==========================================

    @PostMapping("/generate-summary")
    public ApiResponse<String> generateSummary(@RequestBody ResumeDataDto dto) {
        String result = resumeService.generateSummary(
                dto.name,
                dto.education,
                dto.skills,
                dto.careerGoal,
                dto.summary
        );
        return ApiResponse.success(result, "AI Summary generated successfully");
    }

    @PostMapping("/improve-project")
    public ApiResponse<String> improveProject(@RequestBody ResumeDataDto dto) {
        String result = resumeService.improveProject(
                dto.projectName,
                dto.technologies,
                dto.description
        );
        return ApiResponse.success(result, "AI Project description improved successfully");
    }

    @PostMapping("/improve-experience")
    public ApiResponse<String> improveExperience(@RequestBody ResumeDataDto dto) {
        String result = resumeService.improveExperience(
                dto.jobTitle,
                dto.company,
                dto.description
        );
        return ApiResponse.success(result, "AI Experience description improved successfully");
    }

    @PostMapping("/improve-achievement")
    public ApiResponse<String> improveAchievement(@RequestBody ResumeDataDto dto) {
        String result = resumeService.improveAchievement(dto.achievement);
        return ApiResponse.success(result, "AI Achievement improved successfully");
    }

    @PostMapping("/suggest-skills")
    public ApiResponse<List<String>> suggestSkills(@RequestBody Map<String, String> request) {
        String query = request.getOrDefault("query", "");
        List<String> suggestions = resumeService.suggestSkills(query);
        return ApiResponse.success(suggestions, "AI Skill recommendations fetched");
    }

    @PostMapping("/ats-score")
    public ApiResponse<AtsEvaluationResult> checkAtsScore(@RequestBody Resume resume) {
        AtsEvaluationResult result = resumeService.evaluateAtsScore(resume);
        return ApiResponse.success(result, "ATS evaluation completed");
    }

    // ==========================================
    // FILE OPERATIONS ENDPOINTS (Concept 10)
    // ==========================================

    @PostMapping("/resume/save")
    public ApiResponse<Resume> saveResume(@RequestBody Resume resume) {
        Resume saved = resumeService.saveResume(resume);
        return ApiResponse.success(saved, "Resume saved to local storage");
    }

    @GetMapping("/resume/load/{id}")
    public ApiResponse<Resume> loadResume(@PathVariable String id) {
        Resume resume = resumeService.getResume(id);
        return ApiResponse.success(resume, "Resume loaded successfully");
    }

    @GetMapping("/resume/list")
    public ApiResponse<List<Resume>> listResumes() {
        List<Resume> list = resumeService.getAllSavedResumes();
        return ApiResponse.success(list, "Fetched saved resumes");
    }

    @PostMapping("/resume/export")
    public ResponseEntity<String> exportResume(
            @RequestBody Resume resume,
            @RequestParam(defaultValue = "md") String format) {

        String content = resumeService.exportResume(resume, format);
        String filename = "resume." + format;
        MediaType mediaType = switch (format.toLowerCase()) {
            case "html" -> MediaType.TEXT_HTML;
            case "txt" -> MediaType.TEXT_PLAIN;
            default -> MediaType.TEXT_MARKDOWN;
        };

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .contentType(mediaType)
                .body(content);
    }

    // ==========================================
    // DATA TRANSFER DTO (Classes & Objects)
    // ==========================================

    public static class ResumeDataDto {
        public String name;
        public String education;
        public String skills;
        public String careerGoal;
        public String summary;

        public String projectName;
        public String technologies;
        public String description;

        public String jobTitle;
        public String company;

        public String achievement;
    }
}
