// ==========================================================================
// SMART AI RESUME BUILDER - CLIENT SCRIPT
// Coordinates UI Animations, Themes, Layouts, and OOP Backend API Calls
// ==========================================================================

const API_BASE = "http://localhost:5000/api";

let experienceCount = 0;
let projectCount = 0;
let educationCount = 0;
let certificateCount = 0;
let achievementCount = 0;

let currentTheme = "modern";
let currentLayout = "single";

// Theme Palette Definitions for Instant Visual Response
const THEME_PALETTES = {
    modern: {
        primary: "#2563eb",
        gradient: "linear-gradient(135deg, #2563eb, #6366f1)",
        secondary: "#6366f1",
        light: "#eff6ff",
        border: "#bfdbfe",
        badge: "#dbeafe",
        textDark: "#0f172a"
    },
    classic: {
        primary: "#1e293b",
        gradient: "linear-gradient(135deg, #1e293b, #475569)",
        secondary: "#475569",
        light: "#f8fafc",
        border: "#cbd5e1",
        badge: "#e2e8f0",
        textDark: "#020617"
    },
    emerald: {
        primary: "#059669",
        gradient: "linear-gradient(135deg, #059669, #10b981)",
        secondary: "#10b981",
        light: "#ecfdf5",
        border: "#a7f3d0",
        badge: "#d1fae5",
        textDark: "#064e3b"
    },
    coral: {
        primary: "#e11d48",
        gradient: "linear-gradient(135deg, #e11d48, #f43f5e)",
        secondary: "#f43f5e",
        light: "#fff1f2",
        border: "#fecdd3",
        badge: "#ffe4e6",
        textDark: "#4c0519"
    },
    cyber: {
        primary: "#7c3aed",
        gradient: "linear-gradient(135deg, #7c3aed, #ec4899)",
        secondary: "#ec4899",
        light: "#f5f3ff",
        border: "#ddd6fe",
        badge: "#ede9fe",
        textDark: "#2e1065"
    },
    amber: {
        primary: "#d97706",
        gradient: "linear-gradient(135deg, #d97706, #f59e0b)",
        secondary: "#f59e0b",
        light: "#fffbeb",
        border: "#fde68a",
        badge: "#fef3c7",
        textDark: "#451a03"
    }
};

// ==========================================
// INITIALIZATION
// ==========================================

document.addEventListener("DOMContentLoaded", function () {
    console.log("🚀 Smart AI Resume Builder Initialized");

    // Check backend status immediately and keep checking every 3 seconds
    checkBackendHealth();
    setInterval(checkBackendHealth, 3000);

    // Auto-populate with sample student data so user immediately sees full content
    if (!getValue("fullName")) {
        loadSampleData();
    }
});

/**
 * Checks backend health and updates UI indicator
 */
async function checkBackendHealth() {
    const pill = document.getElementById("backendStatusPill");
    const text = document.getElementById("backendStatusText");

    try {
        const response = await fetch(`${API_BASE}/health`, { method: "GET" });
        if (response.ok) {
            const result = await response.json();
            const provider = result.data?.aiProvider || "Smart AI Backend";
            if (text) text.textContent = `${provider} • Online (Port 5000)`;
            if (pill) {
                pill.style.background = "rgba(16, 185, 129, 0.2)";
                pill.style.borderColor = "rgba(16, 185, 129, 0.6)";
            }
        } else {
            setOfflineStatus(text, pill);
        }
    } catch (e) {
        setOfflineStatus(text, pill);
    }
}

function setOfflineStatus(text, pill) {
    if (text && pill) {
        text.textContent = "Offline Mode (Run run-backend.bat)";
        pill.style.background = "rgba(245, 158, 11, 0.2)";
        pill.style.borderColor = "rgba(245, 158, 11, 0.5)";
    }
}

// ==========================================
// UTILITY FUNCTIONS
// ==========================================

function getValue(id) {
    const el = document.getElementById(id);
    return el ? el.value.trim() : "";
}

function setValue(id, val) {
    const el = document.getElementById(id);
    if (el) el.value = val || "";
}

