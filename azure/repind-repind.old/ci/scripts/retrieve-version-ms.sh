#!/bin/sh

NAME=$(echo "$1" | tr '[:lower:]' '[:upper:]' | sed 's:-:\\-:g')

VERSION=$(cat VERSIONSET.md | grep -o "^$NAME=[0-9.]*" | cut -d "=" -f 2)

echo "version=$VERSION" > ms-version.txt
echo "tag=$NAME-$VERSION" >> ms-version.txt