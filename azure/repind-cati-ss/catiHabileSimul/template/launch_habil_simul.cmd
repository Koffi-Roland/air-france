REM Script for launching habil-simul proxy for local test.
REM More info at https://bitbucket.devnet.klm.com/projects/TECCSE/repos/spring-security-habile/browse/spring-security-habile-simul

set VERSION=3.1.11
set HABIL_SIMUL_JAR_PATH=%USERPROFILE%\.m2\repository\com\afklm\springboot\habile\spring-security-habile-simul\%VERSION%\spring-security-habile-simul-%VERSION%.jar
set HABIL_SIMUL_CONFIG_JSON=%~dp0profiles-simu-habile.json
echo %HABIL_SIMUL_CONFIG_JSON%

@echo off
java -jar %HABIL_SIMUL_JAR_PATH% --config.simul=%HABIL_SIMUL_CONFIG_JSON% --habile.proxy.backend.endpoints=/CatiWsWeb/api/** --resilience4j.timelimiter.configs.default.timeout-duration=10s --debug
pause >nul
