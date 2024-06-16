#!/bin/sh

version=$1
alreadyExist=false

if [ $(git tag -l "$version") ]; then
    echo "TAG ${version} already exist in GIT. Skipped this build..."
    alreadyExist="true"
else
    echo "TAG ${version} not exist in GIT."
fi

echo "already.exist=$alreadyExist" > tag.txt
