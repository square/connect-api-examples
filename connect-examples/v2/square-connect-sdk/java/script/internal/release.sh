#!/bin/bash
set -eu

if [ ! -f ".release" ]
then
  echo "Missing release file"
  exit 1
fi

if [ ! -f ".version" ]
then
  echo "Missing version file"
  exit 1
fi

# Setup proxy access
export http_proxy=http://webproxy.vip:3128
export https_proxy=${http_proxy}
export no_proxy='vip,square,corp.squareup.com'

# setup git
git config --global user.name "Connect API Specification Generator"
git config --global user.email "devplat-external-sdk-accounts@squareup.com"

# Push release
access_token=$(cat /secrets/prod-jenkins-github-sdk-deployer.txt)
release_message=$(cat .release)
release_version=$(cat .version)
repo_dir=./github-repo
branch_name=release/$release_version

git clone https://$access_token@github.com/square/connect-java-sdk.git $repo_dir
cd $repo_dir
if [ `git branch -r | grep -i "^\s*origin/${branch_name}$"` ];
then
  git checkout $branch_name
else
  git checkout -f master
  git branch -D $branch_name || true
  git checkout -b $branch_name
fi

cp -rv ../.vscode          .
cp -rv ../docs             .
cp -rv ../gradle           .
cp -rv ../src              .
cp -rv ../travis-ci        .
cp -rv ../maven            .
cp -v ../.gitignore        .
cp -v ../.travis.yml       .
cp -v ../build.gradle      .
cp -v ../CHANGES.md        .
cp -v ../gradle.properties .
cp -v ../gradlew           .
cp -v ../gradlew.bat       .
cp -v ../LICENSE           .
cp -v ../pom.xml           .
cp -v ../README.md         .
cp -v ../settings.gradle   .

if [[ -z $(git status -s) ]]
then
  echo "No changes"
else
  git add --all .
  git commit -m "$release_message"
  git push origin release/$release_version
fi

cd ..
rm -rf $repo_dir
