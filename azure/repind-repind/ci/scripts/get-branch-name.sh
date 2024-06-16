#!/bin/sh

NAME=$(echo "$1" | tr '[:upper:]' '[:lower:]' | sed 's:/:-:g')

echo "name=$NAME" > branch.txt