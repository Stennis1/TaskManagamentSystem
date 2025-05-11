#!/bin/bash

# === CONFIG ===
PROJECT_NAME=NovaTechTaskManager
SRC_DIR=src
WEB_DIR=web
BUILD_DIR=build
TOMCAT_HOME=/opt/tomcat
WAR_NAME=$PROJECT_NAME.war

echo "📦 Cleaning old build..."
rm -rf $BUILD_DIR
mkdir -p $BUILD_DIR/WEB-INF/classes

echo "🛠️ Compiling Java sources..."
javac -cp $TOMCAT_HOME/lib/servlet-api.jar \
      -d $BUILD_DIR/WEB-INF/classes \
      $(find $SRC_DIR -name "*.java")

echo "📂 Copying web files..."
cp -r $WEB_DIR/* $BUILD_DIR/

echo "📦 Packaging WAR..."
cd $BUILD_DIR
jar -cvf $WAR_NAME *
cd ..

echo "🚀 Deploying to Tomcat..."
sudo cp $BUILD_DIR/$WAR_NAME $TOMCAT_HOME/webapps/

echo "♻️ Restarting Tomcat..."
sudo $TOMCAT_HOME/bin/shutdown.sh
sleep 3
sudo $TOMCAT_HOME/bin/startup.sh

echo "✅ Deployed! Visit: http://localhost:8080/$PROJECT_NAME/tasks"
