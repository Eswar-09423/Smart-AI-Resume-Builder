package com.smartresume.ai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ============================================================================
 * SMART RESUME AI - MAIN ENTRY POINT
 * ============================================================================
 * OOPS CONCEPTS ARCHITECTURE PIPELINE:
 * 1. Data Types        -> Primitive (int, double, boolean, long) & Wrapper types
 * 2. Strings           -> StringBuilder, String format, Regular Expressions, Sanitization
 * 3. Classes & Objects -> Encapsulation, POJOs (Resume, PersonalDetails, DTOs)
 * 4. Inheritance       -> AbstractResumeExporter, ResumeSectionItem subclasses
 * 5. Interface         -> IValidatable, IResumeExporter, IAiService, IResumeService, IRepository
 * 6. Packages          -> model, service, repository, exception, util, controller
 * 7. Exception Handling-> ResumeValidationException, AiServiceException, GlobalExceptionHandler
 * 8. Collections       -> List, Set, Map, Stream API, TreeSet, HashSet, HashMap
 * 9. Generics          -> ApiResponse<T>, IRepository<T, ID>
 * 10. File Operations  -> Java NIO Files, BufferedReader, BufferedWriter, File Persistence
 * 11. AI Integration   -> Google Gemini LLM API + Smart Local Heuristic AI Engine
 * ============================================================================
 */
@SpringBootApplication
public class SmartResumeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartResumeApplication.class, args);

        System.out.println("\n" +
                "===============================================================\n" +
                "       🚀 SMART RESUME AI BACKEND - OOPS SHOWCASE              \n" +
                "===============================================================\n" +
                " Server running at: http://localhost:5000                     \n" +
                " API Base URL:      http://localhost:5000/api                 \n" +
                " Health Check:      http://localhost:5000/api/health          \n" +
                "---------------------------------------------------------------\n" +
                " OOP CONCEPTS DEMONSTRATED:                                    \n" +
                " [1]  Data Types        -> Primitives & Type Conversions       \n" +
                " [2]  Strings           -> Regex, StringBuilder, Sanitizer     \n" +
                " [3]  Classes & Objects -> Domain Models & Encapsulation       \n" +
                " [4]  Inheritance       -> Subclasses & Abstract Base Exporters\n" +
                " [5]  Interface         -> Standard contracts & Implementations\n" +
                " [6]  Packages          -> Clean layered architecture          \n" +
                " [7]  Exception Handling-> Custom hierarchy & ControllerAdvice \n" +
                " [8]  Collections       -> List, Set, Map & Stream API         \n" +
                " [9]  Generics          -> ApiResponse<T> & IRepository<T,ID>  \n" +
                " [10] File Operations   -> NIO Files, BufferedReader/Writer    \n" +
                " [11] AI Integration   -> Gemini LLM & Smart Local Engine     \n" +
                "===============================================================\n");
    }
}
