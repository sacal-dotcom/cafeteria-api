#!/bin/sh
#
# Maven Wrapper Script
#
# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License.  You may obtain a copy of the License at
#
#   http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing,
# software distributed under the License is distributed on an
# "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
# KIND, either express or implied.  See the License for the
# specific language governing permissions and limitations
# under the License.
#

# -----------------------------------------------------------------------------
# Maven Wrapper Startup Script
# -----------------------------------------------------------------------------

set -e

# Resolve the location of the wrapper jar
WRAPPER_JAR="$(dirname "$0")/.mvn/wrapper/maven-wrapper.jar"
WRAPPER_PROPERTIES="$(dirname "$0")/.mvn/wrapper/maven-wrapper.properties"

# Download wrapper jar if not present
if [ ! -f "$WRAPPER_JAR" ]; then
    echo "Downloading Maven Wrapper..."
    WRAPPER_URL=$(grep "^wrapperUrl=" "$WRAPPER_PROPERTIES" | cut -d'=' -f2-)
    if [ -z "$WRAPPER_URL" ]; then
        WRAPPER_URL="https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.3.1/maven-wrapper-3.3.1.jar"
    fi
    mkdir -p "$(dirname "$WRAPPER_JAR")"
    curl -fsSL "$WRAPPER_URL" -o "$WRAPPER_JAR"
fi

# Execute Maven with proper main class
MAVEN_OPTS="$MAVEN_OPTS -Dmaven.multiModuleProjectDirectory=$(dirname "$0")"
exec java $MAVEN_OPTS -cp "$WRAPPER_JAR" org.apache.maven.wrapper.MavenWrapperMain "$@"