function escapeHTML(str) {
    return String(str || "")
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/"/g, "&quot;")
        .replace(/'/g, "&#039;");
}

function showToast(message, type = "info") {
    const container = document.getElementById("toastContainer");
    if (!container) return;

    const toast = document.createElement("div");
    toast.className = `toast toast-${type}`;

    let icon = "fa-circle-info";
    if (type === "success") icon = "fa-circle-check";
    if (type === "error") icon = "fa-circle-xmark";
    if (type === "warning") icon = "fa-triangle-exclamation";

    toast.innerHTML = `<i class="fa-solid ${icon}"></i><span>${escapeHTML(message)}</span>`;
    container.appendChild(toast);

    setTimeout(() => {
        toast.classList.add("toast-closing");
        setTimeout(() => toast.remove(), 250);
    }, 3500);
}

// ==========================================
// DYNAMIC SECTION BUILDERS
// ==========================================

window.addExperience = function (role = "", company = "", duration = "", desc = "") {
    experienceCount++;
    const list = document.getElementById("experienceList");
    if (!list) return;

    const item = document.createElement("div");
    item.className = "dynamic-item";
    item.innerHTML = `
        <div class="dynamic-item-header">
            <h4><i class="fa-solid fa-briefcase"></i> Position ${experienceCount}</h4>
            <div class="dynamic-item-actions">
                <button type="button" class="ai-btn" style="padding:5px 10px; font-size:12px;" onclick="enhanceExperience(this)">
                    <i class="fa-solid fa-wand-magic-sparkles"></i> AI Polish
                </button>
                <button type="button" class="remove-btn" onclick="removeItem(this)">
                    <i class="fa-solid fa-trash"></i>
                </button>
            </div>
        </div>
        <div class="form-grid">
            <div class="form-group">
                <label>Job Title / Position</label>
                <input type="text" class="experience-role" placeholder="e.g. Software Engineer Intern" value="${escapeHTML(role)}">
            </div>
            <div class="form-group">
                <label>Company / Organization</label>
                <input type="text" class="experience-company" placeholder="e.g. Infosys / Google" value="${escapeHTML(company)}">
            </div>
            <div class="form-group">
                <label>Duration</label>
                <input type="text" class="experience-duration" placeholder="e.g. Jun 2025 - Present" value="${escapeHTML(duration)}">
            </div>
            <div class="form-group span-2">
                <label>Responsibilities & Technical Achievements</label>
                <textarea class="experience-description" rows="3" placeholder="Describe your technical contributions...">${escapeHTML(desc)}</textarea>
            </div>
        </div>
    `;
    list.appendChild(item);
};

window.addProject = function (title = "", tech = "", desc = "") {
    projectCount++;
    const list = document.getElementById("projectList");
    if (!list) return;

    const item = document.createElement("div");
    item.className = "dynamic-item";
    item.innerHTML = `
        <div class="dynamic-item-header">
            <h4><i class="fa-solid fa-laptop-code"></i> Project ${projectCount}</h4>
            <div class="dynamic-item-actions">
                <button type="button" class="ai-btn" style="padding:5px 10px; font-size:12px;" onclick="enhanceProject(this)">
                    <i class="fa-solid fa-wand-magic-sparkles"></i> AI Polish
                </button>
                <button type="button" class="remove-btn" onclick="removeItem(this)">
                    <i class="fa-solid fa-trash"></i>
                </button>
            </div>
        </div>
        <div class="form-grid">
            <div class="form-group">
                <label>Project Title</label>
                <input type="text" class="project-title" placeholder="e.g. Smart AI Resume Creator" value="${escapeHTML(title)}">
            </div>
            <div class="form-group">
                <label>Technologies Used</label>
                <input type="text" class="project-technologies" placeholder="Java 17, Spring Boot, MySQL, REST APIs" value="${escapeHTML(tech)}">
            </div>
            <div class="form-group span-2">
                <label>Project Description</label>
                <textarea class="project-description" rows="3" placeholder="Explain the problem solved, architecture, and impact...">${escapeHTML(desc)}</textarea>
            </div>
        </div>
    `;
    list.appendChild(item);
};

window.addEducation = function (degree = "", inst = "", year = "", grade = "") {
    educationCount++;
    const list = document.getElementById("educationList");
    if (!list) return;

    const item = document.createElement("div");
    item.className = "dynamic-item";
    item.innerHTML = `
        <div class="dynamic-item-header">
            <h4><i class="fa-solid fa-graduation-cap"></i> Degree ${educationCount}</h4>
            <button type="button" class="remove-btn" onclick="removeItem(this)">
                <i class="fa-solid fa-trash"></i>
            </button>
        </div>
        <div class="form-grid">
            <div class="form-group">
                <label>Degree / Qualification</label>
                <input type="text" class="education-degree" placeholder="e.g. B.Tech Computer Science & Engineering" value="${escapeHTML(degree)}">
            </div>
            <div class="form-group">
                <label>Institution / University</label>
                <input type="text" class="education-institution" placeholder="e.g. Karunya University" value="${escapeHTML(inst)}">
            </div>
            <div class="form-group">
                <label>Year of Passing</label>
                <input type="text" class="education-year" placeholder="e.g. 2024 - 2028" value="${escapeHTML(year)}">
            </div>
            <div class="form-group">
                <label>CGPA / Percentage</label>
                <input type="text" class="education-grade" placeholder="e.g. 8.9 CGPA / 89%" value="${escapeHTML(grade)}">
            </div>
        </div>
    `;
    list.appendChild(item);
};

window.addCertificate = function (name = "", org = "", year = "") {
    certificateCount++;
    const list = document.getElementById("certificateList");
    if (!list) return;

    const item = document.createElement("div");
    item.className = "dynamic-item";
    item.innerHTML = `
        <div class="dynamic-item-header">
            <h4><i class="fa-solid fa-certificate"></i> Certification ${certificateCount}</h4>
            <button type="button" class="remove-btn" onclick="removeItem(this)">
                <i class="fa-solid fa-trash"></i>
            </button>
        </div>
        <div class="form-grid">
            <div class="form-group">
                <label>Certification Name</label>
                <input type="text" class="certificate-name" placeholder="e.g. Oracle Certified Associate Java Programmer" value="${escapeHTML(name)}">
            </div>
            <div class="form-group">
                <label>Issuing Organization</label>
                <input type="text" class="certificate-organization" placeholder="e.g. Oracle / Coursera" value="${escapeHTML(org)}">
            </div>
            <div class="form-group">
                <label>Issue Year</label>
                <input type="text" class="certificate-year" placeholder="e.g. 2026" value="${escapeHTML(year)}">
            </div>
        </div>
    `;
    list.appendChild(item);
};

window.addAchievement = function (text = "") {
    achievementCount++;
    const list = document.getElementById("achievementList");
    if (!list) return;

    const item = document.createElement("div");
    item.className = "dynamic-item";
    item.innerHTML = `
        <div class="dynamic-item-header">
            <h4><i class="fa-solid fa-trophy"></i> Achievement ${achievementCount}</h4>
            <div class="dynamic-item-actions">
                <button type="button" class="ai-btn" style="padding:5px 10px; font-size:12px;" onclick="enhanceAchievement(this)">
                    <i class="fa-solid fa-wand-magic-sparkles"></i> AI Polish
                </button>
                <button type="button" class="remove-btn" onclick="removeItem(this)">
                    <i class="fa-solid fa-trash"></i>
                </button>
            </div>
        </div>
        <div class="form-group">
            <label>Achievement Description</label>
            <textarea class="achievement-text" rows="2" placeholder="e.g. Secured 1st place in National Smart India Hackathon among 500+ teams.">${escapeHTML(text)}</textarea>
        </div>
    `;
    list.appendChild(item);
};

window.removeItem = function (btn) {
    const item = btn.closest(".dynamic-item");
    if (item) {
        item.classList.add("removing");
        setTimeout(() => item.remove(), 250);
    }
};

// ==========================================
// AI ENHANCEMENTS
// ==========================================

window.enhanceSummary = async function () {
    const btn = document.getElementById("btnEnhanceSummary");
    const summaryEl = document.getElementById("summary");

    const name = getValue("fullName") || "Candidate";
    const goal = getValue("careerGoal") || "Software Engineer";
    const skills = getValue("technicalSkills") || "Java, Spring Boot, Web Technologies";
    const edu = getFirstEducationDegree();
    const current = summaryEl.value.trim();

    setLoadingState(btn, summaryEl, true, "Generating AI Summary...");

    try {
        const response = await fetch(`${API_BASE}/generate-summary`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ name, education: edu, skills, careerGoal: goal, summary: current })
        });

        const json = await response.json();
        if (json.success && json.data) {
            summaryEl.value = json.data.trim();
            showToast("Summary enhanced by AI!", "success");
            return;
        }
    } catch (err) {
        console.warn("Backend unavailable, using local smart enhancement.");
    } finally {
        setLoadingState(btn, summaryEl, false, '<i class="fa-solid fa-wand-magic-sparkles"></i> AI Enhance Summary');
    }

    summaryEl.value = `Motivated and results-driven ${edu} student aspiring to excel as a ${goal}. Proficient in ${skills} with a strong foundation in Object-Oriented Programming and modern software engineering principles. Adept at collaborative problem-solving, architectural design, and delivering scalable applications. Eager to contribute technical competence and innovation to high-impact projects.`;
    showToast("Summary enhanced with Smart AI rules!", "success");
};

window.enhanceExperience = async function (btn) {
    const item = btn.closest(".dynamic-item");
    if (!item) return;

    const role = item.querySelector(".experience-role")?.value.trim() || "Software Engineer Intern";
    const company = item.querySelector(".experience-company")?.value.trim() || "Tech Solutions";
    const descEl = item.querySelector(".experience-description");
    const current = descEl.value.trim();

    setLoadingState(btn, descEl, true, "Polishing...");

    try {
        const response = await fetch(`${API_BASE}/improve-experience`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ jobTitle: role, company: company, description: current })
        });

        const json = await response.json();
        if (json.success && json.data) {
            descEl.value = json.data.trim();
            showToast("Experience bullet points enhanced!", "success");
            return;
        }
    } catch (err) {
        console.warn("Backend unavailable, applying local STAR enhancement.");
    } finally {
        setLoadingState(btn, descEl, false, '<i class="fa-solid fa-wand-magic-sparkles"></i> AI Polish');
    }

    descEl.value = `• Spearheaded core software development at ${company} as ${role}, delivering maintainable code adhering to clean OOP principles.\n• Streamlined RESTful service execution and database access layers, improving response efficiency and system stability.\n• Collaborated closely in agile sprints, participating in systematic code reviews and unit test verification.`;
    showToast("Experience polished with STAR method!", "success");
};

window.enhanceProject = async function (btn) {
    const item = btn.closest(".dynamic-item");
    if (!item) return;

    const title = item.querySelector(".project-title")?.value.trim() || "Key Technical Project";
    const tech = item.querySelector(".project-technologies")?.value.trim() || "Java, Spring Boot, MySQL";
    const descEl = item.querySelector(".project-description");
    const current = descEl.value.trim();

    setLoadingState(btn, descEl, true, "Polishing...");

    try {
        const response = await fetch(`${API_BASE}/improve-project`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ projectName: title, technologies: tech, description: current })
        });

        const json = await response.json();
        if (json.success && json.data) {
            descEl.value = json.data.trim();
            showToast("Project description enhanced with action verbs!", "success");
            return;
        }
    } catch (err) {
        console.warn("Backend unavailable, applying local action-verb enhancement.");
    } finally {
        setLoadingState(btn, descEl, false, '<i class="fa-solid fa-wand-magic-sparkles"></i> AI Polish');
    }

    descEl.value = `• Architected and implemented '${title}' utilizing ${tech} to solve critical domain requirements.\n• Engineered a resilient, modular backend architecture featuring automated input validation, exception handling, and persistent data storage.\n• Optimized computational throughput and user interaction flows, ensuring high performance and responsive layout execution.`;
    showToast("Project enhanced with action verbs!", "success");
};

