ANDROID_NDK_HOME=ANDROID_SDK/ndk

go get -d golang.org/x/mobile/cmd/gomobile
go get -d golang.org/x/mobile/cmd/gobind

gomobile bind -target=android
