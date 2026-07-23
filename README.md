<div align="center">

# 📋 Task_Stream

**Приложение для управления задачами с календарём, категориями и приоритетами**

[![Kotlin](https://img.shields.io/badge/Kotlin-2.0.0-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org/)
[![Compose](https://img.shields.io/badge/Jetpack%20Compose-2024.09.00-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)](https://developer.android.com/jetpack/compose)
[![Material3](https://img.shields.io/badge/Material%20Design-3-757575?style=for-the-badge&logo=materialdesign&logoColor=white)](https://m3.material.io/)
[![Room](https://img.shields.io/badge/Room-Database-FFB300?style=for-the-badge&logo=sqlite&logoColor=white)](https://developer.android.com/jetpack/androidx/releases/room)
[![Hilt](https://img.shields.io/badge/Hilt-DI-2C3E50?style=for-the-badge&logo=dagger&logoColor=white)](https://dagger.dev/hilt/)
[![JUnit](https://img.shields.io/badge/JUnit-4.13.2-25A162?style=for-the-badge&logo=junit5&logoColor=white)](https://junit.org/junit4/)
[![Mockk](https://img.shields.io/badge/Mockk-1.13.5-FF6B6B?style=for-the-badge&logo=kotlin&logoColor=white)](https://mockk.io/)
[![Espresso](https://img.shields.io/badge/Espresso-3.5.1-4285F4?style=for-the-badge&logo=android&logoColor=white)](https://developer.android.com/training/testing/espresso)
[![API](https://img.shields.io/badge/API-26%2B-brightgreen.svg?style=for-the-badge)](https://android-arsenal.com/api?level=26)
[![License](https://img.shields.io/badge/License-MIT-blue.svg?style=for-the-badge)](LICENSE)

</div>
**Task_Stream** — это современное Android-приложение для управления задачами, построенное на **Jetpack Compose** с использованием **Clean Architecture**. Оно позволяет легко добавлять, просматривать, редактировать и удалять задачи, а также фильтровать их по датам.

<p align="center">
   <img width="300" height="533" alt="IMG_20260721_213633" src="https://github.com/user-attachments/assets/75bb6595-75ca-479f-9385-4daf86f7a0c1" />
   <img width="300" height="533" alt="IMG_20260721_213542" src="https://github.com/user-attachments/assets/6629adfd-d548-4f45-93e9-88fc80c66eea" />
</p>

---

## ✨ Основные возможности

| Возможность | Описание |
|---|---|
| ✅ **Управление задачами (CRUD)** | Полный цикл работы с задачами — создание, просмотр, редактирование и удаление |
| 📅 **Календарь и фильтрация по дате** | Интуитивно понятный недельный календарь для быстрой навигации и просмотра задач на выбранный день |
| 🏷️ **Приоритеты и категории** | Назначение задач приоритетами (Лёгкий, Средний, Сложный) и категориями для лучшей организации |
| 🌙 **Современный UI** | Адаптивный интерфейс на Jetpack Compose с поддержкой тёмной темы |

---

## 🏗️ Архитектура

Проект построен на **Clean Architecture** с разделением на слои:

```
┌─────────────────────────────────────────────────────────────┐
│                   Presentation Layer                        │
│  • Jetpack Compose UI (MainScreen)                          │
│  • ViewModels (MainViewModel с StateFlow)                   │
│  • UI-компоненты (components)                               │
│  • Темизация (ui/theme)                                     │
├─────────────────────────────────────────────────────────────┤
│                      Domain Layer                           │
│  • Domain Models (Task)                                     │
│  • Реализация репозитория (RepositoryImpl)                  │
├─────────────────────────────────────────────────────────────┤
│                       Data Layer                            │
│  • Room Database (database)                                 │
│  • Мапперы (mappers)                                        │
│  • Интерфейс репозитория (repository)                       │ 
│  • Dagger Hilt модули (DI)                                  │
└─────────────────────────────────────────────────────────────┘
```

### Описание слоёв

- **Presentation Layer** — отвечает за отображение данных и взаимодействие с пользователем. Содержит Compose‑экран (`MainScreen`), переиспользуемые компоненты (`components`), ViewModel (`MainViewModel`) и настройки темы (`ui/theme`).
- **Domain Layer** — содержит бизнес-логику приложения. Здесь определена основная модель данных (`Task`) и **интерфейс репозитория** (`RepositoryImpl`), который обеспечивает доступ к данным.
- **Data Layer** — отвечает за работу с данными. Содержит:
  - Room базу данных (`database`) с сущностями (`TaskEntity`), DAO (`TaskDao`) и модулем для Hilt (`DateBaseModule`);
  - мапперы (`mappers`) для преобразования между Entity и Domain моделями, а также мапперы для категорий, дат и приоритетов;
  - репозиторий (`Repository`) и модуль для внедрения зависимости (`RepositoryModule`).

---

## 🛠️ Технологический стек

| Компонент | Технология |
|---|---|
| **Язык** | Kotlin (основной), Java (тесты) |
| **UI** | Jetpack Compose |
| **DI** | Dagger Hilt |
| **База данных** | Room (SQLite) |
| **Асинхронность** | Kotlin Coroutines + Flow |
| **Тесты** | JUnit4, Mockk |
| **Архитектура** | MVVM + Clean Architecture |
| **Минимальная версия** | Android 8.0 (API 26) |
| **Target SDK** | Android 14 (API 34) |

---

## 📂 Структура проекта

```
com.example.flowstasksapp/
│
├── Application.kt                     # Класс приложения (инициализация DI)
├── MainActivity.kt                    # Главная Activity (точка входа в UI)
│
├── data/                              # Data Layer
│   ├── database/                      # Работа с Room
│   │   ├── DateBaseModule.kt          # Dagger Hilt модуль для базы данных
│   │   ├── TaskDao.kt                 # DAO для задач
│   │   ├── TaskDateBase.kt            # База данных Room
│   │   └── TaskEntity.kt              # Сущность задачи (Room)
│   ├── mappers/                       # Мапперы
│   │   ├── CategoryMapper.kt          # Маппер для категорий
│   │   ├── DateMapper.kt              # Маппер для дат
│   │   ├── MappersModule.kt           # Dagger Hilt модуль для мапперов
│   │   ├── PriorityMapper.kt          # Маппер для приоритетов
│   │   └── TaskMapper.kt              # Маппер TaskEntity ↔ Task
│   └── repository/                    # Интерфейс репозитория
│       ├── Repository.kt              # Реализация интерфейса репозитория
│       └── RepositoryModule.kt        # Dagger Hilt модуль для репозитория
│
├── domain/                            # Domain Layer
│   ├── RepositoryImpl.kt              # Интерфейс репозитория
│   └── Task.kt                        # Модель задачи (бизнес-логика)
│
├── presentation/                      # Presentation Layer
│   ├── components/                    # Переиспользуемые Compose-компоненты
│   │   ├── ContentDialog.kt           # Диалог для ввода/редактирования задачи
│   │   ├── DayOfTheWeek.kt            # Компонент дня недели
│   │   ├── ShowCustomToast.kt         # Кастомный Toast
│   │   ├── ShowDialog.kt              # Диалог подтверждения
│   │   ├── TaskCard.kt                # Карточка задачи
│   │   └── WeeklyStrip.kt             # Недельная полоса с датами
│   ├── MainScreen.kt                  # Главный экран (Compose UI)
│   └── MainViewModel.kt               # ViewModel (StateFlow + обработка событий)
│
└── ui/theme/                          # Темизация
    ├── Color.kt                       # Цветовая палитра
    ├── Theme.kt                       # Настройки темы (светлая/тёмная)
    └── Type.kt                        # Типографика (шрифты, размеры)
```

---

## 🗄️ Структура базы данных

| Таблица | Поле | Тип | Описание |
|---|---|---|---|
| **tasks** | `id` | `Long` | Первичный ключ, автоинкремент |
| | `title` | `String` | Название задачи |
| | `description` | `String` | Описание задачи |
| | `priority` | `String` | Приоритет (Easy, Medium, Hard) |
| | `category` | `String` | Категория (Работа, Личное, Учёба и т.д.) |
| | `date` | `String` | Дата выполнения задачи |

---

## 📦 Список библиотек

Все зависимости указаны в `build.gradle.kts`:

- **Kotlin** — `org.jetbrains.kotlin:kotlin-stdlib`
- **Jetpack Compose** — `androidx.compose.ui`, `androidx.compose.material3`, `androidx.compose.runtime`
- **Compose Navigation** — `androidx.navigation:navigation-compose`
- **Dagger Hilt** — `com.google.dagger:hilt-android`, `androidx.hilt:hilt-navigation-compose`
- **Room** — `androidx.room:room-runtime`, `androidx.room:room-ktx`, `androidx.room:room-compiler`
- **Coroutines** — `org.jetbrains.kotlinx:kotlinx-coroutines-android`, `kotlinx-coroutines-core`
- **Lifecycle** — `androidx.lifecycle:lifecycle-runtime-compose`, `lifecycle-viewmodel-compose`
- **DataStore** — `androidx.datastore:datastore-preferences`
- **Тестирование** — `junit:junit`, `io.mockk:mockk`, `androidx.test:runner`, `androidx.test.espresso:espresso-core`

