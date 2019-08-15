#!/usr/bin/env bash
if [ "$TRAVIS_BRANCH" = 'master' ] && [ "$TRAVIS_PULL_REQUEST" == 'false' ]; then
    openssl aes-256-cbc -K $encrypted_cef8742a9861_key -iv $encrypted_cef8742a9861_iv -in maven/codesigning.asc.enc -out maven/codesigning.asc -d
    gpg --fast-import maven/codesigning.asc 
fi
