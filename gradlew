#!/usr/bin/env sh

#
# Copyright 2015 the original author or authors.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

# Add default JVM options here. You can also use JAVA_OPTS and GRADLE_OPTS to pass any JVM options to Gradle Java process.
# For example, to pass system properties to all tasks:
# GRADLE_OPTS="-Dorg.gradle.internal.http.socketTimeout=60000 -Dorg.gradle.internal.http.connectionTimeout=60000"
DEFAULT_JVM_OPTS="-Xmx64m -Xms64m"

APP_NAME="Gradle"
APP_BASE_NAME=`basename "$0"`

# Use the maximum available, or set MAX_FD != -1 to use that value.
MAX_FD="maximum"

warn () {
    echo "$*"
}

die () {
    echo
    echo "ERROR: $*"
    echo
    exit 1
}

# OS specific support (must be 'true' or 'false').
cygwin=false
msys=false
darwin=false
nonstop=false
case "`uname`" in
  CYGWIN* )
    cygwin=true
    ;;
  Darwin* )
    darwin=true
    ;;
  MINGW* )
    msys=true
    ;;
  NONSTOP* )
    nonstop=true
    ;;
esac

# Attempt to set APP_HOME
# Resolve links: $0 may be a link
PRG="$0"
# Need this for relative symlinks.
while [ -h "$PRG" ] ; do
    ls=`ls -ld "$PRG"`
    link=`expr "$ls" : '.*-> \(.*\)$'`
    if expr "$link" : '/.*' > /dev/null; then
        PRG="$link"
    else
        PRG=`dirname "$PRG"`"/$link"
    fi
done
APP_HOME=`dirname "$PRG"`

# For Cygwin, ensure paths are in UNIX format before anything is touched
if $cygwin ; then
    [ -n "$APP_HOME" ] &&
        APP_HOME=`cygpath --unix "$APP_HOME"`
    [ -n "$JAVA_HOME" ] &&
        JAVA_HOME=`cygpath --unix "$JAVA_HOME"`
    [ -n "$CLASSPATH" ] &&
        CLASSPATH=`cygpath --path --unix "$CLASSPATH"`
fi

# Attempt to set JAVA_HOME if it's not already set.
if [ -z "$JAVA_HOME" ] ; then
    if $darwin ; then
        [ -x '/usr/libexec/java_home' ] && JAVA_HOME=`/usr/libexec/java_home`
    elif $cygwin ; then
        [ -n "`which java.exe`" ] && JAVA_HOME=`which java.exe | xargs readlink -f | xargs dirname | xargs dirname`
    elif $nonstop ; then
        if [ -d /usr/local/java/jdk1.8.0_45 ] ; then
             JAVA_HOME=/usr/local/java/jdk1.8.0_45
        fi
    fi
fi
if [ -z "$JAVA_HOME" ] ; then
    # If all else fails, try to find java in path
    JAVA_EXE=`which java 2>/dev/null`
    if [ -n "$JAVA_EXE" ] ; then
        JAVA_HOME=`echo "$JAVA_EXE" | xargs readlink -f | xargs dirname | xargs dirname`
    fi
fi
if [ -z "$JAVA_HOME" ] ; then
    die "ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.

Please set the JAVA_HOME variable in your environment to match the
location of your Java installation."
fi

# Add extension if we are running on cygwin
if $cygwin ; then
   APP_HOME=`cygpath --path --windows "$APP_HOME"`
   JAVA_HOME=`cygpath --path --windows "$JAVA_HOME"`
   CLASSPATH=`cygpath --path --windows "$CLASSPATH"`
fi

# Set APP_OPTS
if [ -z "$APP_OPTS" ] ; then
    APP_OPTS=""
fi

# Escape application args
APP_ARGS=
for arg in "$@"
do
    APP_ARGS="$APP_ARGS \"$arg\""
done

# Collect all arguments for the java command, following the shell quoting and substitution rules
eval set -- "$APP_ARGS"

# Split up the JVM options string into individual options.
# This is necessary because the shell quoting is not respected by the Windows command line parser.
# (see https://issues.gradle.org/browse/GRADLE-2852)
JAVA_OPTS=
for opt in $DEFAULT_JVM_OPTS $JAVA_OPTS $GRADLE_OPTS; do
    JAVA_OPTS="$JAVA_OPTS $opt"
done

# Add the jar to the classpath
if [ -n "$CLASSPATH" ] ; then
    CLASSPATH="$CLASSPATH":"$APP_HOME"/gradle/wrapper/gradle-wrapper.jar
else
    CLASSPATH="$APP_HOME"/gradle/wrapper/gradle-wrapper.jar
fi

# Determine the Java command to use to start the JVM.
if [ -n "$JAVA_HOME" ] ; then
    if [ -x "$JAVA_HOME/jre/sh/java" ] ; then
        # IBM's JDK on AIX uses strange locations for the executables
        JAVACMD="$JAVA_HOME/jre/sh/java"
    else
        JAVACMD="$JAVA_HOME/bin/java"
    fi
    if [ ! -x "$JAVACMD" ] ; then
        die "ERROR: JAVA_HOME is set to an invalid directory: $JAVA_HOME

Please set the JAVA_HOME variable in your environment to match the
location of your Java installation."
    fi
else
    JAVACMD="java"
    which java >/dev/null 2>&1 || die "ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.

Please set the JAVA_HOME variable in your environment to match the
location of your Java installation."
fi

# Increase the maximum number of open files
if ! $cygwin && ! $msys ; then
    MAX_FD_LIMIT=`ulimit -H -n`
    if [ $? -eq 0 ] ; then
        if [ "$MAX_FD" = "maximum" -o "$MAX_FD" = "max" ] ; then
            # Use the system limit
            MAX_FD="$MAX_FD_LIMIT"
        fi
        ulimit -n $MAX_FD
        if [ $? -ne 0 ] ; then
            warn "Could not set maximum file descriptor limit: $MAX_FD"
        fi
    else
        warn "Could not query maximum file descriptor limit: $MAX_FD_LIMIT"
    fi
fi

# Add default JVM options here. You can also use JAVA_OPTS and GRADLE_OPTS to pass any JVM options to Gradle Java process.
# For example, to pass system properties to all tasks:
# GRADLE_OPTS="-Dorg.gradle.internal.http.socketTimeout=60000 -Dorg.gradle.internal.http.connectionTimeout=60000"
DEFAULT_JVM_OPTS='"-Xmx64m" "-Xms64m"'

# Add -d32 if necessary
if [ "`uname -m`" = "i686" -a -z "`echo $JAVA_OPTS | grep 'd32'`" ] ; then
    JAVA_OPTS="-d32 $JAVA_OPTS"
fi

# Add the jar to the classpath
if [ -n "$CLASSPATH" ] ; then
    CLASSPATH="$CLASSPATH":"$APP_HOME"/gradle/wrapper/gradle-wrapper.jar
else
    CLASSPATH="$APP_HOME"/gradle/wrapper/gradle-wrapper.jar
fi

# Add the main class to the command
exec "$JAVACMD" $JAVA_OPTS -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"
