package com.smartresume.ai.service.impl;

import com.smartresume.ai.model.AtsEvaluationResult;
import com.smartresume.ai.model.PersonalDetails;
import com.smartresume.ai.model.Resume;
import com.smartresume.ai.util.StringSanitizer;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * ============================================================================
 * OOP CONCEPTS DEMONSTRATED:
 * 11. AI INTEGRATION (Smart Local Fallback & Heuristic Generation Engine)
 * 8. COLLECTIONS (Keyword Maps, Skill Sets, Stream operations)
 * 2. STRINGS (Action verbs, tokenization, phrase composition)
 * 1. DATA TYPES (double scoring, int counts, boolean thresholds)
 * ============================================================================
 */
@Component
public class SmartLocalAiEngine {

    // Action verbs for professional resume enhancement (Concept 8: Collections)
    private static final List<String> ACTION_VERBS = Arrays.asList(
            "Architected", "Engineered", "Spearheaded", "Optimized", "Designed",
            "Implemented", "Streamlined", "Pioneered", "Automated", "Delivered"
    );

    // High-demand technical keywords for ATS analysis
    private static final Map<String, List<String>> DOMAIN_SKILL_DATABASE = new HashMap<>();

    static {
        DOMAIN_SKILL_DATABASE.put("java", Arrays.asList("Java 17+", "Spring Boot", "REST APIs", "Microservices", "Hibernate/JPA", "Maven", "Docker", "JUnit", "SQL", "Git", "OOP Design Patterns"));
        DOMAIN_SKILL_DATABASE.put("python", Arrays.asList("Python 3", "Django", "FastAPI", "Pandas", "NumPy", "TensorFlow", "PostgreSQL", "Docker", "RESTful Services", "AWS"));
        DOMAIN_SKILL_DATABASE.put("frontend", Arrays.asList("React.js", "TypeScript", "JavaScript (ES6+)", "HTML5/CSS3", "Tailwind CSS", "Redux Toolkit", "Webpack", "Responsive Design"));
        DOMAIN_SKILL_DATABASE.put("fullstack", Arrays.asList("Java", "Spring Boot", "React.js", "PostgreSQL", "REST APIs", "Docker", "CI/CD", "Git", "Agile/Scrum"));
        DOMAIN_SKILL_DATABASE.put("ai", Arrays.asList("Machine Learning", "Deep Learning", "Generative AI", "NLP", "Python", "PyTorch", "Hugging Face", "LangChain", "Vector Databases"));
        DOMAIN_SKILL_DATABASE.put("data", Arrays.asList("SQL", "Python", "Data Modeling", "ETL Pipelines", "Tableau", "Power BI", "Data Analysis", "Apache Spark"));
    }

    /**
     * Generates a tailored 3-4 sentence professional student summary
     */
    public String enhanceSummary(String name, String education, String skills, String goal, String original) {
        String studentName = StringSanitizer.safeTrim(name, "Motivated Candidate");
        String edu = StringSanitizer.safeTrim(education, "Computer Science & Engineering");
        String techSkills = StringSanitizer.safeTrim(skills, "Software Engineering, Java, and Web Technologies");
        String targetGoal = StringSanitizer.safeTrim(goal, "drive software innovation and deliver measurable value");

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Goal-driven and results-oriented professional specializing in %s with strong foundational expertise in %s. ", edu, techSkills));
        sb.append(String.format("Demonstrates a solid academic track record, adept problem-solving abilities, and hands-on experience building scalable, maintainable applications. "));
        if (original != null && !original.isBlank()) {
            sb.append(String.format("Committed to %s while leveraging rigorous engineering principles and modern industry standards. ", original.trim()));
        } else {
            sb.append(String.format("Eager to leverage technical competence and collaborative teamwork to %s in an energetic engineering environment.", targetGoal));
        }

        return sb.toString();
    }

    /**
     * Improves project description with action verbs and technical impact
     */
    public String improveProject(String title, String tech, String desc) {
        String cleanTitle = StringSanitizer.safeTrim(title, "Key Project");
        String cleanTech = StringSanitizer.safeTrim(tech, "Modern Technologies");
        String cleanDesc = StringSanitizer.safeTrim(desc, "Built scalable solution addressing real-world needs.");

        return String.format(
                "• Architected and developed '%s' leveraging %s to solve real-world industry requirements.\n" +
                "• Designed end-to-end modular architecture with high performance, rigorous unit testing, and robust error handling.\n" +
                "• %s resulting in enhanced execution speed, intuitive user interaction, and seamless maintainability.",
                cleanTitle, cleanTech, cleanDesc
        );
    }

