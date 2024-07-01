<p align="center">
  <a href="#">
    <img alt="demo" src="assets/demo.png" align="center" width="400" />
  </a>
</p>

<h1 align="center">chrome-ai</h1>

<p>
  Experimental library built on top of the <a href="https://github.com/varabyte/kobweb" target="_blank" rel="noopener noreferrer">Kobweb</a> (Compose HTML framework). It allows you to use the official <a href="https://developer.chrome.com/docs/ai/built-in" target="_blank" rel="noopener noreferrer">Built-in AI</a> with KotlinJS and Compose HTML.
</p>

> [!IMPORTANT]  
> Check [enable AI in Chrome](#enable-ai-in-chrome) first to use this library.


## Demo

https://github.com/dead8309/chrome-ai/assets/68665948/6b14f9a0-f53c-4a07-b33a-ba2ff19bbe11


## Installation

Update your project's `build.gradle.kts` file:
```gradle
repositories {
    ..
    maven(url = "https://jitpack.io")
}
```

Add the dependency to your module's `build.gradle.kts` file:
```gradle
kotlin { 
    sourceSets {
        ..
        val jsMain by getting {
            dependencies {
                ..
                implementation("com.github.dead8309:chrome-ai:0.0.1")
            }
        }
    }
}
```


## Docs

Checkout the docs [dead8309.github.io/chrome-ai](https://dead8309.github.io/chrome-ai/)


## Enable AI in Chrome

Chrome built-in AI is a preview feature, you need to use chrome version 127 or greater, now in [dev](https://www.google.com/chrome/dev/?extra=devchannel) or [canary](https://www.google.com/chrome/canary/) channel, [may release on stable chanel at Jul 17, 2024](https://chromestatus.com/roadmap).

After then, you should turn on these flags:
* [chrome://flags/#prompt-api-for-gemini-nano](chrome://flags/#prompt-api-for-gemini-nano): `Enabled`
* [chrome://flags/#optimization-guide-on-device-model](chrome://flags/#optimization-guide-on-device-model): `Enabled BypassPrefRequirement`
* [chrome://components/](chrome://components/): Click `Optimization Guide On Device Model` to download the model.


## Running locally

### Prerequisites

- [Kobweb](https://github.com/varabyte/kobweb?tab=readme-ov-file#install-the-kobweb-binary)
- Kotlin
- Java JDK 11+

Clone the repository:

```bash
git clone https://github.com/dead8309/chrome-ai-kt.git
cd chrome-ai-kt
```

Run the site

```bash
cd site
kobweb run
```