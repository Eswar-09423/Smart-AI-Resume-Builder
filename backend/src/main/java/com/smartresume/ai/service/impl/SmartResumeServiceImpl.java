package com.smartresume.ai.service.impl;

import com.smartresume.ai.exception.ResourceNotFoundException;
import com.smartresume.ai.exception.ResumeValidationException;
import com.smartresume.ai.model.AtsEvaluationResult;
import com.smartresume.ai.model.Resume;
import com.smartresume.ai.repository.FileResumeRepository;
import com.smartresume.ai.service.IAiService;
import com.smartresume.ai.service.IResumeExporter;
import com.smartresume.ai.service.IResumeService;
import com.smartresume.ai.util.StringSanitizer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ============================================================================
 * OOP CONCEPTS DEMONSTRATED:
 * 5. INTERFACE (Implements IResumeService)
 * 8. COLLECTIONS (Map<String, IResumeExporter>, List<Resume>, List<String>)
 * 10. FILE OPERATIONS (Coordinates with FileResumeRepository)
 * 11. AI INTEGRATION (Coordinates with IAiService & SmartLocalAiEngine)
 * 4. INHERITANCE & POLYMORPHISM (Invoking export() polymorphically across exporters)
 * 7. EXCEPTION HANDLING (Validation and propagation)
 * ============================================================================
 */
@Service
public class SmartResumeServiceImpl implements IResumeService {

    private final IAiService aiService;
    private final SmartLocalAiEngine localAiEngine;
    private final FileResumeRepository fileRepository;

    // Polymorphic Map of Exporters (Concept 8: Collections & Concept 5: Interface)
    private final Map<String, IResumeExporter> exporterMap;

    public SmartResumeServiceImpl(
            @Qualifier("geminiAiService") IAiService aiService,
            SmartLocalAiEngine localAiEngine,
            FileResumeRepository fileRepository,
            MarkdownResumeExporter markdownExporter,
            PlainTextResumeExporter plainTextExporter,
            HtmlResumeExporter htmlExporter) {

        this.aiService = aiService;
        this.localAiEngine = localAiEngine;
        this.fileRepository = fileRepository;

        this.exporterMap = new HashMap<>();
        this.exporterMap.put("md", markdownExporter);
        this.exporterMap.put("markdown", markdownExporter);
        this.exporterMap.put("txt", plainTextExporter);
        this.exporterMap.put("text", plainTextExporter);
        this.exporterMap.put("html", htmlExporter);
    }

    @Override
    public String generateSummary(String name, String education, String skills, String careerGoal, String currentSummary) {
        if (aiService.isAvailable()) {
            String prompt = String.format("""
                    Create a professional resume summary for a candidate.
                    Name: %s
                    Education: %s
                    Skills: %s
                    Career Goal: %s
                    Existing Summary: %s
                    
                    Requirements:
                    - 3 to 4 professional, ATS-optimized sentences.
                    - Highlight technical skills and career aspirations.
                    - Return ONLY the summary text with no extra commentary.
                    """,
                    StringSanitizer.safeTrim(name, "Candidate"),
                    StringSanitizer.safeTrim(education, "Engineering"),
                    StringSanitizer.safeTrim(skills, "Software Development"),
                    StringSanitizer.safeTrim(careerGoal, "Software Engineer"),
                    StringSanitizer.safeTrim(currentSummary, "")
            );
            try {
                return aiService.generateContent(prompt);
            } catch (Exception ex) {
                System.err.println("[SERVICE] AI generation failed, using local engine: " + ex.getMessage());
            }
        }
        return localAiEngine.enhanceSummary(name, education, skills, careerGoal, currentSummary);
    }

    @Override
    public String improveProject(String projectName, String technologies, String description) {
        if (aiService.isAvailable()) {
            String prompt = String.format("""
                    Improve this project description for a professional student software engineering resume.
                    Project Name: %s
                    Technologies: %s
                    Description: %s
                    
                    Requirements:
                    - Write 3 bullet points starting with strong action verbs (Architected, Engineered, Implemented).
                    - Highlight technical contribution, performance, and real-world outcomes.
                    - Return ONLY the bullet points.
                    """,
                    StringSanitizer.safeTrim(projectName, "Project"),
                    StringSanitizer.safeTrim(technologies, "Software Stack"),
                    StringSanitizer.safeTrim(description, "")
            );
            try {
                return aiService.generateContent(prompt);
            } catch (Exception ex) {
                System.err.println("[SERVICE] Project AI failed, using local engine: " + ex.getMessage());
            }
        }
        return localAiEngine.improveProject(projectName, technologies, description);
    }

    @Override
    public String improveExperience(String jobTitle, String company, String description) {
        if (aiService.isAvailable()) {
            String prompt = String.format("""
                    Improve the following work/internship experience for a professional resume.
                    Job Title: %s
                    Company: %s
                    Description: %s
                    
                    Requirements:
                    - 2 to 3 concise, impactful bullet points using the STAR method.
                    - Focus on technical tasks, team collaboration, and impact.
                    - Return ONLY the bullet points.
                    """,
                    StringSanitizer.safeTrim(jobTitle, "Engineer"),
                    StringSanitizer.safeTrim(company, "Company"),
                    StringSanitizer.safeTrim(description, "")
            );
            try {
                return aiService.generateContent(prompt);
            } catch (Exception ex) {
                System.err.println("[SERVICE] Experience AI failed, using local engine: " + ex.getMessage());
            }
        }
        return localAiEngine.improveExperience(jobTitle, company, description);
    }

    @Override
    public String improveAchievement(String achievement) {
        return localAiEngine.improveAchievement(achievement);
    }

    @Override
    public List<String> suggestSkills(String query) {
        return localAiEngine.suggestSkills(query);
    }

    @Override
    public AtsEvaluationResult evaluateAtsScore(Resume resume) {
        if (resume == null) {
            resume = new Resume();
        }
        return localAiEngine.evaluateAtsScore(resume);
    }

    @Override
    public Resume saveResume(Resume resume) {
        if (resume == null) {
            throw new ResumeValidationException("resume", "Resume payload cannot be null.");
        }
        resume.validate(); // Polymorphic validation (Concept 5: Interface)
        return fileRepository.save(resume.getId(), resume);
    }

    @Override
    public Resume getResume(String id) {
        return fileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resume", id));
    }

    @Override
    public List<Resume> getAllSavedResumes() {
        return fileRepository.findAll();
    }

    @Override
    public String exportResume(Resume resume, String format) {
        String cleanFormat = (format != null) ? format.toLowerCase().trim() : "md";
        IResumeExporter exporter = exporterMap.get(cleanFormat); // Polymorphic Dispatch
        if (exporter == null) {
            exporter = exporterMap.get("md");
        }
        return exporter.export(resume);
    }
}