window.enhanceAchievement = async function (btn) {
    const item = btn.closest(".dynamic-item");
    if (!item) return;

    const textEl = item.querySelector(".achievement-text");
    const current = textEl.value.trim() || "National level hackathon achievement";
    setLoadingState(btn, textEl, true, "Polishing...");

    try {
        const response = await fetch(`${API_BASE}/improve-achievement`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ achievement: current })
        });

        const json = await response.json();
        if (json.success && json.data) {
            textEl.value = json.data.trim();
            showToast("Achievement bullet point polished!", "success");
            return;
        }
    } catch (err) {
        console.warn("Backend unavailable, applying local polish.");
    } finally {
        setLoadingState(btn, textEl, false, '<i class="fa-solid fa-wand-magic-sparkles"></i> AI Polish');
    }

    textEl.value = `Honored with competitive distinction: ${current}. Commended for algorithmic problem-solving, clean software architecture, and technical leadership.`;
    showToast("Achievement polished!", "success");
};

window.suggestSkills = async function () {
    const query = getValue("careerGoal") || getValue("fullName") || "Software Developer";
    const box = document.getElementById("aiSkillSuggestionsBox");
    const chipsContainer = document.getElementById("aiSkillChips");

    box.style.display = "flex";
    chipsContainer.innerHTML = '<span style="font-size:12px; color:#64748b;">Consulting AI recommendations...</span>';

    try {
        const response = await fetch(`${API_BASE}/suggest-skills`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ query: query })
        });

        const json = await response.json();
        if (json.success && Array.isArray(json.data)) {
            renderSkillChips(json.data, chipsContainer);
            showToast(`Loaded ${json.data.length} skill recommendations!`, "success");
            return;
        }
    } catch (err) {
        console.warn("Backend unavailable, loading local high-demand skills.");
    }

    const fallbackSkills = ["Java 17+", "Spring Boot", "REST APIs", "Microservices", "Docker", "SQL", "Git", "JUnit", "Data Structures", "OOP Design Patterns"];
    renderSkillChips(fallbackSkills, chipsContainer);
    showToast("Loaded high-demand industry skills!", "success");
};

function renderSkillChips(skills, container) {
    container.innerHTML = "";
    skills.forEach(skill => {
        const chip = document.createElement("button");
        chip.type = "button";
        chip.className = "skill-chip";
        chip.innerHTML = `<i class="fa-solid fa-plus"></i> ${escapeHTML(skill)}`;
        chip.onclick = () => addSkillToInput(skill, chip);
        container.appendChild(chip);
    });
}

function addSkillToInput(skill, chip) {
    const input = document.getElementById("technicalSkills");
    let current = input.value.trim();
    const skillsList = current.split(",").map(s => s.trim().toLowerCase());

    if (!skillsList.includes(skill.toLowerCase())) {
        input.value = current ? `${current}, ${skill}` : skill;
        chip.style.opacity = "0.5";
        chip.style.pointerEvents = "none";
        chip.innerHTML = `<i class="fa-solid fa-check"></i> ${escapeHTML(skill)}`;
        showToast(`Added '${skill}' to skills!`, "success");
    } else {
        showToast(`'${skill}' is already added.`, "info");
    }
}

function setLoadingState(btn, targetEl, isLoading, text) {
    if (btn) {
        btn.disabled = isLoading;
        btn.innerHTML = isLoading ? `<i class="fa-solid fa-spinner fa-spin-fast"></i> ${text}` : text;
    }
    if (targetEl) {
        if (isLoading) targetEl.classList.add("shimmer-loading");
        else targetEl.classList.remove("shimmer-loading");
    }
}

function getFirstEducationDegree() {
    const firstDeg = document.querySelector("#educationList .education-degree");
    return firstDeg ? firstDeg.value.trim() : "Computer Science";
}

// ==========================================
// RESUME AGGREGATION & GENERATION
// ==========================================

function buildResumeObject() {
    const resume = {
        title: getValue("fullName") || "Untitled Resume",
        personalDetails: {
            fullName: getValue("fullName"),
            email: getValue("email"),
            phone: getValue("phone"),
            location: getValue("location"),
            linkedin: getValue("linkedin"),
            github: getValue("github"),
            careerGoal: getValue("careerGoal"),
            summary: getValue("summary")
        },
        technicalSkillsRaw: getValue("technicalSkills"),
        experiences: [],
        projects: [],
        educations: [],
        certifications: [],
        achievements: [],
        stylePreferences: {
            theme: currentTheme,
            layout: currentLayout
        }
    };

    // Experiences
    document.querySelectorAll("#experienceList .dynamic-item").forEach(item => {
        const role = item.querySelector(".experience-role")?.value.trim() || "";
        const company = item.querySelector(".experience-company")?.value.trim() || "";
        const duration = item.querySelector(".experience-duration")?.value.trim() || "";
        const desc = item.querySelector(".experience-description")?.value.trim() || "";
        if (role || company || desc) {
            resume.experiences.push({ role, company, duration, description: desc });
        }
    });

    // Projects
    document.querySelectorAll("#projectList .dynamic-item").forEach(item => {
        const title = item.querySelector(".project-title")?.value.trim() || "";
        const tech = item.querySelector(".project-technologies")?.value.trim() || "";
        const desc = item.querySelector(".project-description")?.value.trim() || "";
        if (title || tech || desc) {
            resume.projects.push({ title, technologies: tech, description: desc });
        }
    });

    // Educations
    document.querySelectorAll("#educationList .dynamic-item").forEach(item => {
        const degree = item.querySelector(".education-degree")?.value.trim() || "";
        const institution = item.querySelector(".education-institution")?.value.trim() || "";
        const year = item.querySelector(".education-year")?.value.trim() || "";
        const grade = item.querySelector(".education-grade")?.value.trim() || "";
        if (degree || institution) {
            resume.educations.push({ degree, institution, year, grade });
        }
    });

    // Certifications
    document.querySelectorAll("#certificateList .dynamic-item").forEach(item => {
        const name = item.querySelector(".certificate-name")?.value.trim() || "";
        const org = item.querySelector(".certificate-organization")?.value.trim() || "";
        const year = item.querySelector(".certificate-year")?.value.trim() || "";
        if (name) {
            resume.certifications.push({ name, organization: org, year });
        }
    });

    // Achievements
    document.querySelectorAll("#achievementList .dynamic-item").forEach(item => {
        const text = item.querySelector(".achievement-text")?.value.trim() || "";
        if (text) {
            resume.achievements.push({ achievementText: text });
        }
    });

    return resume;
}

window.generateResume = function () {
    const resume = buildResumeObject();
    renderResumeDocument(resume);

    document.getElementById("formView").classList.add("hidden-view");
    document.getElementById("formView").classList.remove("active-view");

    document.getElementById("outputView").classList.remove("hidden-view");
    document.getElementById("outputView").classList.add("active-view");

    window.scrollTo({ top: 0, behavior: "smooth" });
    showToast("Resume generated successfully!", "success");
};

/**
 * Render Resume Document according to Layout and Theme
 */
