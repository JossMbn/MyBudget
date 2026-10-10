# Claude Instructions — MyBudget Compose Multiplatform Project

## Project Overview
MyBudget is a Compose Multiplatform project for managing a personal budget. 
It is designed to run on Android and iOS. The project uses Jetpack Compose for the shared user interface.
It follows **Clean Architecture**, **MVI**, and **Repository pattern** principles.
The project supports **Jetpack Compose**, **Material 3** UIs

## Technical Stack
- **Language**: Kotlin
- **Build System**: Gradle Kotlin DSL with version catalog (`gradle/libs.versions.toml`)
- **UI Framework**: Jetpack Compose Multiplatform
- **Asynchronous Programming**: Kotlin Coroutines
- **Dependency Injection (DI)**: Koin
- **Serialization**: Kotlinx Serialization
- **Image Loading**: Coil 3 for Compose

---

## Project Structure

The goal is to tend into a multi-module project structure in the future. This will facilitate maintenance, isolation, code reuse, and application evolution. All new code should follow this structure.

```
├── androidApp/ # Android Application root module.
│   └── src/main/kotlin/com/jmabilon/mybudget/ # Android app source code
├── iosApp/ # iOS Application root module.
├── shared/ # Shared ui and logic module for Android and iOS.
│   ├── src/androidMain/ # Android-specific code. Mirrors the structure of `commonMain` for platform-specific implementations if needed.
│   ├── src/commonMain/ # Shared code for both platforms.
│   │   ├── core/ # Shared logic and utilities (network, DI, logging, etc.)
│   │   ├── designsystem/ # Shared design system (colors, typography, shapes, themes, etc.)
│   │   ├── data/ # Shared data layer organised by concepts (repositories, data sources, database etc.)
│   │   ├── domain/ # Shared domain logic organised by concepts (use cases, models, repository interfaces etc.)
│   │   ├── presentation/ # Shared ui logic organised by concepts who know the business logic (viewmodels, UiModel uiState, uiEvent etc.)
│   │   └── feature/ # All features
│   │       ├── featureA/ # Feature A package
│   │       │   ├── data/ # (Optional) data layer for featureA
│   │       │   ├── di/ # Dependency injection package for featureA
│   │       │   │   └── FeatureAModule # Koin module for featureA
│   │       │   ├── domain/ # (Optional) Shared domain logic for featureA
│   │       │   └── presentation/ # presentation layer for featureA (ViewModels, UiState, UiEvent, Action etc.)
│   │       ├── featureB/
│   │       └── etc.
│   └── src/iosMain/ # iOS-specific code. Mirrors the structure of `commonMain` for platform-specific implementations if needed.
```

---

## General Coding Guidelines
- Follow **Clean Architecture** strictly: `ui` + `domain` + `data`.
- All **Business Logic** must be in the **shared** module.
- **ViewModels** contain business logic; Composables must remain stateless.
- Use **Coroutines**, **Flow** and **Suspend Functions** for async operations.
- Use **Material3** components for Compose.
- Keep consistent naming across packages and modules.
- Extract ui resources from `shared/src/commonMain/composeResources/` for Compose components.

---

## What to Avoid
- Avoid putting business logic in the Android or iOS app modules.
- Never edit `shared/build/`, XCFramework generated, `Podfile.lock`.
- No JVM API usage in the shared module. Use Kotlin Multiplatform compatible libraries.

---

## UI Behavior Recommendations
- **NEVER** use interactive Material3 components directly (`Button`, `Card`, `IconButton`, etc.)
- **ALLOWED**: Primitive components (`Text`, `Icon`, `Spacer`) but MUST use MyBudget theme file for styling (`shared/commonMain/kotlin/**/designsystem/theme/`).
- **ALWAYS** use design system components from `shared/commonMain/kotlin/**/designsystem/widget/` for interactive elements (e.g., `AppButton`, `AppFloatingButton`, etc.)
- **ALWAYS** use compose when building new screens or components.

---

## Global Skills & Agents
- **CRITICAL:** If a requested skill, agent, or pattern is not found in the local project root (`.claude/rules/`), always check the global directories at `~/.gemini/skills/` and `~/.copilot/skills/` or `~/.agents/skills/` before reporting it as missing.

---

## Things to Keep in Mind
- Suggest solutions aligned with **Clean Architecture** and **MVI**.
- All reflections must provide **Legible**, **Scalable**, **Maintainable** and **Idiomatic** code. Avoid hacks, workarounds, and anti-patterns.
- Generate fully typed Kotlin code; avoid pseudo-code.
- Include comments and KDoc for public functions and classes when it's necessary. Comment must be **clear**, **concise**, and **relevant** to the code.

<!-- rtk-instructions v2 -->
# RTK (Rust Token Killer) - Token-Optimized Commands

## Golden Rule

**Always prefix commands with `rtk`**. If RTK has a dedicated filter, it uses it. If not, it passes through unchanged. This means RTK is always safe to use.

