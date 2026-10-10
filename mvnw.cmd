@echo off
:: Maven Wrapper Batch Script for Windows
::
:: Licensed to the Apache Software Foundation (ASF) under one
:: or more contributor license agreements.  See the NOTICE file
:: distributed with this work for additional information
:: regarding copyright ownership.  The ASF licenses this file
:: to you under the Apache License, Version 2.0 (the
:: "License"); you may not use this file except in compliance
:: with the License.  You may obtain a copy of the License at
::
::   http://www.apache.org/licenses/LICENSE-2.0
::
:: Unless required by applicable law or agreed to in writing,
:: software distributed under the License is distributed on an
:: "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
:: KIND, either express or implied.  See the License for the
:: specific language governing permissions and limitations
:: under the License.

setlocal enabledelayedexpansion

set "WRAPPER_DIR=%~dp0.mvn\wrapper"
set "WRAPPER_JAR=%WRAPPER_DIR%\maven-wrapper.jar"
set "WRAPPER_PROPERTIES=%WRAPPER_DIR%\maven-wrapper.properties"

:: Download wrapper jar if not present
if not exist "%WRAPPER_JAR%" (
    echo Downloading Maven Wrapper...
    for /f "tokens=1* delims==" %%A in ('findstr "^wrapperUrl=" "%WRAPPER_PROPERTIES%"') do set "WRAPPER_URL=%%B"
    if not defined WRAPPER_URL set "WRAPPER_URL=https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.3.1/maven-wrapper-3.3.1.jar"
    if not exist "%WRAPPER_DIR%" mkdir "%WRAPPER_DIR%"
    curl -fsSL "%WRAPPER_URL%" -o "%WRAPPER_JAR%"
)

:: Execute Maven with proper main class
set "MAVEN_OPTS=%MAVEN_OPTS% -Dmaven.multiModuleProjectDirectory=%~dp0"
java %MAVEN_OPTS% -cp "%WRAPPER_JAR%" org.apache.maven.wrapper.MavenWrapperMain %*