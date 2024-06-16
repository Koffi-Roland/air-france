#!/bin/sh

NAME=$(echo "$1" | tr '[:lower:]' '[:upper:]')

VERSION=$(head -n 1 CHANGELOG.md | grep -Eo '[0-9]*\.[0-9]*\.[0-9]+')

echo "tag=$NAME-$VERSION" >> version.txt
echo "version=$VERSION" >> version.txt