function renderResumeDocument(resume) {
    const sheet = document.getElementById("resumeDocument");
    if (!sheet) return;

    // Apply layout and theme classes to sheet
    sheet.className = `resume-sheet layout-${currentLayout} theme-${currentTheme}`;

    // Apply inline CSS variables to guarantee instant visual response
    const palette = THEME_PALETTES[currentTheme] || THEME_PALETTES.modern;
    sheet.style.setProperty("--theme-primary", palette.primary);
    sheet.style.setProperty("--theme-gradient", palette.gradient);
    sheet.style.setProperty("--theme-secondary", palette.secondary);
    sheet.style.setProperty("--theme-light", palette.light);
    sheet.style.setProperty("--theme-border", palette.border);
    sheet.style.setProperty("--theme-badge", palette.badge);
    sheet.style.setProperty("--theme-text-dark", palette.textDark);

    const p = resume.personalDetails;
    let contactItems = [];
    if (p.email) contactItems.push(`<span><i class="fa-solid fa-envelope"></i> ${escapeHTML(p.email)}</span>`);
    if (p.phone) contactItems.push(`<span><i class="fa-solid fa-phone"></i> ${escapeHTML(p.phone)}</span>`);
    if (p.location) contactItems.push(`<span><i class="fa-solid fa-location-dot"></i> ${escapeHTML(p.location)}</span>`);
    if (p.linkedin) contactItems.push(`<span><i class="fa-brands fa-linkedin"></i> ${escapeHTML(p.linkedin)}</span>`);
    if (p.github) contactItems.push(`<span><i class="fa-brands fa-github"></i> ${escapeHTML(p.github)}</span>`);

    const skillsArray = resume.technicalSkillsRaw
        ? resume.technicalSkillsRaw.split(",").map(s => s.trim()).filter(Boolean)
        : [];

    if (currentLayout === "twocolumn") {
        renderTwoColumnLayout(sheet, p, contactItems, skillsArray, resume);
    } else if (currentLayout === "timeline") {
        renderTimelineLayout(sheet, p, contactItems, skillsArray, resume);
    } else if (currentLayout === "executive") {
        renderExecutiveLayout(sheet, p, contactItems, skillsArray, resume);
    } else {
        renderSingleOrMinimalLayout(sheet, p, contactItems, skillsArray, resume);
    }
}

function renderSingleOrMinimalLayout(sheet, p, contactItems, skillsArray, resume) {
    let html = `
        <header class="resume-header">
            <h1 style="color:var(--theme-primary);">${escapeHTML(p.fullName)}</h1>
            ${p.careerGoal ? `<div style="font-size:15px; font-weight:700; color:var(--theme-primary); margin-bottom:8px;">${escapeHTML(p.careerGoal)}</div>` : ""}
            <div class="contact-info">
                ${contactItems.join("")}
            </div>
        </header>
    `;

    if (p.summary) {
        html += `
            <section class="resume-section">
                <h2 class="section-title"><i class="fa-solid fa-user-tie"></i> Professional Summary</h2>
                <p class="resume-desc">${escapeHTML(p.summary)}</p>
            </section>
        `;
    }

    if (resume.experiences.length > 0) {
        html += `<section class="resume-section"><h2 class="section-title"><i class="fa-solid fa-briefcase"></i> Work Experience</h2>`;
        resume.experiences.forEach(exp => {
            html += `
                <div class="resume-item">
                    <div class="resume-item-top">
                        <h3>${escapeHTML(exp.role)}</h3>
                        <span class="resume-duration">${escapeHTML(exp.duration)}</span>
                    </div>
                    <div class="resume-meta">${escapeHTML(exp.company)}</div>
                    <p class="resume-desc">${escapeHTML(exp.description)}</p>
                </div>
            `;
        });
        html += `</section>`;
    }

    if (resume.projects.length > 0) {
        html += `<section class="resume-section"><h2 class="section-title"><i class="fa-solid fa-laptop-code"></i> Technical Projects</h2>`;
        resume.projects.forEach(proj => {
            html += `
                <div class="resume-item">
                    <div class="resume-item-top">
                        <h3>${escapeHTML(proj.title)}</h3>
                    </div>
                    ${proj.technologies ? `<div class="resume-meta">Technologies: ${escapeHTML(proj.technologies)}</div>` : ""}
                    <p class="resume-desc">${escapeHTML(proj.description)}</p>
                </div>
            `;
        });
        html += `</section>`;
    }

    if (resume.educations.length > 0) {
        html += `<section class="resume-section"><h2 class="section-title"><i class="fa-solid fa-graduation-cap"></i> Education</h2>`;
        resume.educations.forEach(edu => {
            html += `
                <div class="resume-item">
                    <div class="resume-item-top">
                        <h3>${escapeHTML(edu.degree)}</h3>
                        <span class="resume-duration">${escapeHTML(edu.year)}</span>
                    </div>
                    <div class="resume-meta">${escapeHTML(edu.institution)} ${edu.grade ? " • " + escapeHTML(edu.grade) : ""}</div>
                </div>
            `;
        });
        html += `</section>`;
    }

    if (skillsArray.length > 0) {
        html += `
            <section class="resume-section">
                <h2 class="section-title"><i class="fa-solid fa-code"></i> Technical Skills</h2>
                <div class="skills-tags">
                    ${skillsArray.map(s => `<span class="skill-tag">${escapeHTML(s)}</span>`).join("")}
                </div>
            </section>
        `;
    }

    if (resume.certifications.length > 0) {
        html += `
            <section class="resume-section">
                <h2 class="section-title"><i class="fa-solid fa-certificate"></i> Certifications</h2>
                <ul class="resume-list">
                    ${resume.certifications.map(c => `<li><strong>${escapeHTML(c.name)}</strong> - ${escapeHTML(c.organization)} (${escapeHTML(c.year)})</li>`).join("")}
                </ul>
            </section>
        `;
    }

    if (resume.achievements.length > 0) {
        html += `
            <section class="resume-section">
                <h2 class="section-title"><i class="fa-solid fa-trophy"></i> Honors & Achievements</h2>
                <ul class="resume-list">
                    ${resume.achievements.map(a => `<li>${escapeHTML(a.achievementText)}</li>`).join("")}
                </ul>
            </section>
        `;
    }

    sheet.innerHTML = html;
}

