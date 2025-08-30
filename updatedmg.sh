#!/bin/bash
echo "Updating TypingGame.dmg..."

# Rebuild JAR
echo "Rebuilding JAR..."
rm -rf build/
mkdir -p build
javac -cp src/main/java -d build src/main/java/**/*.java
cp src/main/java/res/* build/

cat > manifest.txt << 'EOL'
Manifest-Version: 1.0
Main-Class: main.TypingApp

EOL

jar cfm TypingGame.jar manifest.txt -C build/ .

# Remove old DMG
echo "Removing old DMG..."
rm -f TypingGame-*.dmg

# Create new DMG
echo "Creating new DMG..."
jpackage --input . \
         --name "TypingGame" \
         --main-jar TypingGame.jar \
         --main-class main.TypingApp \
         --type dmg \
         --app-version 1.0 \
         --description "Fast-Paced Typing Game"

echo "DMG updated successfully!"
ls -la TypingGame-*.dmg