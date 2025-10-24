# Oakay Contracts - Android (Jetpack Compose) - Project Skeleton

Это минимальная заготовка Android-проекта (Kotlin + Jetpack Compose) для приложения **Oakay Contracts**.

Невозможно собрать APK непосредственно в этом окружении (на платформе ChatGPT) из-за отсутствия Android SDK/Gradle. Вместо этого здесь доступны два пути:

1. **Собрать APK локально**:
   - Скачайте этот проект и откройте в Android Studio (Arctic Fox или новее).
   - Убедитесь, что у вас установлен Android SDK (compileSdk 34) и JDK 17.
   - Нажмите *Build -> Build Bundle(s) / APK(s) -> Build APK(s)*.
   - APK появится в `app/build/outputs/apk/`.

2. **Автоматически собирать APK через GitHub Actions**:
   - Добавьте этот проект в репозиторий GitHub.
   - Создайте workflow `.github/workflows/android.yml` (пример ниже).
   - GitHub Actions автоматически установит SDK и соберёт APK, который можно скачать из артефактов сборки.

## Что реализовано в проекте
- Jetpack Compose UI для списка заказов и формы добавления.
- Room база данных для хранения заказов.
- WorkManager Worker (ReminderWorker) — отправляет локальное уведомление.
- Простая логика статуса заказа и цветовой подсветки карточек.
- Места для интеграции Google Sign-In и Google Drive (помечены как TODO).

## Как настроить Google Sign-In и Drive
- Зарегистрируйте OAuth 2.0 client ID в Google Cloud Console.
- Добавьте `google-services.json` (если используете Firebase) или настройте Play Services auth.
- Используйте `com.google.android.gms:play-services-auth` для входа через Google.
- Для синхронизации используйте Google Drive REST API (Drive v3) и сохранение/загрузку JSON базы или экспорт медиафайлов.

## Пример GitHub Actions workflow (android.yml)
```yaml
name: Android CI

on:
  push:
    branches: [ main ]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - name: Set up JDK 17
        uses: actions/setup-java@v4
        with:
          distribution: 'temurin'
          java-version: '17'
      - name: Build APK
        run: ./gradlew :app:assembleRelease
      - name: Upload APK
        uses: actions/upload-artifact@v4
        with:
          name: OakayContracts-apk
          path: app/build/outputs/apk/release/*.apk
```

Если нужно — я могу подготовить также готовую GitHub Actions конфигурацию в проекте (и/или CI конфиг для CircleCI), чтобы вы могли сразу пушить и получать собранный APK в артефактах сборки.

---
