(() => {
  const questions = Array.isArray(window.QUIZ_QUESTIONS) ? window.QUIZ_QUESTIONS : [];
  const levelId = Number(window.QUIZ_LEVEL_ID || 0);
  const panel = document.getElementById("question-panel");
  const dots = document.getElementById("progress-dots");
  const scoreVal = document.getElementById("score-val");
  const scoreTot = document.getElementById("score-tot");

  if (!panel || !dots || !scoreVal || !scoreTot) {
    return;
  }

  scoreTot.textContent = String(questions.length);

  if (questions.length === 0) {
    panel.innerHTML = "<p style='color:var(--text-muted); font-weight:700'>No questions available for this level.</p>";
    return;
  }

  const selectedByQuestion = {};
  let current = 0;

  const escapeHtml = (value) => {
    const text = value == null ? "" : String(value);
    return text
      .replace(/&/g, "&amp;")
      .replace(/</g, "&lt;")
      .replace(/>/g, "&gt;")
      .replace(/\"/g, "&quot;")
      .replace(/'/g, "&#39;");
  };

  const normalizeType = (q) => {
    const t = (q?.type || "S").toUpperCase();
    return t === "M" ? "M" : "S";
  };

  const isAnswered = (qId) => {
    const v = selectedByQuestion[qId];
    return Array.isArray(v) ? v.length > 0 : v != null;
  };

  const getAnsweredCount = () => questions.filter((q) => isAnswered(q.id)).length;

  const toggleSelection = (question, answerId, checked) => {
    const type = normalizeType(question);
    if (type === "M") {
      const currentValues = Array.isArray(selectedByQuestion[question.id])
        ? [...selectedByQuestion[question.id]]
        : [];
      const idx = currentValues.indexOf(answerId);
      if (checked && idx === -1) currentValues.push(answerId);
      if (!checked && idx !== -1) currentValues.splice(idx, 1);
      selectedByQuestion[question.id] = currentValues;
      return;
    }
    selectedByQuestion[question.id] = checked ? answerId : null;
  };

  const renderDots = () => {
    dots.innerHTML = "";
    questions.forEach((q, idx) => {
      const btn = document.createElement("button");
      btn.type = "button";
      btn.className = "dot-btn";
      if (idx === current) btn.classList.add("active");
      if (isAnswered(q.id)) btn.classList.add("done");
      btn.textContent = String(idx + 1);
      btn.addEventListener("click", () => {
        current = idx;
        renderQuestion();
      });
      dots.appendChild(btn);
    });
  };

  const renderQuestion = () => {
    const q = questions[current];
    const type = normalizeType(q);
    const answerValues = Array.isArray(q.answers) ? q.answers : [];
    const chosen = selectedByQuestion[q.id];
    const chosenSet = new Set(Array.isArray(chosen) ? chosen : chosen != null ? [chosen] : []);

    const answersHtml = answerValues
      .map((a) => {
        const inputType = type === "M" ? "checkbox" : "radio";
        const checked = chosenSet.has(a.id) ? "checked" : "";
        return `
          <label class="answer-item">
            <input type="${inputType}" name="q_${q.id}" value="${a.id}" ${checked} />
            <span class="al">${escapeHtml(a.label || "")}</span>
            <span>${escapeHtml(a.text || "")}</span>
          </label>
        `;
      })
      .join("");

    const mediaHtml = q.mediaUrl
      ? `<div class="q-media"><img src="${escapeHtml(q.mediaUrl)}" alt="Question media" loading="lazy" /></div>`
      : "";

    panel.innerHTML = `
      <section class="q-card">
        <div class="q-head">
          <span class="q-index">Question ${current + 1} / ${questions.length}</span>
          <span class="q-type">${type === "M" ? "Multi choice" : "Single choice"}</span>
        </div>
        <h2 class="q-title">${escapeHtml(q.title || "")}</h2>
        ${mediaHtml}
        <div class="answers-wrap">${answersHtml}</div>
        <div class="q-actions">
          <button type="button" class="btn btn-ghost btn-sm" id="prev-btn" ${current === 0 ? "disabled" : ""}>← Previous</button>
          ${
            current < questions.length - 1
              ? '<button type="button" class="btn btn-primary btn-sm" id="next-btn">Next →</button>'
              : '<button type="button" class="btn btn-primary btn-sm" id="submit-btn">Submit Quiz</button>'
          }
        </div>
      </section>
    `;

    const answerInputs = panel.querySelectorAll(`input[name="q_${q.id}"]`);
    answerInputs.forEach((input) => {
      input.addEventListener("change", (e) => {
        const target = e.target;
        toggleSelection(q, Number(target.value), target.checked);
        scoreVal.textContent = String(getAnsweredCount());
        renderDots();
      });
    });

    const prevBtn = document.getElementById("prev-btn");
    if (prevBtn) {
      prevBtn.addEventListener("click", () => {
        if (current > 0) {
          current -= 1;
          renderQuestion();
        }
      });
    }

    const nextBtn = document.getElementById("next-btn");
    if (nextBtn) {
      nextBtn.addEventListener("click", () => {
        if (current < questions.length - 1) {
          current += 1;
          renderQuestion();
        }
      });
    }

    const submitBtn = document.getElementById("submit-btn");
    if (submitBtn) {
      submitBtn.addEventListener("click", submitQuiz);
    }

    scoreVal.textContent = String(getAnsweredCount());
    renderDots();
  };

  const submitQuiz = () => {
    const form = document.createElement("form");
    form.method = "post";
    form.action = "/result";

    const levelInput = document.createElement("input");
    levelInput.type = "hidden";
    levelInput.name = "levelId";
    levelInput.value = String(levelId);
    form.appendChild(levelInput);

    questions.forEach((q) => {
      const selected = selectedByQuestion[q.id];
      if (Array.isArray(selected)) {
        selected.forEach((id) => {
          const input = document.createElement("input");
          input.type = "hidden";
          input.name = `q${q.id}`;
          input.value = String(id);
          form.appendChild(input);
        });
      } else if (selected != null) {
        const input = document.createElement("input");
        input.type = "hidden";
        input.name = `q${q.id}`;
        input.value = String(selected);
        form.appendChild(input);
      }
    });

    document.body.appendChild(form);
    form.submit();
  };

  renderQuestion();
})();