package com.smartresume.ai.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.smartresume.ai.exception.FileStorageException;
import com.smartresume.ai.exception.ResourceNotFoundException;
import com.smartresume.ai.model.Resume;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * ============================================================================
 * OOP CONCEPTS DEMONSTRATED:
 * 10. FILE OPERATIONS (NIO.2 Files, Path, BufferedReader, BufferedWriter, Try-with-resources)
 * 9. GENERICS (Implements generic interface IRepository<Resume, String>)
 * 7. EXCEPTION HANDLING (Checked IOException handling & custom exception wrapping)
 * 8. COLLECTIONS (List<Resume>, Stream processing)
 * 5. INTERFACE (Concrete implementation of repository contract)
 * ============================================================================
 */
@Repository
public class FileResumeRepository implements IRepository<Resume, String> {

    private final Path storageDirectory;
    private final ObjectMapper objectMapper;

    public FileResumeRepository() {
        // Storage folder for saved resumes on disk (Concept 10: File Operations)
        this.storageDirectory = Paths.get(System.getProperty("user.dir"), "saved_resumes");
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        initDirectory();
    }

    /**
     * Initializes directory on disk (Concept 10: File Operations)
     */
    private void initDirectory() {
        try {
            if (!Files.exists(storageDirectory)) {
                Files.createDirectories(storageDirectory);
                System.out.println("[FILE SYSTEM] Initialized resume directory: " + storageDirectory.toAbsolutePath());
            }
        } catch (IOException e) {
            throw new FileStorageException(storageDirectory.toString(), "Could not create storage directory", e);
        }
    }

    @Override
    public Resume save(String id, Resume resume) throws FileStorageException {
        if (id == null || id.isBlank()) {
            id = (resume.getId() != null) ? resume.getId() : "default_resume";
        }
        resume.setId(id);
        resume.setLastUpdated(System.currentTimeMillis());

        Path filePath = storageDirectory.resolve("resume_" + id + ".json");

        // Concept 10: File Operations using Try-with-resources (Automatic resource closing)
        // Concept 7: Exception Handling
        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8)) {
            String jsonContent = objectMapper.writeValueAsString(resume);
            writer.write(jsonContent);
            writer.flush();
            System.out.println("[FILE SYSTEM] Successfully saved resume to: " + filePath.toAbsolutePath());
            return resume;
        } catch (IOException e) {
            throw new FileStorageException(filePath.toString(), "Failed to write resume to disk: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Resume> findById(String id) throws FileStorageException {
        Path filePath = storageDirectory.resolve("resume_" + id + ".json");
        if (!Files.exists(filePath)) {
            return Optional.empty();
        }

        // Concept 10: File Operations using BufferedReader & FileReader
        try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            Resume resume = objectMapper.readValue(reader, Resume.class);
            return Optional.ofNullable(resume);
        } catch (IOException e) {
            throw new FileStorageException(filePath.toString(), "Failed to read resume file: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Resume> findAll() throws FileStorageException {
        List<Resume> resumeList = new ArrayList<>(); // Concept 8: Collections (ArrayList)

        // Concept 10: File Operations using Directory Stream
        try (Stream<Path> stream = Files.list(storageDirectory)) {
            stream.filter(path -> path.toString().endsWith(".json"))
                  .forEach(path -> {
                      try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
                          Resume resume = objectMapper.readValue(reader, Resume.class);
                          resumeList.add(resume);
                      } catch (Exception ex) {
                          System.err.println("[FILE SYSTEM] Warning: failed to parse " + path + ": " + ex.getMessage());
                      }
                  });
            return resumeList;
        } catch (IOException e) {
            throw new FileStorageException(storageDirectory.toString(), "Failed to list resumes from disk", e);
        }
    }

    @Override
    public boolean deleteById(String id) throws FileStorageException, ResourceNotFoundException {
        Path filePath = storageDirectory.resolve("resume_" + id + ".json");
        if (!Files.exists(filePath)) {
            throw new ResourceNotFoundException("Resume", id);
        }
        try {
            return Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new FileStorageException(filePath.toString(), "Failed to delete file from disk", e);
        }
    }

    @Override
    public boolean existsById(String id) {
        Path filePath = storageDirectory.resolve("resume_" + id + ".json");
        return Files.exists(filePath);
    }
}
