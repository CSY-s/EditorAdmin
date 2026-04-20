param(
    [string]$SourceDir = "",
    [string]$GitSafeDir = "C:\temp\EditorAdmin_upload",
    [string]$RepoUrl = "https://github.com/CSY-s/EditorAdmin.git",
    [string]$CommitMessage = ""
)

$ErrorActionPreference = "Stop"

if ([string]::IsNullOrWhiteSpace($SourceDir)) {
    $SourceDir = [System.IO.Path]::GetFullPath((Join-Path $PSScriptRoot ".."))
}

function Invoke-Git {
    param(
        [string[]]$Arguments,
        [string]$WorkingDirectory
    )

    Push-Location $WorkingDirectory
    try {
        & git @Arguments
        if ($LASTEXITCODE -ne 0) {
            throw "git $($Arguments -join ' ') failed with exit code $LASTEXITCODE"
        }
    } finally {
        Pop-Location
    }
}

if (!(Test-Path $SourceDir)) {
    throw "Source directory not found: $SourceDir"
}

if (!(Test-Path "C:\temp")) {
    New-Item -ItemType Directory -Path "C:\temp" -Force | Out-Null
}

if (!(Test-Path $GitSafeDir)) {
    New-Item -ItemType Directory -Path $GitSafeDir -Force | Out-Null
}

$robocopyArgs = @(
    $SourceDir,
    $GitSafeDir,
    "/E",
    "/XD", ".git", "target", "logs", "upload", "frontend\node_modules", ".metadata", ".settings", ".apt_generated", ".apt_generated_tests",
    "/XF", ".vite-dev.out.log", ".vite-dev.err.log", "*.log", "*.gz", "hs_err_pid*.log", "replay_pid*.log",
    "/R:1",
    "/W:1"
)

& robocopy @robocopyArgs | Out-Null
$robocopyExitCode = $LASTEXITCODE
if ($robocopyExitCode -ge 8) {
    throw "robocopy failed with exit code $robocopyExitCode"
}

if (!(Test-Path (Join-Path $GitSafeDir ".git"))) {
    Invoke-Git -Arguments @("init") -WorkingDirectory $GitSafeDir
    Invoke-Git -Arguments @("branch", "-M", "main") -WorkingDirectory $GitSafeDir
    Invoke-Git -Arguments @("remote", "add", "origin", $RepoUrl) -WorkingDirectory $GitSafeDir
}

$currentRemote = (& git -C $GitSafeDir remote get-url origin 2>$null)
if (!$currentRemote) {
    Invoke-Git -Arguments @("remote", "add", "origin", $RepoUrl) -WorkingDirectory $GitSafeDir
} elseif ($currentRemote.Trim() -ne $RepoUrl) {
    Invoke-Git -Arguments @("remote", "set-url", "origin", $RepoUrl) -WorkingDirectory $GitSafeDir
}

Invoke-Git -Arguments @("add", ".") -WorkingDirectory $GitSafeDir

$status = (& git -C $GitSafeDir status --short)
if (-not $status) {
    Write-Host "No changes to publish."
    exit 0
}

if ([string]::IsNullOrWhiteSpace($CommitMessage)) {
    $CommitMessage = "Update on $(Get-Date -Format 'yyyy-MM-dd HH:mm:ss')"
}

Invoke-Git -Arguments @("commit", "-m", $CommitMessage) -WorkingDirectory $GitSafeDir
Invoke-Git -Arguments @("-c", "pack.threads=1", "-c", "pack.window=0", "-c", "pack.depth=0", "-c", "core.compression=0", "push", "-u", "origin", "main") -WorkingDirectory $GitSafeDir

Write-Host "Published successfully from $GitSafeDir"
