<p align="left">
  <a href="#"><img alt="Android OS" src="https://img.shields.io/badge/OS-Android-3DDC84?style=flat-square&logo=android"></a>
  <a href="#"><img alt="Languages-Kotlin" src="https://flat.badgen.net/badge/Language/Kotlin?icon=https://raw.githubusercontent.com/binaryshrey/Awesome-Android-Open-Source-Projects/master/assets/Kotlin_Logo_icon_white.svg&color=f18e33"/></a>
  <a href="#"><img alt="PRs" src="https://img.shields.io/badge/PRs-Welcome-3DDC84?style=flat-square"></a>
</p>

![header](https://1.bp.blogspot.com/-9MiK78CFMLM/YQFurOq9AII/AAAAAAAAQ1A/lKj5GiDnO_MkPLb72XqgnvD5uxOsHO-eACLcBGAsYHQ/s0/Android-Compose-1.0-header-v2.png)

# 스캔 메모 app
## Skill
Clean Architecture + Dependency injection with Hilt + Jetpack Compose + Navigation + ML kit + CameraX

<p align="left">
<img src="https://github.com/ese111/CardScanner/blob/dev/screen1.png" width="24%" height="24%">
<img src="https://github.com/ese111/CardScanner/blob/dev/screen_list.png" width="24%" height="24%">
<img src="https://github.com/ese111/CardScanner/blob/dev/screen_permission.png" width="24%" height="24%">
</p>                                                                                            

## Introduce

약품 설명서, QR 링크 등 일상에서 버리기도 아깝고, 그렇다고 다 모아 놓을 수도 없는 텍스트들이 굉장히 많습니다.
그래서 이런 텍스트를 사진으로 찍거나 QR을 스캔해서 해당 정보를 간편하게 저장할 수 있는 메모 앱을 만들어 보았습니다.

## Skill Intro
* AAC
* Kotlin
* Jetpack Compose
* Accompanist-Permissions
* Kotlin Coroutines & Flow
* Clean Architecture
* Material Design 3
* Hilt
* Navigation, DataStore in Jetpack
* dateTime, Serialization in Kotlin
* ML kit
* MVVM

### 기술 설명
* Android Application으로 AAC Activity 위에 Compose를 이용하여 UI를 구현 하였습니다.
* State Holder 패턴을 이용하여 Compose의 State를 보관하고, AAC viewModel을 사용해서 비지니스 로직을 처리하도록 구현하였습니다.
  * UI -> StateHolder -> ViewModel -> Repository -> Data Layer 흐름으로 아키텍처를 구성하였습니다.
* State Hoisting을 사용하여 StateHolder를 내려서 상태를 내리고 람다로 이벤트를 전달하여 State를 변경하였습니다.
* Hilt를 이용한 DI를 이용하였습니다.
* jetpack Navigation을 이용해 Composable간의 인자 전달과 화면 이동을 구현하였습니다.
* jetpack DataStore를 이용하여 local storage를 이용하였습니다.
  * DataStore는 SharedPreferences의 단점을 보완하여 비동기적으로 flow를 이용하여 사용가능하고 에러핸들링이 가능합니다. UI Thread를 사용하지 않기때문 에 ANR을 예방이 가능합니다
* ML kit의 OCR과 바코드 스캔 기능을 사용하여 텍스트 인식과 QR의 데이터를 받아 올수 있는 기능을 구현하였습니다.
* Serialization을 이용해서 직렬화와 역직렬화를 구현하였습니다.
* dateTime을 이용해서 날짜를 이용하였습니다.

## Contact


Email : feung94@gmail.com

Medium : https://amuru.tistory.com