function renderTwoColumnLayout(sheet, p, contactItems, skillsArray, resume) {
    // Generate Initials Avatar
    const initials = (p.fullName || "A").split(" ").map(n => n[0]).join("").substring(0, 2).toUpperCase();

    let sidebarHtml = `
        <div class="sidebar-col">
            <div style="text-align:center; margin-bottom:20px;">
                <div style="width:65px; height:65px; border-radius:50%; background:var(--theme-primary); color:white; display:flex; align-items:center; justify-content:center; font-size:22px; font-weight:800; margin:0 auto 10px; box-shadow:0 6px 18px rgba(0,0,0,0.15);">${initials}</div>
                <div style="font-weight:800; font-size:16px; color:var(--theme-text-dark);">${escapeHTML(p.fullName)}</div>
                <div style="font-size:12px; color:var(--theme-primary); font-weight:600;">${escapeHTML(p.careerGoal)}</div>
            </div>

            <section class="resume-section">
                <h2 class="section-title">Contact</h2>
                <div style="display:flex; flex-direction:column; gap:10px; font-size:12px; color:#475569;">
                    ${contactItems.map(c => `<div>${c}</div>`).join("")}
                </div>
            </section>
    `;

    if (skillsArray.length > 0) {
        sidebarHtml += `
            <section class="resume-section">
                <h2 class="section-title">Skills</h2>
                <div class="skills-tags">
                    ${skillsArray.map(s => `<span class="skill-tag">${escapeHTML(s)}</span>`).join("")}
                </div>
            </section>
        `;
    }

    if (resume.educations.length > 0) {
        sidebarHtml += `
            <section class="resume-section">
                <h2 class="section-title">Education</h2>
                ${resume.educations.map(edu => `
                    <div style="margin-bottom:12px;">
                        <div style="font-weight:700; font-size:12.5px; color:var(--theme-text-dark);">${escapeHTML(edu.degree)}</div>
                        <div style="font-size:11.5px; color:#64748b;">${escapeHTML(edu.institution)}</div>
                        <div style="font-size:11px; color:var(--theme-primary); font-weight:600;">${escapeHTML(edu.year)} | ${escapeHTML(edu.grade)}</div>
                    </div>
                `).join("")}
            </section>
        `;
    }

    sidebarHtml += `</div>`;

    let mainHtml = `
        <div class="main-col">
            <header class="resume-header" style="border-bottom:2px solid var(--theme-border); padding-bottom:14px; margin-bottom:20px;">
                <h1 style="color:var(--theme-primary); font-size:32px; font-weight:800;">${escapeHTML(p.fullName)}</h1>
                <div style="font-size:15px; color:#64748b; font-weight:600; margin-top:2px;">${escapeHTML(p.careerGoal)}</div>
            </header>
    `;

    if (p.summary) {
        mainHtml += `
            <section class="resume-section">
                <h2 class="section-title">Summary</h2>
                <p class="resume-desc">${escapeHTML(p.summary)}</p>
            </section>
        `;
    }

    if (resume.experiences.length > 0) {
        mainHtml += `<section class="resume-section"><h2 class="section-title">Experience</h2>`;
        resume.experiences.forEach(exp => {
            mainHtml += `
                <div class="resume-item">
                    <div class="resume-item-top">
                        <h3>${escapeHTML(exp.role)}</h3>
                        <span class="resume-duration">${escapeHTML(exp.duration)}</span>
                    </div>
                    <div class="resume-meta">${escapeHTML(exp.company)}</div>
                    <p class="resume-desc">${escapeHTML(exp.description)}</p>
                </div>
            `;
        });
        mainHtml += `</section>`;
    }

    if (resume.projects.length > 0) {
        mainHtml += `<section class="resume-section"><h2 class="section-title">Projects</h2>`;
        resume.projects.forEach(proj => {
            mainHtml += `
                <div class="resume-item">
                    <h3>${escapeHTML(proj.title)}</h3>
                    ${proj.technologies ? `<div class="resume-meta">Technologies: ${escapeHTML(proj.technologies)}</div>` : ""}
                    <p class="resume-desc">${escapeHTML(proj.description)}</p>
                </div>
            `;
        });
        mainHtml += `</section>`;
    }

    if (resume.certifications.length > 0) {
        mainHtml += `
            <section class="resume-section">
                <h2 class="section-title">Certifications</h2>
                <ul class="resume-list">
                    ${resume.certifications.map(c => `<li><strong>${escapeHTML(c.name)}</strong> - ${escapeHTML(c.organization)} (${escapeHTML(c.year)})</li>`).join("")}
                </ul>
            </section>
        `;
    }

    mainHtml += `</div>`;

    sheet.innerHTML = `<div class="twocolumn-grid">${sidebarHtml}${mainHtml}</div>`;
}

function renderTimelineLayout(sheet, p, contactItems, skillsArray, resume) {
    let html = `
        <header class="resume-header">
            <h1 style="color:var(--theme-primary);">${escapeHTML(p.fullName)}</h1>
            ${p.careerGoal ? `<div style="font-size:16px; color:var(--theme-primary); font-weight:700; margin-bottom:8px;">${escapeHTML(p.careerGoal)}</div>` : ""}
            <div class="contact-info">
                ${contactItems.join("")}
            </div>
        </header>
    `;

    if (p.summary) {
        html += `
            <section class="resume-section">
                <h2 class="section-title"><i class="fa-solid fa-user-tie"></i> Professional Summary</h2>
                <p class="resume-desc" style="line-height:1.7; color:#334155;">${escapeHTML(p.summary)}</p>
            </section>
        `;
    }

    if (resume.experiences.length > 0) {
        html += `
            <section class="resume-section">
                <h2 class="section-title"><i class="fa-solid fa-briefcase"></i> Experience Timeline</h2>
                <div class="timeline-container">
        `;
        resume.experiences.forEach(exp => {
            html += `
                <div class="timeline-item resume-item">
                    <div class="resume-item-top">
                        <h3>${escapeHTML(exp.role)}</h3>
                        <span class="resume-duration">${escapeHTML(exp.duration)}</span>
                    </div>
                    <div class="resume-meta">${escapeHTML(exp.company)}</div>
                    <p class="resume-desc">${escapeHTML(exp.description)}</p>
                </div>
            `;
        });
        html += `</div></section>`;
    }

    if (resume.projects.length > 0) {
        html += `
            <section class="resume-section">
                <h2 class="section-title"><i class="fa-solid fa-laptop-code"></i> Projects Timeline</h2>
                <div class="timeline-container">
        `;
        resume.projects.forEach(proj => {
            html += `
                <div class="timeline-item resume-item">
                    <div class="resume-item-top">
                        <h3>${escapeHTML(proj.title)}</h3>
                    </div>
                    ${proj.technologies ? `<div class="resume-meta">Technologies: ${escapeHTML(proj.technologies)}</div>` : ""}
                    <p class="resume-desc">${escapeHTML(proj.description)}</p>
                </div>
            `;
        });
        html += `</div></section>`;
    }

    if (resume.educations.length > 0) {
        html += `
            <section class="resume-section">
                <h2 class="section-title"><i class="fa-solid fa-graduation-cap"></i> Education</h2>
                <div class="timeline-container">
        `;
        resume.educations.forEach(edu => {
            html += `
                <div class="timeline-item resume-item">
                    <div class="resume-item-top">
                        <h3>${escapeHTML(edu.degree)}</h3>
                        <span class="resume-duration">${escapeHTML(edu.year)}</span>
                    </div>
                    <div class="resume-meta">${escapeHTML(edu.institution)} ${edu.grade ? " • " + escapeHTML(edu.grade) : ""}</div>
                </div>
            `;
        });
        html += `</div></section>`;
    }

    if (skillsArray.length > 0) {
        html += `
            <section class="resume-section">
                <h2 class="section-title"><i class="fa-solid fa-code"></i> Technical Stack & Competencies</h2>
                <div class="skills-tags">
                    ${skillsArray.map(s => `<span class="skill-tag">${escapeHTML(s)}</span>`).join("")}
                </div>
            </section>
        `;
    }

    if (resume.certifications.length > 0) {
        html += `
            <section class="resume-section">
                <h2 class="section-title"><i class="fa-solid fa-certificate"></i> Certifications</h2>
                <ul class="resume-list">
                    ${resume.certifications.map(c => `<li><strong>${escapeHTML(c.name)}</strong> - ${escapeHTML(c.organization)} (${escapeHTML(c.year)})</li>`).join("")}
                </ul>
            </section>
        `;
    }

    if (resume.achievements.length > 0) {
        html += `
            <section class="resume-section">
                <h2 class="section-title"><i class="fa-solid fa-trophy"></i> Key Achievements</h2>
                <ul class="resume-list">
                    ${resume.achievements.map(a => `<li>${escapeHTML(a.achievementText)}</li>`).join("")}
                </ul>
            </section>
        `;
    }

    sheet.innerHTML = html;
}

