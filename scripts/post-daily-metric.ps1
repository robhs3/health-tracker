#!/usr/bin/env bash

Invoke-WebRequest -Uri "http://localhost:8080/api/daily-metrics" `
  -Method POST `
  -Headers @{ "Content-Type" = "application/json" } `
  -Body '{"date":"2025-12-29","weight":167.9,"calories":2550,"protein":175}'