**Important**: Even in command chains with `&&`, use `rtk`:
```bash
# ❌ Wrong
git add . && git commit -m "msg" && git push

# ✅ Correct
rtk git add . && rtk git commit -m "msg" && rtk git push
```

## RTK Commands by Workflow

### Build & Compile (80-90% savings)
```bash
rtk cargo build         # Cargo build output
rtk cargo check         # Cargo check output
rtk cargo clippy        # Clippy warnings grouped by file (80%)
rtk tsc                 # TypeScript errors grouped by file/code (83%)
rtk lint                # ESLint/Biome violations grouped (84%)
rtk prettier --check    # Files needing format only (70%)
rtk next build          # Next.js build with route metrics (87%)
```

### Test (60-99% savings)
```bash
rtk cargo test          # Cargo test failures only (90%)
rtk go test             # Go test failures only (90%)
rtk jest                # Jest failures only (99.5%)
rtk vitest              # Vitest failures only (99.5%)
rtk playwright test     # Playwright failures only (94%)
rtk pytest              # Python test failures only (90%)
rtk rake test           # Ruby test failures only (90%)
rtk rspec               # RSpec test failures only (60%)
rtk test <cmd>          # Generic test wrapper - failures only
```

### Git (59-80% savings)
```bash
rtk git status          # Compact status
rtk git log             # Compact log (works with all git flags)
rtk git diff            # Compact diff (80%)
rtk git show            # Compact show (80%)
rtk git add             # Ultra-compact confirmations (59%)
rtk git commit          # Ultra-compact confirmations (59%)
rtk git push            # Ultra-compact confirmations
rtk git pull            # Ultra-compact confirmations
rtk git branch          # Compact branch list
rtk git fetch           # Compact fetch
rtk git stash           # Compact stash
rtk git worktree        # Compact worktree
```

Note: Git passthrough works for ALL subcommands, even those not explicitly listed.

### GitHub (26-87% savings)
```bash
rtk gh pr view <num>    # Compact PR view (87%)
rtk gh pr checks        # Compact PR checks (79%)
rtk gh run list         # Compact workflow runs (82%)
rtk gh issue list       # Compact issue list (80%)
rtk gh api              # Compact API responses (26%)
```

### JavaScript/TypeScript Tooling (70-90% savings)
```bash
rtk pnpm list           # Compact dependency tree (70%)
rtk pnpm outdated       # Compact outdated packages (80%)
rtk pnpm install        # Compact install output (90%)
rtk npm run <script>    # Compact npm script output
rtk npx <cmd>           # Compact npx command output
rtk prisma              # Prisma without ASCII art (88%)
rtk uv run <cmd>        # Compact uv project command output
```

### Files & Search (60-75% savings)
```bash
rtk ls <path>           # Tree format, compact (65%)
rtk read <file>         # Code reading with filtering (60%)
rtk grep <pattern>      # Search grouped by file (75%). Format flags (-c, -l, -L, -o, -Z) run raw.
rtk find <pattern>      # Find grouped by directory (70%)
```

### Analysis & Debug (70-90% savings)
```bash
rtk err <cmd>           # Filter errors only from any command
rtk log <file>          # Deduplicated logs with counts
rtk json <file>         # JSON structure without values
rtk deps                # Dependency overview
rtk env                 # Environment variables compact
rtk summary <cmd>       # Smart summary of command output
rtk diff                # Ultra-compact diffs
```

### Infrastructure (85% savings)
```bash
rtk docker ps           # Compact container list
rtk docker images       # Compact image list
rtk docker logs <c>     # Deduplicated logs
rtk kubectl get         # Compact resource list
rtk kubectl logs        # Deduplicated pod logs
```

### Network (65-70% savings)
```bash
rtk curl <url>          # Compact HTTP responses (70%)
rtk wget <url>          # Compact download output (65%)
```

### Meta Commands
```bash
rtk gain                # View token savings statistics
rtk gain --history      # View command history with savings
rtk discover            # Analyze Claude Code sessions for missed RTK usage
rtk proxy <cmd>         # Run command without filtering (for debugging)
rtk init                # Add RTK instructions to CLAUDE.md
rtk init --global       # Add RTK to ~/.claude/CLAUDE.md
```

## Token Savings Overview

| Category         | Commands                       | Typical Savings |
|------------------|--------------------------------|-----------------|
| Tests            | vitest, playwright, cargo test | 90-99%          |
| Build            | next, tsc, lint, prettier      | 70-87%          |
| Git              | status, log, diff, add, commit | 59-80%          |
| GitHub           | gh pr, gh run, gh issue        | 26-87%          |
| Package Managers | pnpm, npm, npx                 | 70-90%          |
| Files            | ls, read, grep, find           | 60-75%          |
| Infrastructure   | docker, kubectl                | 85%             |
| Network          | curl, wget                     | 65-70%          |

Overall average: **60-90% token reduction** on common development operations.
<!-- /rtk-instructions -->
