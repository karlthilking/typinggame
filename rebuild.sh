#!/bin/bash
echo "Rebuilding TypingGame.jar..."

# Clean
rm -rf build/
mkdir -p build

# Compile
echo "Compiling Java files..."
javac -cp src/main/java -d build src/main/java/**/*.java

# Copy resources with explicit verification
echo "Copying resources..."
if [ -f "src/main/res/sound.png" ]; then
    cp src/main/res/sound.png build/
    echo "✓ Copied sound.png"
else
    echo "✗ sound.png not found in src/main/res/"
fi

if [ -f "src/main/res/377-english-words.txt" ]; then
    cp src/main/res/377-english-words.txt build/
    echo "✓ Copied 377-english-words.txt"
else
    echo "✗ 377-english-words.txt not found"
fi

if [ -f "src/main/res/tweaks" ]; then
    cp src/main/res/tweaks build/
    echo "✓ Copied tweaks"
else
    echo "✗ tweaks not found"
fi

# Verify resources in build
echo "Files in build directory:"
ls -la build/

# Create manifest
cat > manifest.txt << 'EOL'
Manifest-Version: 1.0
Main-Class: main.TypingApp

EOL

# Create JAR with manifest
echo "Creating JAR..."
jar cfm TypingGame.jar manifest.txt -C build/ .

# Show what ended up in the JAR
echo "Contents of JAR:"
jar tf TypingGame.jar | head -10

echo "JAR updated successfully!"