function renderExecutiveLayout(sheet, p, contactItems, skillsArray, resume) {
    let html = `
        <header class="resume-header">
            <h1>${escapeHTML(p.fullName)}</h1>
            ${p.careerGoal ? `<div class="executive-goal">${escapeHTML(p.careerGoal)}</div>` : ""}
            <div class="contact-info">
                ${contactItems.join("")}
            </div>
        </header>
    `;

    if (p.summary) {
        html += `
            <section class="resume-section">
                <h2 class="section-title"><i class="fa-solid fa-user-tie"></i> Executive Profile</h2>
                <p class="resume-desc">${escapeHTML(p.summary)}</p>
            </section>
        `;
    }

    if (resume.experiences.length > 0) {
        html += `<section class="resume-section"><h2 class="section-title"><i class="fa-solid fa-briefcase"></i> Professional Experience</h2>`;
        resume.experiences.forEach(exp => {
            html += `
                <div class="resume-item">
                    <div class="resume-item-top">
                        <h3>${escapeHTML(exp.role)}</h3>
                        <span style="font-size:12px; font-weight:600; color:var(--theme-primary); background:var(--theme-light); padding:3px 10px; border-radius:12px;">${escapeHTML(exp.duration)}</span>
                    </div>
                    <div class="resume-meta">${escapeHTML(exp.company)}</div>
                    <p class="resume-desc">${escapeHTML(exp.description)}</p>
                </div>
            `;
        });
        html += `</section>`;
    }

    if (resume.projects.length > 0) {
        html += `<section class="resume-section"><h2 class="section-title"><i class="fa-solid fa-laptop-code"></i> Key Technical Projects</h2>`;
        resume.projects.forEach(proj => {
            html += `
                <div class="resume-item">
                    <div class="resume-item-top">
                        <h3>${escapeHTML(proj.title)}</h3>
                    </div>
                    ${proj.technologies ? `<div class="resume-meta">Technologies: ${escapeHTML(proj.technologies)}</div>` : ""}
                    <p class="resume-desc">${escapeHTML(proj.description)}</p>
                </div>
            `;
        });
        html += `</section>`;
    }

    if (skillsArray.length > 0) {
        html += `
            <section class="resume-section">
                <h2 class="section-title"><i class="fa-solid fa-code"></i> Technical Expertise</h2>
                <div class="skills-tags">
                    ${skillsArray.map(s => `<span class="skill-tag">${escapeHTML(s)}</span>`).join("")}
                </div>
            </section>
        `;
    }

    if (resume.educations.length > 0) {
        html += `<section class="resume-section"><h2 class="section-title"><i class="fa-solid fa-graduation-cap"></i> Academic Background</h2>`;
        resume.educations.forEach(edu => {
            html += `
                <div class="resume-item">
                    <div class="resume-item-top">
                        <h3>${escapeHTML(edu.degree)}</h3>
                        <span style="font-size:12px; color:#64748b; font-weight:600;">${escapeHTML(edu.year)}</span>
                    </div>
                    <div class="resume-meta">${escapeHTML(edu.institution)} ${edu.grade ? " • " + escapeHTML(edu.grade) : ""}</div>
                </div>
            `;
        });
        html += `</section>`;
    }

    if (resume.certifications.length > 0) {
        html += `
            <section class="resume-section">
                <h2 class="section-title"><i class="fa-solid fa-certificate"></i> Certifications</h2>
                <ul class="resume-list">
                    ${resume.certifications.map(c => `<li><strong>${escapeHTML(c.name)}</strong> - ${escapeHTML(c.organization)} (${escapeHTML(c.year)})</li>`).join("")}
                </ul>
            </section>
        `;
    }

    if (resume.achievements.length > 0) {
        html += `
            <section class="resume-section">
                <h2 class="section-title"><i class="fa-solid fa-trophy"></i> Key Achievements</h2>
                <ul class="resume-list">
                    ${resume.achievements.map(a => `<li>${escapeHTML(a.achievementText)}</li>`).join("")}
                </ul>
            </section>
        `;
    }

    sheet.innerHTML = html;
}

// ==========================================
// THEMES & LAYOUT SWITCHERS (INSTANT VISUAL UPDATE)
// ==========================================

window.setTheme = function (theme) {
    currentTheme = theme;
    document.body.className = `theme-${theme}`;

    document.querySelectorAll(".theme-btn").forEach(btn => {
        btn.classList.toggle("active", btn.getAttribute("data-theme") === theme);
    });

    const doc = document.getElementById("resumeDocument");
    if (doc) {
        renderResumeDocument(buildResumeObject());
    }
    showToast(`Switched to '${theme.toUpperCase()}' Theme!`, "info");
};

window.setLayout = function (layout) {
    currentLayout = layout;

    document.querySelectorAll(".layout-btn").forEach(btn => {
        btn.classList.toggle("active", btn.getAttribute("data-layout") === layout);
    });

    const doc = document.getElementById("resumeDocument");
    if (doc) {
        renderResumeDocument(buildResumeObject());
    }
    showToast(`Switched to '${layout.toUpperCase()}' Pattern!`, "info");
};

window.showFormView = function () {
    document.getElementById("outputView").classList.add("hidden-view");
    document.getElementById("outputView").classList.remove("active-view");

    document.getElementById("formView").classList.remove("hidden-view");
    document.getElementById("formView").classList.add("active-view");

    window.scrollTo({ top: 0, behavior: "smooth" });
};

// ==========================================
// ATS SCORE ANALYSIS MODAL & CALCULATOR
// ==========================================

window.openAtsModal = async function () {
    const modal = document.getElementById("atsModal");
    modal.classList.add("active");

    const resume = buildResumeObject();

    try {
        const response = await fetch(`${API_BASE}/ats-score`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(resume)
        });

        if (response.ok) {
            const json = await response.json();
            if (json.success && json.data) {
                renderAtsModalData(json.data);
                return;
            }
        }
    } catch (e) {
        console.info("Backend offline. Running instant local ATS analysis engine.");
    }

    const localResult = calculateLocalAtsScore(resume);
    renderAtsModalData(localResult);
};

window.closeAtsModal = function () {
    document.getElementById("atsModal").classList.remove("active");
};