    /**
     * Improves work or internship experience description
     */
    public String improveExperience(String role, String company, String desc) {
        String cleanRole = StringSanitizer.safeTrim(role, "Software Engineer Intern");
        String cleanCompany = StringSanitizer.safeTrim(company, "Organization");
        String cleanDesc = StringSanitizer.safeTrim(desc, "Contributed to core development and project milestones.");

        return String.format(
                "• Collaborated as %s at %s to implement mission-critical features, adhering to clean OOP and software design principles.\n" +
                "• %s, improving operational efficiency and reducing latency through systematic code reviews and optimization.\n" +
                "• Partnered with cross-functional peers to troubleshoot production bottlenecks, maintain documentation, and deliver releases on schedule.",
                cleanRole, cleanCompany, cleanDesc
        );
    }

    /**
     * Improves achievement text into a crisp bullet point
     */
    public String improveAchievement(String achievement) {
        String clean = StringSanitizer.safeTrim(achievement, "Outstanding academic performance and extracurricular leadership.");
        if (!clean.endsWith(".")) {
            clean += ".";
        }
        return "Honored with recognition: " + clean + " Demonstrating high technical rigor, competitive problem-solving, and dedication to excellence.";
    }

    /**
     * Recommends skills based on candidate's career goal or keywords
     */
    public List<String> suggestSkills(String query) {
        String lower = (query != null) ? query.toLowerCase() : "";
        Set<String> suggestions = new LinkedHashSet<>();

        for (Map.Entry<String, List<String>> entry : DOMAIN_SKILL_DATABASE.entrySet()) {
            if (lower.contains(entry.getKey())) {
                suggestions.addAll(entry.getValue());
            }
        }

        // If no match, provide top general engineering skills
        if (suggestions.isEmpty()) {
            suggestions.addAll(DOMAIN_SKILL_DATABASE.get("java"));
            suggestions.addAll(DOMAIN_SKILL_DATABASE.get("fullstack"));
        }

        return new ArrayList<>(suggestions);
    }

