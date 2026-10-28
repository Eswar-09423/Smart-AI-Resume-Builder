# 🎓 Smart AI Resume Creator - OOP Concepts Guide (Viva / Lab Reference)

This project strictly adheres to the requested Object-Oriented Programming (OOP) progression pipeline:
**Data Types → Strings → Classes & Objects → Inheritance → Interface → Packages → Exception Handling → Collections → Generics → File Operations → AI Integration**

---

## 📌 1. Data Types
- **Location:** `com.smartresume.ai.model.*`, `com.smartresume.ai.util.ApiResponse`
- **Primitives Used:**
  - `int`: word counts (`countWords()`), duration months, total keywords matched, HTTP status code (`statusCode = 200`).
  - `double`: CGPA scoring (`numericCgpa`), ATS score percentage (`overallScore`), resume completeness (`completenessPercentage = 85.5`).
  - `boolean`: validation flags (`hasLinkedin()`, `isPassedAts()`, `success`).
  - `long`: timestamps (`createdAtTimestamp`, `errorTimestamp`).
- **Type Conversions:**
  - Extracting numbers from strings using `Double.parseDouble(tokens[0])` and `Integer.parseInt()`.

---

## 📌 2. Strings
- **Location:** `com.smartresume.ai.util.StringSanitizer`, `com.smartresume.ai.service.impl.AbstractResumeExporter`
- **Concepts Implemented:**
  - **StringBuilder:** High-performance, memory-efficient string building in `MarkdownResumeExporter` and `PlainTextResumeExporter`.
  - **Regular Expressions (`java.util.regex.Pattern` & `Matcher`):**
    - Email validation: `^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$`
    - Phone number validation and URL pattern matching.
  - **String Operations:** `trim()`, `split("\\s+")` for word counting, `String.format()`, `String.join()`, and text escaping for HTML.

---

## 📌 3. Classes & Objects
- **Location:** `com.smartresume.ai.model.*`
- **Concepts Implemented:**
  - **Encapsulation:** Private attributes with public getters, setters, and constructors.
  - **Domain Model (Aggregate Root):** `Resume` class encapsulating personal details, experience, projects, education, skills, and styling preferences.
  - **Constructors:** Default constructors and overloaded parameterized constructors for object instantiation.

---

## 📌 4. Inheritance
- **Location:** `ResumeSectionItem` hierarchy & `AbstractResumeExporter` hierarchy
- **Base Class & Subclasses:**
  - `ResumeSectionItem` (Abstract Superclass) with `id`, `title`, `description`, `orderIndex`, `createdAtTimestamp`.
    - `ExperienceItem extends ResumeSectionItem` (adds company, duration, role, calls `super()`).
    - `ProjectItem extends ResumeSectionItem` (adds technologies, projectUrl).
    - `EducationItem extends ResumeSectionItem` (adds institution, grade, graduationYear).
    - `CertificationItem extends ResumeSectionItem` (adds organization, year).
    - `AchievementItem extends ResumeSectionItem` (adds achievementText).
  - `AbstractResumeExporter` (Abstract Base Class implementing `IResumeExporter`):
    - `MarkdownResumeExporter extends AbstractResumeExporter`
    - `PlainTextResumeExporter extends AbstractResumeExporter`
    - `HtmlResumeExporter extends AbstractResumeExporter`
  - **Polymorphism:** Subclasses override `@Override public String toFormattedText()` and `@Override public String getDisplayTitle()`.

---

## 📌 5. Interface
- **Location:** `com.smartresume.ai.service.*`, `com.smartresume.ai.repository.IRepository`, `com.smartresume.ai.model.IValidatable`
- **Interfaces Defined:**
  - `IValidatable`: Contract enforcing `void validate() throws ResumeValidationException;`.
  - `IAiService`: Contract for AI generation (`generateContent(String prompt)`).
  - `IResumeService`: Contract for all business operations (summaries, enhancements, ATS analysis).
  - `IResumeExporter`: Contract for multi-format document generation.
  - `IRepository<T, ID>`: Generic contract for data persistence.

---