function calculateLocalAtsScore(resume) {
    let score = 0;
    const catScores = {};
    const strengths = [];
    const improvements = [];

    const p = resume.personalDetails || {};

    // 1. Contact Info (20 pts)
    let contactPts = 0;
    if (p.fullName && p.fullName.trim() && p.fullName !== "Your Name") {
        contactPts += 5;
    } else {
        improvements.push("Add your full name in the Personal Details section.");
    }

    if (p.email && p.email.includes("@")) {
        contactPts += 5;
    } else {
        improvements.push("Provide a valid email address for recruiter outreach.");
    }

    if (p.phone && p.phone.trim().length >= 7) {
        contactPts += 4;
    } else {
        improvements.push("Add a contact phone number so hiring managers can reach you.");
    }

    if (p.location && p.location.trim().length > 0) {
        contactPts += 3;
    } else {
        improvements.push("Include your location/city to match regional recruiter searches.");
    }

    if (p.linkedin && p.linkedin.trim().length > 0) {
        contactPts += 3;
    } else {
        improvements.push("Add your LinkedIn profile link to improve recruiter confidence.");
    }

    if (contactPts === 20) {
        strengths.push("Complete personal details with valid contact and profile links.");
    }

    catScores["Personal Details & Contact"] = contactPts * 5;
    score += contactPts;

    // 2. Summary (15 pts)
    let summaryPts = 0;
    const summaryText = p.summary || "";
    const words = summaryText.trim().split(/\s+/).filter(Boolean).length;
    if (words >= 25 && words <= 120) {
        summaryPts = 15;
        strengths.push(`Professional summary length is optimal (${words} words).`);
    } else if (words > 0) {
        summaryPts = 8;
        improvements.push("Expand your professional summary to 3-4 sentences to highlight career goals.");
    } else {
        improvements.push("Add a professional summary using the 'AI Enhance' button to communicate your value.");
    }
    catScores["Professional Summary"] = Math.round(summaryPts * (100 / 15));
    score += summaryPts;

    // 3. Technical Skills (20 pts)
    const rawSkills = resume.technicalSkillsRaw || "";
    const skillList = rawSkills.split(",").map(s => s.trim()).filter(Boolean);
    let skillPts = 0;
    if (skillList.length >= 7) {
        skillPts = 20;
        strengths.push(`Strong keyword breadth with ${skillList.length} relevant technical skills.`);
    } else if (skillList.length >= 3) {
        skillPts = 12;
        improvements.push("Add more relevant technical tools, languages, and frameworks using 'AI Suggest Skills'.");
    } else {
        skillPts = 4;
        improvements.push("Crucial: Add core technical skills (Java, SQL, Git, React, Docker) for automated filters.");
    }
    catScores["Technical Skills & Keywords"] = Math.round(skillPts * 5);
    score += skillPts;

    // 4. Projects (20 pts)
    let projPts = 0;
    const projs = resume.projects || [];
    if (projs.length >= 2) {
        projPts = 20;
        strengths.push(`Impressive project portfolio with ${projs.length} detailed technical projects.`);
    } else if (projs.length === 1) {
        projPts = 12;
        improvements.push("Add a second project to demonstrate technical versatility across domains.");
    } else {
        improvements.push("Add at least 1-2 major software projects with technologies used.");
    }
    catScores["Technical Projects"] = Math.round(projPts * 5);
    score += projPts;

    // 5. Work Experience (15 pts)
    let expPts = 0;
    const exps = resume.experiences || [];
    if (exps.length >= 1) {
        expPts = 15;
        strengths.push("Professional internship/work experience included.");
    } else {
        expPts = 8;
        improvements.push("If you have completed internships, open-source work, or hackathons, add them under Experience.");
    }
    catScores["Work Experience"] = Math.round(expPts * (100 / 15));
    score += expPts;

    // 6. Education & Certifications (10 pts)
    let eduPts = 0;
    if ((resume.educations || []).length > 0) eduPts += 6;
    if ((resume.certifications || []).length > 0) {
        eduPts += 4;
        strengths.push("Industry certifications verified, validating your domain expertise.");
    } else {
        improvements.push("Add industry certifications (Oracle, AWS, Coursera) to boost credibility.");
    }
    catScores["Education & Certifications"] = eduPts * 10;
    score += eduPts;

    let grade = "C (Needs Polish)";
    let summaryFeedback = "Resume is incomplete. Fill in missing sections and click 'AI Enhance' to pass automated screening.";
    if (score >= 85) {
        grade = "A+ (ATS Ready)";
        summaryFeedback = "Outstanding! Well-structured, keyword-rich, and ready to pass top company ATS filters.";
    } else if (score >= 70) {
        grade = "B+ (Good Potential)";
        summaryFeedback = "Strong foundation! Implement the recommended tips below to reach top percentile.";
    }

    return {
        overallScore: score,
        grade: grade,
        summaryFeedback: summaryFeedback,
        categoryScores: catScores,
        strengths: strengths,
        improvements: improvements
    };
}

function renderAtsModalData(data) {
    const score = Math.round(data.overallScore || 0);
    const circle = document.getElementById("atsScoreCircle");
    document.getElementById("atsScoreNum").textContent = score;
    document.getElementById("atsGrade").textContent = data.grade || "Ready";
    document.getElementById("atsFeedback").textContent = data.summaryFeedback || "";

    circle.className = "ats-score-circle";
    if (score >= 80) circle.classList.add("score-high");
    else if (score >= 60) circle.classList.add("score-medium");
    else circle.classList.add("score-low");

    const catList = document.getElementById("atsCategoryList");
    catList.innerHTML = "";
    if (data.categoryScores) {
        for (const [cat, val] of Object.entries(data.categoryScores)) {
            const row = document.createElement("div");
            row.className = "ats-cat-row";
            row.innerHTML = `
                <div class="ats-cat-label">
                    <span>${escapeHTML(cat)}</span>
                    <span>${val}%</span>
                </div>
                <div class="ats-progress-bar">
                    <div class="ats-progress-fill" style="width: ${val}%;"></div>
                </div>
            `;
            catList.appendChild(row);
        }
    }

    const strList = document.getElementById("atsStrengthsList");
    strList.innerHTML = (data.strengths && data.strengths.length > 0)
        ? data.strengths.map(s => `<li>${escapeHTML(s)}</li>`).join("")
        : "<li>Complete all sections to unlock strengths.</li>";

    const impList = document.getElementById("atsImprovementsList");
    impList.innerHTML = (data.improvements && data.improvements.length > 0)
        ? data.improvements.map(i => `<li>${escapeHTML(i)}</li>`).join("")
        : "<li>Great job! Resume adheres to high ATS compatibility.</li>";
}

// ==========================================
// FILE OPERATIONS (SAVE, LOAD, EXPORT)
// ==========================================

window.saveToBackendDisk = async function () {
    const resume = buildResumeObject();

    try {
        const response = await fetch(`${API_BASE}/resume/save`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(resume)
        });

        const json = await response.json();
        if (json.success) {
            showToast(`Resume safely stored on disk (ID: ${json.data.id.substring(0, 8)})!`, "success");
            return;
        }
    } catch (e) {
        console.warn("Backend disk storage unavailable.");
    }

    localStorage.setItem("saved_resume_backup", JSON.stringify(resume));
    showToast("Resume saved to browser storage (run backend to save to /saved_resumes/ disk).", "info");
};

window.openLoadModal = async function () {
    const modal = document.getElementById("loadModal");
    modal.classList.add("active");
    const container = document.getElementById("savedResumesList");
    container.innerHTML = '<p style="text-align:center; color:#94a3b8; padding:15px;">Loading from /saved_resumes/ ...</p>';

    try {
        const response = await fetch(`${API_BASE}/resume/list`);
        const json = await response.json();
        if (json.success && Array.isArray(json.data) && json.data.length > 0) {
            container.innerHTML = "";
            json.data.forEach(r => {
                const name = r.personalDetails?.fullName || "Untitled Resume";
                const date = new Date(r.lastUpdated).toLocaleDateString();
                const card = document.createElement("div");
                card.style = "display:flex; justify-content:space-between; align-items:center; background:#f8fafc; padding:12px; border-radius:8px; border:1px solid #e2e8f0;";
                card.innerHTML = `
                    <div>
                        <div style="font-weight:700; font-size:13.5px; color:#0f172a;">${escapeHTML(name)}</div>
                        <div style="font-size:11px; color:#64748b;">Updated: ${date}</div>
                    </div>
                    <button type="button" class="tool-btn" style="padding:6px 12px; font-size:12px;" onclick="loadResumeById('${r.id}')">
                        <i class="fa-solid fa-download"></i> Load
                    </button>
                `;
                container.appendChild(card);
            });
            return;
        }
    } catch (e) {
        console.warn("Backend list unavailable.");
    }

    const backup = localStorage.getItem("saved_resume_backup");
    if (backup) {
        container.innerHTML = `
            <div style="display:flex; justify-content:space-between; align-items:center; background:#f8fafc; padding:12px; border-radius:8px; border:1px solid #e2e8f0;">
                <div>
                    <div style="font-weight:700; font-size:13.5px; color:#0f172a;">Browser Saved Backup</div>
                    <div style="font-size:11px; color:#64748b;">Local Session</div>
                </div>
                <button type="button" class="tool-btn" style="padding:6px 12px; font-size:12px;" onclick="loadBackup()">
                    <i class="fa-solid fa-download"></i> Load
                </button>
            </div>
        `;
    } else {
        container.innerHTML = '<p style="text-align:center; color:#64748b; padding:20px;">No saved resumes found. Click "Save to Disk" to create one.</p>';
    }
};

window.closeLoadModal = function () {
    document.getElementById("loadModal").classList.remove("active");
};

window.loadBackup = function () {
    const backup = localStorage.getItem("saved_resume_backup");
    if (backup) {
        populateFormWithData(JSON.parse(backup));
        closeLoadModal();
        showToast("Loaded saved resume backup!", "success");
    }
};

