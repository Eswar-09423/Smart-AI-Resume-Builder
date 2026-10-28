package com.smartresume.ai.service;

import com.smartresume.ai.model.AtsEvaluationResult;
import com.smartresume.ai.model.Resume;

import java.util.List;

/**
 * ============================================================================
 * OOP CONCEPT DEMONSTRATED: 5. INTERFACE
 * ============================================================================
 * Defines business service operations for the Smart AI Resume Creator application.
 * Decouples controller layer from concrete business logic implementations.
 * ============================================================================
 */
public interface IResumeService {

    /**
     * Generates or enhances professional summary using AI
     */
    String generateSummary(String name, String education, String skills, String careerGoal, String currentSummary);

    /**
     * Polishes project description with tech metrics and action verbs
     */
    String improveProject(String projectName, String technologies, String description);

    /**
     * Polishes work/internship experience using STAR methodology
     */
    String improveExperience(String jobTitle, String company, String description);

    /**
     * Improves achievement bullet point
     */
    String improveAchievement(String achievement);

    /**
     * Suggests relevant skills for target job or technical area
     */
    List<String> suggestSkills(String query);

    /**
     * Evaluates comprehensive ATS score and provides actionable recommendations
     */
    AtsEvaluationResult evaluateAtsScore(Resume resume);

    /**
     * Saves resume to local disk file system
     */
    Resume saveResume(Resume resume);

    /**
     * Loads saved resume from disk
     */
    Resume getResume(String id);

    /**
     * Lists all saved resumes on disk
     */
    List<Resume> getAllSavedResumes();

    /**
     * Exports resume to specified format ("md", "txt", "html")
     */
    String exportResume(Resume resume, String format);
}
