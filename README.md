Bạn là chuyên gia Android. Tạo TOÀN BỘ dự án "BatteryCare" (com.example.batterycare) có thể build ngay trên GitHub Actions mà KHÔNG cần sửa gì. Cung cấp TẤT CẢ file cần thiết, bao gồm cả workflow CI/CD.

## YÊU CẦU BUILD (QUAN TRỌNG NHẤT)
- Gradle: 8.4 (wrapper). AGP: 8.2.2. Kotlin: 1.9.22.
- compileSdk = 34, targetSdk = 34, minSdk = 28.
- Java: 17 (sourceCompatibility & targetCompatibility).
- AndroidX + Jetifier bật trong gradle.properties.
- Dependencies: core-ktx 1.12.0, appcompat 1.6.1, lifecycle-viewmodel-ktx 2.7.0, lifecycle-livedata-ktx 2.7.0, room-runtime 2.6.1, room-ktx 2.6.1, room-compiler 2.6.1 (dùng KSP), material 1.11.0.
- Plugin: kotlin-android, kotlin-ksp (hoặc kapt tùy chọn).
- Namespace trong app/build.gradle.kts: `namespace = "com.example.batterycare"`.
- AndroidManifest.xml: KHÔNG có `package`; mọi component (activity, service, receiver) PHẢI có `android:exported` rõ ràng.
- Icon launcher: dùng adaptive icon đơn giản, hoặc nếu không có file ảnh, tự tạo vector drawable và set trong manifest (không tham chiếu đến resource thiếu).

## CẤU TRÚC THƯ MỤC CHÍNH XÁC
Cung cấp mỗi file trong block code với tiêu đề `// File: [đường dẫn]`.

BatteryCare/
├── build.gradle.kts (project)
├── settings.gradle.kts
├── gradle.properties
├── gradle/wrapper/
│   ├── gradle-wrapper.jar (base64 hoặc link tải)
│   └── gradle-wrapper.properties
├── gradlew (script)
├── gradlew.bat
├── app/
│   ├── build.gradle.kts
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── assets/
│       │   └── reference_curve.json (dữ liệu mẫu từ 0-100% thời gian sạc tham chiếu)
│       ├── java/com/example/batterycare/
│       │   ├── BatteryApp.kt
│       │   ├── MainActivity.kt
│       │   ├── MainViewModel.kt
│       │   ├── BatteryChartView.kt
│       │   ├── PopupInfoDialog.kt
│       │   ├── BatteryMonitorService.kt
│       │   ├── BatteryReceiver.kt
│       │   ├── BatteryData.kt
│       │   ├── BatteryDatabase.kt
│       │   ├── BatteryDao.kt
│       │   ├── BatterySession.kt
│       │   ├── BatteryRepository.kt
│       │   ├── ReferenceChargeCurve.kt
│       │   ├── ChargeCurveComparator.kt
│       │   ├── BatteryStateAnalyzer.kt
│       │   ├── NotificationHelper.kt
│       │   ├── SoundPlayer.kt
│       │   ├── CheckpointLogger.kt
│       │   ├── BatteryUtil.kt
│       │   └── PermissionHelper.kt
│       └── res/
│           ├── values/strings.xml (toàn bộ text tiếng Việt)
│           ├── drawable/ (nếu cần icon)
│           └── mipmap-*/ (icon launcher, có thể dùng vector)
└── .github/workflows/
    └── build.yml (CI/CD tự động build debug APK)

## TÍNH NĂNG ỨNG DỤNG (tóm tắt, giữ nguyên tinh thần)
- Giám sát pin thời gian thực (mức %, điện áp, nhiệt độ, dòng sạc, trạng thái, công nghệ).
- Vẽ đường cong sạc thực tế (Canvas) đối chiếu với đường cong chuẩn (từ asset JSON).
- Chẩn đoán bằng tiếng Việt: "Pin còn tốt", "Pin yếu, nên thay", "Quá trình sạc bất thường", "Nhiệt độ pin cao, hãy rút sạc".
- Foreground Service + BroadcastReceiver giám sát liên tục, thông báo khi đầy hoặc lỗi, có âm thanh.
- Room DB lưu lịch sử phiên sạc, checkpoint logger chống crash.
- MVVM, mỗi file Kotlin ≤150 dòng, tiêu thụ RAM thấp.

## GIAO DIỆN (UI/UX)
- Full màn hình, nền tối (#121212), chữ xanh/đỏ theo trạng thái.
- Màn hình chính: phần trăm pin lớn, các chỉ số (nhiệt độ, điện áp, health) có thể chạm để mở popup giải thích tiếng Việt chi tiết.
- Biểu đồ nửa dưới màn hình, khung chẩn đoán ở dưới cùng.
- Toàn bộ chuỗi hiển thị trong strings.xml bằng tiếng Việt.

## WORKFLOW GITHUB ACTIONS (file `.github/workflows/build.yml`)
- Chạy khi push lên main.
- Dùng ubuntu-latest.
- Checkout code, setup Java 17 (temurin), cache Gradle.
- Cấp quyền thực thi cho gradlew.
- Chạy `./gradlew assembleDebug --no-daemon`.
- Upload artifact: `app/build/outputs/apk/debug/app-debug.apk` với tên `BatteryCare-APK`.

## ĐỊNH DẠNG OUTPUT
Với mỗi file, dùng:
