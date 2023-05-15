#!/bin/bash

VERSION=1.0.0
MC_VERSION=1.19.4

# Make directories
rm -rf ./temp_build
mkdir -p ./temp_build
cd temp_build

rm -rf ./playtimeutils
mkdir -p ./playtimeutils/ca/sperrer/p0t4t0sandwich/playtimeutils

# Copy output Jars to temp directory
cp ../bukkit/build/libs/bukkit-*.jar ./
mv ./bukkit-*.jar ./bukkit.zip

cp ../bungee/build/libs/bungee-*.jar ./
mv ./bungee-*.jar ./bungee.zip

cp ../common/build/libs/common-*.jar ./
mv ./common-*.jar ./common.zip

cp ../fabric/build/libs/fabric-*.jar ./
mv ./fabric-*.jar ./fabric.zip

cp ../forge/build/libs/forge-*.jar ./
mv ./forge-*.jar ./forge.zip

# Unzip Jars
unzip ./bukkit.zip -d ./bukkit
unzip ./bungee.zip -d ./bungee
unzip ./common.zip -d ./common
unzip ./fabric.zip -d ./fabric
unzip ./forge.zip -d ./forge

# Process Jars
cp -r ./bukkit/ca/sperrer/p0t4t0sandwich/playtimeutils/bukkit ./playtimeutils/ca/sperrer/p0t4t0sandwich/playtimeutils/bukkit
cp ./bukkit/plugin.yml ./playtimeutils

cp -r ./bungee/ca/sperrer/p0t4t0sandwich/playtimeutils/bungee ./playtimeutils/ca/sperrer/p0t4t0sandwich/playtimeutils/bungee
cp ./bungee/bungee.yml ./playtimeutils

cp -r ./common/* ./playtimeutils
rm -rf ./playtimeutils/META-INF/*
rm -f ./playtimeutils/LICENSE

cp -r ./fabric/ca/sperrer/p0t4t0sandwich/playtimeutils/fabric ./playtimeutils/ca/sperrer/p0t4t0sandwich/playtimeutils/fabric
cp ./fabric/fabric.mod.json ./playtimeutils
cp ./fabric/playtimeutils.mixins.json ./playtimeutils
cp -r ./fabric/assets ./playtimeutils

cp -r ./forge/ca/sperrer/p0t4t0sandwich/playtimeutils/forge ./playtimeutils/ca/sperrer/p0t4t0sandwich/playtimeutils/forge
cp ./forge/pack.mcmeta ./playtimeutils
cp ./forge/META-INF/mods.toml ./playtimeutils/META-INF

# Zip Jar contents
cd ./playtimeutils
zip -r ./playtimeutils.zip ./*

# Rename Jar
mv ./playtimeutils.zip ../../build/libs/PlaytimeUtils-$VERSION-$MC_VERSION.jar
