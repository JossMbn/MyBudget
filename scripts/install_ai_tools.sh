#!/usr/bin/env bash
# install_ai_tools.sh — Install RTK and initialize all supported AI tool integrations.

set -euo pipefail

# Always run from the project root, whatever the caller's working directory.
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(cd "${SCRIPT_DIR}/.." && pwd)"
cd "${PROJECT_ROOT}"

GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
RED='\033[0;31m'
NC='\033[0m'

ok() { echo -e "${GREEN}✓${NC} $1"; }
warn() { echo -e "${YELLOW}⚠${NC}  $1"; }
info() { echo -e "${BLUE}→${NC} $1"; }
fail() { echo -e "${RED}✗${NC} $1" >&2; }

if ! command -v brew >/dev/null 2>&1; then
  fail "Homebrew is required. Install Homebrew first: https://brew.sh"
  exit 1
fi

if command -v rtk >/dev/null 2>&1; then
  ok "rtk is already installed"
else
  info "Installing rtk via Homebrew..."
  brew install rtk
  ok "rtk installed"
fi

init_integration() {
  local name="$1"
  shift

  info "Initializing ${name} integration..."
  if rtk init "$@"; then
    ok "${name} integration initialized"
  else
    warn "${name} integration could not be initialized in this environment"
  fi
}

# Always configure all supported integrations to avoid Copilot-only setup.
init_integration "Claude Code"
init_integration "Copilot Chat" --copilot

ok "AI tools setup finished (project root: ${PROJECT_ROOT})"
