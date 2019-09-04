@echo off
title Server
cd ./bin/
java -Xmx512m -cp "../libraries/*;" core.Server 43595 PVP LIVE
pause
