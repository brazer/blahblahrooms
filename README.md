## Inspiration
Main idea is inspired by the BlaBlaCar app, but based on rooms.

## What it does
The app allows users to find a roommate. There are 2 cases: in the first case, you rent a room and 
you can add in the app to add the room; in other case, you don't have a room and you can find the room 
in the app to share it with another roommate.

## How we built it
We created a client-server app with Google Maps Platform. 

## Challenges we ran into
The main challenge is implementation of Google Map for Jetpack Compose. 

## Accomplishments that we're proud of
We implemented the app with back-end, database, Google Maps, Jetpack Compose, Clean Architecture and 
MVI.

## What we learned
How to implement Google Maps in Jetpack Compose.

## What's next for BlahBlahRooms
Fix bugs, improve design, add more features (authorisation, first of all).

# Introduction

The BlahBlahRooms app allows a user to find a roommate.
This app is created by a template for doing Android development using GitLab and [fastlane](https://fastlane.tools/).
It is based on the tutorial for Android apps in general that can be found [here](https://developer.android.com/training/basics/firstapp/). 
If you're learning Android at the same time, you can also follow along that
tutorial and learn how to do everything all at once.

# Reference links

- [GitLab CI Documentation](https://docs.gitlab.com/ee/ci/)
- [Blog post: Android publishing with GitLab and fastlane](https://about.gitlab.com/2019/01/28/android-publishing-with-gitlab-and-fastlane/)

You'll definitely want to read through the blog post since that walks you in detail
through a working production configuration using this model.

# Getting started

First thing is to follow the [Android tutorial](https://developer.android.com/training/basics/firstapp/) and
get Android Studio installed on your machine, so you can do development using
the Android IDE. Other IDE options are possible, but not directly described or
supported here. If you're using your own IDE, it should be fairly straightforward
to convert these instructions to use with your preferred toolchain.

### Fastlane files

It also has fastlane setup per our [blog post](https://about.gitlab.com/2019/01/28/android-publishing-with-gitlab-and-fastlane/) on
getting GitLab CI set up with fastlane. Note that you may want to update your
fastlane bundle to the latest version; if a newer version is available, the pipeline
job output will tell you.

### Dockerfile build environment

In the root there is a Dockerfile which defines a build environment which will be
used to ensure consistent and reliable builds of your Android application using
the correct Android SDK and other details you expect. Feel free to add any
build-time tools or whatever else you need here.

We generate this environment as needed because installing the Android SDK
for every pipeline run would be very slow.

### Gradle configuration

The gradle configuration is exactly as output by Android Studio except for the
version name being updated to 

Instead of:

`versionName "1.0"`

It is now set to:

`versionName "1.0-${System.env.VERSION_SHA}"`

You'll want to update this for whatever versioning scheme you prefer.

### Build configuration (`.gitlab-ci.yml`)

The sample project also contains a basic `.gitlab-ci.yml` which will successfully 
build the Android application.

Note that for publishing to the test channels or production, you'll need to set
up your secret API key. The stub code is here for that, but please see our
[blog post](https://about.gitlab.com/2019/01/28/android-publishing-with-gitlab-and-fastlane/) for
details on how to set this up completely. In the meantime, publishing steps will fail.

The build script also handles automatic versioning by relying on the CI pipeline
ID to generate a unique, ever increasing number. If you have a different versioning
scheme you may want to change this.

```yaml
    - "export VERSION_CODE=$(($CI_PIPELINE_IID)) && echo $VERSION_CODE"
    - "export VERSION_SHA=`echo ${CI_COMMIT_SHA:0:8}` && echo $VERSION_SHA"
```

Apache License, Version 2.0