## 📌 6. Packages
- Clean multi-tier layered package organization:
  - `com.smartresume.ai`: Main entrypoint & Spring Boot runner.
  - `com.smartresume.ai.model`: Domain entities & value objects.
  - `com.smartresume.ai.service`: Business service interfaces.
  - `com.smartresume.ai.service.impl`: Business logic & AI implementations.
  - `com.smartresume.ai.repository`: Data access & file persistence.
  - `com.smartresume.ai.exception`: Custom exception hierarchy & global handler.
  - `com.smartresume.ai.util`: Utilities, generic wrappers, and string sanitizers.
  - `com.smartresume.ai.controller`: REST API endpoints.

---

## 📌 7. Exception Handling
- **Location:** `com.smartresume.ai.exception.*`
- **Exception Hierarchy:**
  - `ResumeAppException` (Base unchecked exception)
    - `ResumeValidationException`: Thrown on invalid email, missing required fields.
    - `AiServiceException`: Thrown on AI generation errors.
    - `FileStorageException`: Wraps checked `IOException` during file read/write.
    - `ResourceNotFoundException`: Thrown when a saved resume is not found.
- **Centralized Handler:** `GlobalExceptionHandler` with `@RestControllerAdvice` mapping exceptions to standard HTTP error codes (`400 Bad Request`, `404 Not Found`, `500 Internal Error`).
- **Try-with-resources:** Automatically closes `BufferedReader` and `BufferedWriter` streams without memory leaks.

---

## 📌 8. Collections Framework
- **Location:** Throughout models and services
- **Collections Demonstrated:**
  - `List<T>`: `ArrayList<ExperienceItem>`, `ArrayList<ProjectItem>`, `ArrayList<EducationItem>`.
  - `Set<T>`: `TreeSet<String>` for alphabetical deduplicated skills; `HashSet<String>` for keyword lookups.
  - `Map<K, V>`: `HashMap<String, Integer>` for ATS category scoring, `HashMap<String, IResumeExporter>` for polymorphic strategy dispatch.
  - **Stream API:** Filtering, mapping, and collecting collections using `stream().filter().collect()`.

---

## 📌 9. Generics
- **Location:** `com.smartresume.ai.util.ApiResponse<T>`, `com.smartresume.ai.repository.IRepository<T, ID>`
- **Concepts Implemented:**
  - **Generic Class `ApiResponse<T>`:** Wraps any payload (`Resume`, `AtsEvaluationResult`, `List<String>`).
  - **Generic Static Factory Methods:** `<T> ApiResponse<T> success(T data, String message)`.
  - **Generic Interface:** `IRepository<T, ID>` parameterized by entity `T` and identifier `ID`.

---

## 📌 10. File Operations
- **Location:** `com.smartresume.ai.repository.FileResumeRepository`, `com.smartresume.ai.service.impl.*`
- **File I/O Features:**
  - `java.nio.file.Files`, `java.nio.file.Path`, `Paths.get()`.
  - `BufferedReader` & `BufferedWriter` using `StandardCharsets.UTF_8`.
  - Saving resume JSON files to `/saved_resumes/` on local disk (`POST /api/resume/save`).
  - Reading and listing saved files from disk (`GET /api/resume/list`).
  - Exporting resume files as `.md`, `.txt`, and `.html`.

---

## 📌 11. AI Integration
- **Location:** `com.smartresume.ai.service.impl.GeminiAiServiceImpl` & `SmartLocalAiEngine`
- **Dual Engine Architecture:**
  - **Cloud LLM:** Google Gemini API (`gemini-1.5-flash`) via REST HTTP client.
  - **Smart Local Fallback Engine:** Heuristic rule-based engine that automatically steps in if API keys are not supplied, ensuring the project NEVER crashes during presentations or offline testing!
  - **Features:** Summary generation, project action-verb improvements, STAR method experience enhancement, ATS score calculation, and skill suggestions.

---

## 🚀 How to Run
1. Double-click **`start-project.bat`** (or run `run-backend.bat` and open `frontend/index.html`).
2. Click **"Load Sample Data"** to prefill with 1 click.
3. Test AI buttons: **AI Enhance Summary**, **AI Polish**, **AI Suggest Skills**, and **ATS Score Checker**.
4. Test different **Themes** and **Layout Styles** (Single Column, Two-Column, Minimalist).
5. Click **"Save to Disk"** or **"Export MD / TXT"** to verify File Operations!
