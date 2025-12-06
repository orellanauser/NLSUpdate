NLSUpdate

Purpose
- An app specifically for Newland Android 11+ devices.
- A minimal Android app that triggers a Newland system update when a specific firmware file exists.
- The OTA.zip must be a Newland Market firmware version.

Behavior
- On launch, checks for the OTA.zip in /storage/emulated/0/Documents or /storage/emulated/0/Download
- If the file exists, sends a broadcast with:
  - action: nlscan.action.RUN_SYSTEM_UPDATE
  - extra: file_path = absolute path to the OTA.zip
- Finishes immediately after attempting to send. If the file is not present, the app exits without sending the broadcast.

Build requirements
- JDK 11
- Android SDK: compile/target API 36, minSdk 33
- Use the included Gradle wrapper

Build
- Linux/macOS: ./gradlew assembleDebug
- Windows: gradlew.bat assembleDebug

Run/Test
1) Obtain the correct firmware package from your device vendor (Newland) and copy it to the device as OTA.zip.
2) Place the file at /Documents or /Download on the device.
3) Install and launch the app.
4) If the broadcast is handled by the system, the device should start the vendor update process.

Notes
- No UI is shown; the app performs its task and exits.
- No firmware archives are included in this repository. 
- Do not commit vendor firmware files or large archives to version control.

License

  MIT License

Copyright (c) 2025 Luis E. Orellana

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including, without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES, OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT, OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OF OTHER DEALINGS IN THE
SOFTWARE.
