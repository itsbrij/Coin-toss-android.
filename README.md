# Coin Toss Android app

A small native Android app with an animated coin flip, Heads/Tails result, running counts, and a reset button. It works offline and requests no permissions.

## Build an APK using only your phone

This project includes a GitHub Actions workflow that builds the APK on GitHub's servers.

1. Sign in to https://github.com using your phone browser.
2. Create a new repository, for example `coin-toss-android`. A public or private repository can work; private repositories keep your source less visible.
3. Upload the **contents** of this project folder to the repository root. Make sure the `.github/workflows/build-apk.yml` file is included. If the upload UI hides dot-folders or makes multi-file uploads difficult, use GitHub's web editor to create `.github/workflows/build-apk.yml` and paste the workflow contents from that file.
4. Commit the files to the `main` branch. A workflow run should start automatically. Alternatively, open the repository's **Actions** tab, select **Build Coin Toss APK**, and tap **Run workflow**.
5. Open the completed workflow run. In **Artifacts**, tap `coin-toss-debug-apk` to download a ZIP containing `app-debug.apk`.
6. Extract that ZIP on your phone and tap `app-debug.apk` to install it.
7. If Android blocks installation, open the prompt to allow installs from Chrome or your file manager, then retry. Only install APKs you trust.

The first build may take several minutes. The generated debug APK is for personal testing, not a Play Store release.