window.loadResumeById = async function (id) {
    try {
        const response = await fetch(`${API_BASE}/resume/load/${id}`);
        const json = await response.json();
        if (json.success && json.data) {
            populateFormWithData(json.data);
            closeLoadModal();
            showToast("Resume loaded from disk successfully!", "success");
        }
    } catch (e) {
        showToast("Failed to load resume file.", "error");
    }
};

function populateFormWithData(data) {
    if (!data) return;
    const p = data.personalDetails || {};
    setValue("fullName", p.fullName);
    setValue("careerGoal", p.careerGoal);
    setValue("email", p.email);
    setValue("phone", p.phone);
    setValue("location", p.location);
    setValue("linkedin", p.linkedin);
    setValue("github", p.github);
    setValue("summary", p.summary);
    setValue("technicalSkills", data.technicalSkillsRaw || "");

    const expList = document.getElementById("experienceList");
    expList.innerHTML = "";
    experienceCount = 0;
    if (Array.isArray(data.experiences)) {
        data.experiences.forEach(e => addExperience(e.role, e.company, e.duration, e.description));
    }

    const projList = document.getElementById("projectList");
    projList.innerHTML = "";
    projectCount = 0;
    if (Array.isArray(data.projects)) {
        data.projects.forEach(pr => addProject(pr.title, pr.technologies, pr.description));
    }

    const eduList = document.getElementById("educationList");
    eduList.innerHTML = "";
    educationCount = 0;
    if (Array.isArray(data.educations)) {
        data.educations.forEach(ed => addEducation(ed.degree, ed.institution, ed.year, ed.grade));
    }

    const certList = document.getElementById("certificateList");
    certList.innerHTML = "";
    certificateCount = 0;
    if (Array.isArray(data.certifications)) {
        data.certifications.forEach(c => addCertificate(c.name, c.organization, c.year));
    }

    const achList = document.getElementById("achievementList");
    achList.innerHTML = "";
    achievementCount = 0;
    if (Array.isArray(data.achievements)) {
        data.achievements.forEach(a => addAchievement(a.achievementText));
    }
}

window.exportFile = async function (format) {
    const resume = buildResumeObject();
    try {
        const response = await fetch(`${API_BASE}/resume/export?format=${format}`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(resume)
        });

        if (response.ok) {
            const blob = await response.blob();
            downloadBlob(blob, `resume_${getValue("fullName") || "export"}.${format}`);
            showToast(`Exported as .${format} successfully!`, "success");
            return;
        }
    } catch (e) {
        console.warn("Backend export unavailable, using client export.");
    }

    let content = "";
    if (format === "md") {
        content = `# ${resume.personalDetails.fullName}\n\n` +
                  `**${resume.personalDetails.email} | ${resume.personalDetails.phone} | ${resume.personalDetails.location}**\n\n` +
                  `## Professional Summary\n${resume.personalDetails.summary}\n\n` +
                  `## Technical Skills\n${resume.technicalSkillsRaw}\n\n`;
    } else {
        content = `${resume.personalDetails.fullName.toUpperCase()}\n` +
                  `${resume.personalDetails.email} | ${resume.personalDetails.phone} | ${resume.personalDetails.location}\n\n` +
                  `PROFESSIONAL SUMMARY\n${resume.personalDetails.summary}\n\n` +
                  `TECHNICAL SKILLS\n${resume.technicalSkillsRaw}\n\n`;
    }

    const blob = new Blob([content], { type: "text/plain;charset=utf-8" });
    downloadBlob(blob, `resume_${getValue("fullName") || "export"}.${format}`);
    showToast(`Downloaded .${format} file!`, "success");
};

function downloadBlob(blob, filename) {
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement("a");
    a.href = url;
    a.download = filename;
    document.body.appendChild(a);
    a.click();
    a.remove();
    window.URL.revokeObjectURL(url);
}

// ==========================================
// SAMPLE DATA & RESET
// ==========================================

window.loadSampleData = function () {
    setValue("fullName", "Alex Morgan");
    setValue("careerGoal", "Java & Cloud Software Engineer");
    setValue("email", "alex.morgan@university.edu");
    setValue("phone", "+91 98765 43210");
    setValue("location", "Bangalore, India");
    setValue("linkedin", "linkedin.com/in/alexmorgan-dev");
    setValue("github", "github.com/alexmorgan");
    setValue("summary", "Results-oriented Computer Science Engineering student specializing in scalable backend architectures and AI applications. Proven expertise in Java 17, Spring Boot, REST APIs, and database design with multiple hackathon accolades. Passionate about engineering high-throughput software solutions.");
    setValue("technicalSkills", "Java 17, Spring Boot, Microservices, REST APIs, SQL, Docker, React.js, Git, Generative AI");

    document.getElementById("experienceList").innerHTML = "";
    document.getElementById("projectList").innerHTML = "";
    document.getElementById("educationList").innerHTML = "";
    document.getElementById("certificateList").innerHTML = "";
    document.getElementById("achievementList").innerHTML = "";
    experienceCount = 0;
    projectCount = 0;
    educationCount = 0;
    certificateCount = 0;
    achievementCount = 0;

    addExperience(
        "Software Engineering Intern",
        "Apex Cloud Technologies",
        "May 2025 - Aug 2025",
        "• Engineered 12+ RESTful microservice endpoints with Spring Boot and Spring Data JPA.\n• Optimized SQL database query indexing, cutting response latency by 35% across high-traffic endpoints.\n• Partnered with senior engineers in CI/CD containerization workflows utilizing Docker and Git."
    );

    addProject(
        "Smart AI Resume Builder",
        "Java 17, Spring Boot, Google Gemini AI, HTML5/CSS3",
        "• Architected an end-to-end full stack application showcasing complete OOP principles and AI integration.\n• Implemented automated ATS keyword evaluation, dynamic layout themes, and multi-format document export.\n• Engineered secure REST APIs with exception handling and persistent file system repositories."
    );

    addProject(
        "Distributed Task Orchestrator",
        "Java, RabbitMQ, Redis, Spring Cloud",
        "• Built an asynchronous task distribution pipeline managing 5,000+ daily concurrent background tasks.\n• Integrated Redis caching layer resulting in sub-15ms message dispatch."
    );

    addEducation(
        "B.Tech in Computer Science & Engineering",
        "Karunya University",
        "2023 - 2027",
        "9.2 CGPA"
    );

    addCertificate(
        "Oracle Certified Professional: Java SE Developer",
        "Oracle Corporation",
        "2025"
    );

    addCertificate(
        "Spring Boot Microservices & Cloud Architecture",
        "Coursera / DeepLearning.AI",
        "2025"
    );

    addAchievement(
        "Winner - 1st Prize in National Hackathon among 350+ university collegiate teams."
    );

    addAchievement(
        "Published technical article on clean OOP architecture with 10,000+ readers."
    );

    showToast("Sample student credentials loaded with 1 click!", "success");
};

window.resetForm = function () {
    if (confirm("Clear all resume fields?")) {
        document.querySelectorAll("input, textarea").forEach(el => el.value = "");
        document.getElementById("experienceList").innerHTML = "";
        document.getElementById("projectList").innerHTML = "";
        document.getElementById("educationList").innerHTML = "";
        document.getElementById("certificateList").innerHTML = "";
        document.getElementById("achievementList").innerHTML = "";
        experienceCount = 0;
        projectCount = 0;
        educationCount = 0;
        certificateCount = 0;
        achievementCount = 0;
        showToast("Form cleared.", "info");
    }
};

// Auto-populate 1 initial card on blank load
if (experienceCount === 0) addExperience();
if (projectCount === 0) addProject();
if (educationCount === 0) addEducation();
