#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")/.."
test_classes=$(mktemp -d)
trap 'rm -rf "$test_classes"' EXIT
javac -encoding UTF-8 --release 8 -d "$test_classes" src/main/java/com/yaoroz/game/*.java tests/com/yaoroz/game/*.java
java -Djava.awt.headless=true -cp "$test_classes" com.yaoroz.game.BreakoutTest
java -Djava.awt.headless=true -cp "$test_classes" com.yaoroz.game.BreakoutGameTest
javac -encoding UTF-8 --release 11 -d "$test_classes/module" src/main/java/module-info.java src/main/java/com/yaoroz/game/*.java