    /**
     * Evaluates comprehensive ATS score and generates feedback
     */
    public AtsEvaluationResult evaluateAtsScore(Resume resume) {
        AtsEvaluationResult result = new AtsEvaluationResult();
        int score = 0;

        PersonalDetails p = resume.getPersonalDetails();
        // 1. Contact & Personal Details (20 points)
        int contactScore = 0;
        if (p != null) {
            if (p.getFullName() != null && !p.getFullName().isBlank() && !p.getFullName().equalsIgnoreCase("Your Name")) {
                contactScore += 5;
            } else {
                result.addImprovement("Add your full name in the Personal Details section.");
            }

            if (StringSanitizer.isValidEmail(p.getEmail())) {
                contactScore += 5;
            } else {
                result.addImprovement("Provide a valid email address for recruiter outreach.");
            }

            if (p.getPhone() != null && !p.getPhone().isBlank() && p.getPhone().trim().length() >= 7) {
                contactScore += 4;
            } else {
                result.addImprovement("Add a contact phone number so hiring managers can reach you.");
            }

            if (p.getLocation() != null && !p.getLocation().isBlank()) {
                contactScore += 3;
            } else {
                result.addImprovement("Include your location/city to match regional recruiter searches.");
            }

            if (p.hasLinkedin()) {
                contactScore += 3;
            } else {
                result.addImprovement("Add your LinkedIn profile link to improve recruiter confidence.");
            }

            if (contactScore == 20) {
                result.addStrength("Complete personal details with valid contact and profile links.");
            }
        } else {
            result.addImprovement("Add personal details and contact information.");
        }
        result.addCategoryScore("Personal Details & Contact", contactScore * 5); // scale to 100
        score += contactScore;

        // 2. Summary Quality (15 points)
        int summaryScore = 0;
        if (p != null && p.getSummary() != null && !p.getSummary().isBlank()) {
            int words = p.getSummaryWordCount();
            if (words >= 25 && words <= 120) {
                summaryScore = 15;
                result.addStrength("Professional summary length is optimal (" + words + " words).");
            } else if (words < 25) {
                summaryScore = 8;
                result.addImprovement("Summary is too short. Expand to 3-4 sentences highlighting career objectives.");
            } else {
                summaryScore = 10;
                result.addImprovement("Summary is lengthy. Make it concise for rapid ATS screening.");
            }
        } else {
            result.addImprovement("Add a professional summary to quickly communicate your strengths to recruiters.");
        }
        result.addCategoryScore("Professional Summary", (int)(summaryScore * (100.0 / 15.0)));
        score += summaryScore;

        // 3. Technical Skills (20 points)
        int skillsScore = 0;
        int skillCount = (resume.getSkills() != null) ? resume.getSkills().size() : 0;
        if (skillCount >= 8) {
            skillsScore = 20;
            result.addStrength("Excellent technical skills diversity (" + skillCount + " unique skills detected).");
        } else if (skillCount >= 4) {
            skillsScore = 14;
            result.addImprovement("Add more technical skills (languages, frameworks, tools) to improve ATS keyword match.");
        } else {
            skillsScore = 6;
            result.addImprovement("Crucial: Include at least 6-8 core technical skills relevant to your target role.");
        }
        result.addCategoryScore("Technical Skills & Keywords", (int)(skillsScore * 5.0));
        score += skillsScore;

        // 4. Projects (20 points)
        int projectScore = 0;
        int projCount = (resume.getProjects() != null) ? resume.getProjects().size() : 0;
        if (projCount >= 2) {
            projectScore = 20;
            result.addStrength("Great project portfolio with " + projCount + " featured technical projects.");
        } else if (projCount == 1) {
            projectScore = 12;
            result.addImprovement("Consider adding a second project to showcase breadth in technical capabilities.");
        } else {
            result.addImprovement("Add at least 1-2 major academic or personal software projects.");
        }
        result.addCategoryScore("Projects Section", (int)(projectScore * 5.0));
        score += projectScore;

        // 5. Experience / Internships (15 points)
        int expScore = 0;
        int expCount = (resume.getExperiences() != null) ? resume.getExperiences().size() : 0;
        if (expCount >= 1) {
            expScore = 15;
            result.addStrength("Relevant internship or professional experience included.");
        } else {
            expScore = 8; // Freshers may have no experience yet
            result.addImprovement("If you completed internships, hackathons, or open-source work, list them under Experience.");
        }
        result.addCategoryScore("Experience & Roles", (int)(expScore * (100.0 / 15.0)));
        score += expScore;

        // 6. Education & Certifications (10 points)
        int eduScore = 0;
        if (resume.getEducations() != null && !resume.getEducations().isEmpty()) {
            eduScore += 6;
        }
        if (resume.getCertifications() != null && !resume.getCertifications().isEmpty()) {
            eduScore += 4;
            result.addStrength("Certifications verified - adds competitive credibility.");
        }
        result.addCategoryScore("Education & Certifications", eduScore * 10);
        score += eduScore;

        // Overall calculations
        result.setOverallScore(score);
        if (score >= 85) {
            result.setGrade("A+ (ATS Ready)");
            result.setSummaryFeedback("Outstanding resume! Well-structured, keyword-rich, and ready for ATS screening.");
        } else if (score >= 70) {
            result.setGrade("B+ (Good Potential)");
            result.setSummaryFeedback("Strong resume with good foundations. Addressing the improvement tips will elevate it to top ATS tiers.");
        } else {
            result.setGrade("Needs Polish");
            result.setSummaryFeedback("Resume has missing core sections or inadequate keyword depth. Use AI Enhance buttons to boost quality.");
        }

        // Keywords detection
        if (resume.getSkills() != null) {
            result.getDetectedKeywords().addAll(resume.getSkills());
        }
        result.setTotalKeywordsMatched(result.getDetectedKeywords().size());

        // Recommended additional keywords
        List<String> suggested = suggestSkills((p != null) ? p.getCareerGoal() : "");
        for (String s : suggested) {
            if (resume.getSkills() == null || !resume.getSkills().contains(s)) {
                result.getRecommendedKeywords().add(s);
            }
        }

        return result;
    }
}
